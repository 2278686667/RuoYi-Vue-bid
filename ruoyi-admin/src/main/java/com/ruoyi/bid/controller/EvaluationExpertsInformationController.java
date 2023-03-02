package com.ruoyi.bid.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.bid.common.vo.EvaluationExpertsInfomationVO;
import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.domain.EvaluationExpertsInformation;
import com.ruoyi.bid.domain.FileInfomation;
import com.ruoyi.bid.domain.TenderProject;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.bid.service.IEvaluationExpertsInformationService;
import com.ruoyi.bid.service.IFileInfomationService;
import com.ruoyi.bid.service.ITenderProjectService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.common.CommonController;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评审专家信息Controller
 * 
 * @author ruoyi
 * @date 2023-02-23
 */
@RestController
@RequestMapping("/system/evaluation_experts_information")
public class EvaluationExpertsInformationController extends BaseController
{
    @Autowired
    private IEvaluationExpertsInformationService evaluationExpertsInformationService;

    @Autowired
    private ITenderProjectService tenderProjectService;

    @Autowired
    private IBidListService bidListService;

    @Autowired
    private IFileInfomationService fileInfomationService;

    @Autowired
    private CommonController commonController;

    /**
     * 查询评审专家信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:information:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvaluationExpertsInformation evaluationExpertsInformation)
    {
        startPage();
        List<EvaluationExpertsInformation> list = evaluationExpertsInformationService.selectEvaluationExpertsInformationList(evaluationExpertsInformation);
        return getDataTable(list);
    }
    /**
     * 查询评审专家信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:information:list')")
    @GetMapping("/listByUserId")
    public TableDataInfo listByUserId(EvaluationExpertsInformation evaluationExpertsInformation)
    {


        //获取当前登录用户
        evaluationExpertsInformation.setUserId(SecurityUtils.getUserId());
        List<EvaluationExpertsInformation> list = evaluationExpertsInformationService.selectEvaluationExpertsInformationList(evaluationExpertsInformation);

        //封装公司信息列表
        List<EvaluationExpertsInfomationVO> voList=new ArrayList<>(list.size());
        int count=0;
        for (int i = 0; i < list.size(); i++) {
            EvaluationExpertsInformation evaluationExpertsInformation1 = list.get(i);
            Long projId = evaluationExpertsInformation1.getProjId();
            TenderProject tenderProject=tenderProjectService.selectTenderProjectByProjIdOne(projId);
            if (StringUtils.isNull(tenderProject)){
                continue;

            }
            EvaluationExpertsInfomationVO vo=new EvaluationExpertsInfomationVO();
            vo.setTpId(tenderProject.getTpId());
            vo.setStatus(evaluationExpertsInformation1.getStatus());
            vo.setSignIn(evaluationExpertsInformation1.getSignIn());
            vo.setReviewId(evaluationExpertsInformation1.getReviewId());
            vo.setPurchaser(tenderProject.getPurchaser());
            vo.setProjNumber(tenderProject.getProjNumber());
            vo.setProjName(tenderProject.getProjName());
            vo.setIsDeleted(evaluationExpertsInformation1.getIsDeleted());
            vo.setFileName(tenderProject.getFileName());
            vo.setBidderId(tenderProject.getBidderId());
            vo.setFileId(tenderProject.getFielId());
            vo.setBidderCompany(tenderProject.getBidderCompany());
            vo.setUserId(evaluationExpertsInformation1.getUserId());
            vo.setProjId(tenderProject.getProjId());



            voList.add(vo);

        }
        return getDataTable(voList);
    }

    /**
     * 导出评审专家信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:information:export')")
    @Log(title = "评审专家信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EvaluationExpertsInformation evaluationExpertsInformation)
    {
        List<EvaluationExpertsInformation> list = evaluationExpertsInformationService.selectEvaluationExpertsInformationList(evaluationExpertsInformation);
        ExcelUtil<EvaluationExpertsInformation> util = new ExcelUtil<EvaluationExpertsInformation>(EvaluationExpertsInformation.class);
        util.exportExcel(response, list, "评审专家信息数据");
    }

    /**
     * 获取评审专家信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:information:query')")
    @GetMapping(value = "/{reviewId}")
    public AjaxResult getInfo(@PathVariable("reviewId") Long reviewId)
    {
        return success(evaluationExpertsInformationService.selectEvaluationExpertsInformationByReviewId(reviewId));
    }

    /**
     * 新增评审专家信息
     */
    @PreAuthorize("@ss.hasPermi('system:information:add')")
    @Log(title = "评审专家信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvaluationExpertsInformation evaluationExpertsInformation)
    {
        return toAjax(evaluationExpertsInformationService.insertEvaluationExpertsInformation(evaluationExpertsInformation));
    }

    /**
     * 修改评审专家信息
     */
    @PreAuthorize("@ss.hasPermi('system:information:edit')")
    @Log(title = "评审专家信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvaluationExpertsInformation evaluationExpertsInformation)
    {
        return toAjax(evaluationExpertsInformationService.updateEvaluationExpertsInformation(evaluationExpertsInformation));
    }

    /**
     * 删除评审专家信息
     */
    @PreAuthorize("@ss.hasPermi('system:information:remove')")
    @Log(title = "评审专家信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable Long[] reviewIds)
    {
        return toAjax(evaluationExpertsInformationService.deleteEvaluationExpertsInformationByReviewIds(reviewIds));
    }
    @Log(title = "签到")
    @GetMapping("/signIn")
    public AjaxResult singIn(EvaluationExpertsInformation evaluationExpertsInformation){
        BidList bidList=new BidList();
        bidList.setProjId(evaluationExpertsInformation.getProjId());
        bidList.setStatus("3");
        AjaxResult ajaxResult = bidListService.updateByStatus(bidList);
        return toAjax(evaluationExpertsInformationService.updateEvaluationExpertsInformation(evaluationExpertsInformation));

    }

    @Log(title = "下载评审资料")
    @GetMapping("/downloadReview")
    public void downloadReview(EvaluationExpertsInformation evaluationExpertsInformation, HttpServletResponse response, HttpServletRequest request){
        Long fileId = evaluationExpertsInformation.getFileId();
        FileInfomation fileInfomation = fileInfomationService.selectFileInfomationByFileId(fileId);
        commonController.fileDownload(fileInfomation.getFilePath(),false,response,request);

    }

}
