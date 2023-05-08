package com.ruoyi.bid.controller;

import java.math.BigDecimal;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.domain.EvaluationExpertsInformation;
import com.ruoyi.bid.domain.TenderProject;
import com.ruoyi.bid.mapper.BidListMapper;
import com.ruoyi.bid.mapper.EvaluationExpertsInformationMapper;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.bid.service.IEvaluationExpertsInformationService;
import com.ruoyi.bid.service.ITenderProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bid.domain.MarkSheet;
import com.ruoyi.bid.service.IMarkSheetService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评分表Controller
 *
 * @author chb
 * @date 2023-04-15
 */
@RestController
@RequestMapping("/sheet/sheet")
public class MarkSheetController extends BaseController {
    @Autowired
    private IMarkSheetService markSheetService;

    @Autowired
    private IBidListService bidListService;

    @Autowired
    private IEvaluationExpertsInformationService evaluationExpertsInformationService;

    @Autowired
    private ITenderProjectService tenderProjectService;


    /**
     * 查询评分表列表
     */
    @PreAuthorize("@ss.hasPermi('sheet:sheet:list')")
    @GetMapping("/list")
    public TableDataInfo list(MarkSheet markSheet) {
        startPage();
        List<MarkSheet> list = markSheetService.selectMarkSheetList(markSheet);
        return getDataTable(list);
    }

    /**
     * 导出评分表列表
     */
    @PreAuthorize("@ss.hasPermi('sheet:sheet:export')")
    @Log(title = "评分表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MarkSheet markSheet) {
        List<MarkSheet> list = markSheetService.selectMarkSheetList(markSheet);
        ExcelUtil<MarkSheet> util = new ExcelUtil<MarkSheet>(MarkSheet.class);
        util.exportExcel(response, list, "评分表数据");
    }

    /**
     * 获取评分表详细信息
     */
    @PreAuthorize("@ss.hasPermi('sheet:sheet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(markSheetService.selectMarkSheetById(id));
    }

    /**
     * 新增评分表
     */

    @Log(title = "评分表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MarkSheet markSheet) {
        return toAjax(markSheetService.insertMarkSheet(markSheet));
    }

    /**
     * 修改评分表
     */

    @Log(title = "评分表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MarkSheet markSheet) {
        //根据tenderProjectId reviewId projId 确定记录
        MarkSheet markSheet2 = new MarkSheet();
        markSheet2.setTenderProjectId(markSheet.getTenderProjectId());
        markSheet2.setProjId(markSheet.getProjId());
        markSheet2.setReviewId(markSheet.getReviewId());
        List<MarkSheet> list = markSheetService.selectMarkSheetList(markSheet2);
        MarkSheet markSheet1 = list.get(0);
        markSheet.setId(markSheet1.getId());
        return toAjax(markSheetService.updateMarkSheet(markSheet));
    }

    /**
     * 删除评分表
     */
    @PreAuthorize("@ss.hasPermi('sheet:sheet:remove')")
    @Log(title = "评分表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(markSheetService.deleteMarkSheetByIds(ids));
    }


    /**
     * 根据项目id查询评分表列表
     */
    @GetMapping("/listprojId/{id}")
    public TableDataInfo listByProjectId(@PathVariable String id) {
        startPage();
        MarkSheet markSheet = new MarkSheet();
        markSheet.setProjId(Long.valueOf(id));
        markSheet.setIsSummary(1L);
        List<MarkSheet> list = markSheetService.selectMarkSheetList(markSheet);
        List<Map<String, Object>> collect = list.stream().map(new Function<MarkSheet, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(MarkSheet markSheet) {
                Long tenderProjectId = markSheet.getTenderProjectId();
                Long id = markSheet.getId();
                BigDecimal price = markSheet.getPrice();
                BigDecimal business = markSheet.getBusiness();
                BigDecimal techology = markSheet.getTechology();
                BigDecimal after = markSheet.getAfter();
                Long projId = markSheet.getProjId();
                Long reviewId = markSheet.getReviewId();
                BigDecimal total = markSheet.getTotal();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("price", price);
                map.put("business", business);
                map.put("techology", techology);
                map.put("after", after);
                map.put("projId", projId);
                map.put("reviewId", reviewId);
                map.put("total", total);
                map.put("tenderProjectId", tenderProjectId);
                BidList bidList = bidListService.selectBidListByProjId(projId);
                EvaluationExpertsInformation evaluationExpertsInformation = evaluationExpertsInformationService.selectEvaluationExpertsInformationByReviewId(reviewId);
                TenderProject tenderProject = tenderProjectService.selectTenderProjectByTpId(tenderProjectId);
                map.put("projIdByName", bidList.getProjName());
                map.put("tenderProjectIdByName", tenderProject.getBidderCompany());


                return map;
            }
        }).collect(Collectors.toList());
        return getDataTable(collect);
    }

    /**
     * 查询是否已经评过标
     */
    @GetMapping("/isEvaluationOfBid")
    public AjaxResult isEvaluationOfBid(MarkSheet markSheet) {
        startPage();
        List<MarkSheet> list = markSheetService.selectMarkSheetList(markSheet);
        if (list.size() > 0) {
            return AjaxResult.success("401", "已经评分完成");
        }
        return AjaxResult.success();
    }

}
