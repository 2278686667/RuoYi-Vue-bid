package com.ruoyi.bid.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 招投标Controller
 * 
 * @author chb
 * @date 2022-11-27
 */
@RestController
@RequestMapping("/bid/invite_tenders")
public class BidListController extends BaseController
{
    @Autowired
    private IBidListService bidListService;

    /**
     * 查询招投标列表
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:list')")
    @GetMapping("/list")
    public TableDataInfo list(BidList bidList)
    {
        startPage();
        List<BidList> list = bidListService.selectBidListList(bidList);
        return getDataTable(list);
    }

    /**
     * 导出招投标列表
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:export')")
    @Log(title = "招投标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BidList bidList)
    {
        List<BidList> list = bidListService.selectBidListList(bidList);
        ExcelUtil<BidList> util = new ExcelUtil<BidList>(BidList.class);
        util.exportExcel(response, list, "招投标数据");
    }

    /**
     * 获取招投标详细信息
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:query')")
    @GetMapping(value = "/{projId}")
    public AjaxResult getInfo(@PathVariable("projId") Long projId)
    {
        return success(bidListService.selectBidListByProjId(projId));
    }

    /**
     * 新增招投标
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:add')")
    @Log(title = "招投标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BidList bidList)
    {
        return toAjax(bidListService.insertBidList(bidList));
    }

    /**
     * 修改招投标
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:edit')")
    @Log(title = "招投标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BidList bidList)
    {
        return toAjax(bidListService.updateBidList(bidList));
    }

    /**
     * 删除招投标
     */
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:remove')")
    @Log(title = "招投标", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projIds}")
    public AjaxResult remove(@PathVariable Long[] projIds)
    {
        return toAjax(bidListService.deleteBidListByProjIds(projIds));
    }
    @PreAuthorize("@ss.hasPermi('bid:invite_tenders:edit')")
    @Log(title = "招投标", businessType = BusinessType.UPDATE)
    @PutMapping("/lssue")
    public AjaxResult lssue(@RequestBody BidList bidList){

        return bidListService.updateByStatus(bidList);
    }

}
