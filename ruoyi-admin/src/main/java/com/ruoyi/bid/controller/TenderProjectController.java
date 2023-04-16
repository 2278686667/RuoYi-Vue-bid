package com.ruoyi.bid.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.domain.FileInfomation;
import com.ruoyi.bid.domain.FolderStructure;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.bid.service.IFileInfomationService;
import com.ruoyi.bid.service.IFolderStructureService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.models.auth.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bid.domain.TenderProject;
import com.ruoyi.bid.service.ITenderProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 投标项目Controller
 * 
 * @author chb
 * @date 2023-02-24
 */
@RestController
@RequestMapping("/tender/project")
public class TenderProjectController extends BaseController
{
    @Autowired
    private ITenderProjectService tenderProjectService;

    @Autowired
    private IFileInfomationService fileInfomationService;

    @Autowired
    private IFolderStructureService folderStructureService;

    @Autowired
    private IBidListService bidListService;

    /**
     * 我的投标项目列表
     */
    @PreAuthorize("@ss.hasPermi('tender:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(TenderProject tenderProject)
    {
        startPage();
        tenderProject.setBidderId(SecurityUtils.getUserId());
        List<TenderProject> list = tenderProjectService.selectTenderProjectList(tenderProject);
        for (TenderProject project : list) {
            Long projId = project.getProjId();
            BidList bidList = bidListService.selectBidListByProjId(projId);
            project.setBidStatus(bidList.getStatus());
        }
        return getDataTable(list);
    }

    /**
     * 导出投标项目列表
     */
    @PreAuthorize("@ss.hasPermi('tender:project:export')")
    @Log(title = "投标项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TenderProject tenderProject)
    {
        List<TenderProject> list = tenderProjectService.selectTenderProjectList(tenderProject);
        ExcelUtil<TenderProject> util = new ExcelUtil<TenderProject>(TenderProject.class);
        util.exportExcel(response, list, "投标项目数据");
    }

    /**
     * 获取投标项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('tender:project:query')")
    @GetMapping(value = "/{tpId}")
    public AjaxResult getInfo(@PathVariable("tpId") Long tpId)
    {
        return success(tenderProjectService.selectTenderProjectByTpId(tpId));
    }

    /**
     * 新增投标项目
     */
    @PreAuthorize("@ss.hasPermi('tender:project:add')")
    @Log(title = "投标项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TenderProject tenderProject)
    {
        Long deptId = SecurityUtils.getUserId();
        tenderProject.setBidderId(deptId);
        tenderProject.setStatus("0");
        tenderProject.setUserId(SecurityUtils.getUserId());
        //设置项目状态
        BidList bidList = bidListService.selectBidListByProjId(tenderProject.getProjId());
        tenderProject.setBidStatus(bidList.getStatus());

        return toAjax(tenderProjectService.insertTenderProject(tenderProject));
    }

    /**
     * 修改投标项目
     */
    @PreAuthorize("@ss.hasPermi('tender:project:edit')")
    @Log(title = "投标项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TenderProject tenderProject)
    {
        return toAjax(tenderProjectService.updateTenderProject(tenderProject));
    }

    /**
     * 删除投标项目
     */
    @PreAuthorize("@ss.hasPermi('tender:project:remove')")
    @Log(title = "投标项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tpIds}")
    public AjaxResult remove(@PathVariable Long[] tpIds)
    {
        return toAjax(tenderProjectService.deleteTenderProjectByTpIds(tpIds));
    }

    /**
     * 文件修改
     */
    @Transactional
    @PreAuthorize("@ss.hasPermi('tender:project:edit')")
    @Log(title = "投标项目", businessType = BusinessType.UPDATE)
    @PutMapping("/file")
    public AjaxResult editFile(@RequestBody Map<String,Object> map)
    {
        Integer projId = (Integer) map.get("projId");
        Integer tpId = (Integer) map.get("tpId");
        String fileName = (String) map.get("tenderTemp");

        //查询文件柜id
        FolderStructure folderStructure=new FolderStructure();
        folderStructure.setProjId(projId+"");
        folderStructure.setFolderName("投标文件");
        folderStructure=folderStructureService.selectFolderByProjId(folderStructure);
        if (StringUtils.isNull(folderStructure)) {

            return toAjax(0);
        }
        //查询文件柜是否有该文件，如果有设置state 1 已删除
        TenderProject tenderProject = tenderProjectService.selectTenderProjectByTpId(tpId.longValue());

        //要进行修改，把原来的文件删除
        if (StringUtils.isNotNull(tenderProject.getFielId())){
            FileInfomation fileInfomation1 = fileInfomationService.selectFileInfomationByFileId(tenderProject.getFielId());
            //如果查询不到file文件说明有误
            if (StringUtils.isNull(fileInfomation1)){
                throw new RuntimeException("文件上传有误");
            }
            fileInfomation1.setIsDeleted(1L);
            fileInfomationService.updateFileInfomation(fileInfomation1);
        }


        //封装文件信息
        FileInfomation fileInfomation=new FileInfomation();
        fileInfomation.setProjId( projId.longValue());
        fileInfomation.setFolderId(folderStructure.getFolderId());
        fileInfomation.setFilePath(fileName);
        fileInfomation.setFileName(fileName.substring(fileName.lastIndexOf("/") + 1));
        fileInfomation.setUpdateBy(SecurityUtils.getUsername());
        fileInfomationService.insertFileInfomation(fileInfomation);

        //修改投标项目信息
         tenderProject=new TenderProject();
        tenderProject.setTpId(tpId.longValue());
        tenderProject.setFielId(fileInfomation.getFileId());
        tenderProject.setFileName(fileInfomation.getFileName());
        tenderProject.setUploadBy(fileInfomation.getUpdateBy());
        tenderProject.setUploadTime(new Date());
        //已上传
        tenderProject.setStatus("1");
        int i = tenderProjectService.updateTenderProject(tenderProject);
            return toAjax(i);

    }


    @Transactional
    @PreAuthorize("@ss.hasPermi('tender:project:edit')")
    @Log(title = "投标项目", businessType = BusinessType.UPDATE)
    @PutMapping("/file2")
    public AjaxResult editFile2(@RequestBody Map<String,Object> map)
    {
        Integer projId = (Integer) map.get("projId");
        Integer tpId = (Integer) map.get("tpId");
        String fileName = (String) map.get("tenderTemp");

        //查询文件柜id
        FolderStructure folderStructure=new FolderStructure();
        folderStructure.setProjId(projId+"");
        folderStructure.setFolderName("投标文件");
        folderStructure=folderStructureService.selectFolderByProjId(folderStructure);
        if (StringUtils.isNull(folderStructure)) {

            return toAjax(0);
        }
        //查询文件柜是否有该文件，如果有设置state 1 已删除
        TenderProject tenderProject = tenderProjectService.selectTenderProjectByTpId(tpId.longValue());

        //要进行修改，把原来的文件删除
        if (StringUtils.isNotNull(tenderProject.getFielId())){
            FileInfomation fileInfomation1 = fileInfomationService.selectFileInfomationByFileId(tenderProject.getFielId());
            //如果查询不到file文件说明有误
            if (StringUtils.isNull(fileInfomation1)){
                throw new RuntimeException("文件上传有误");
            }
            fileInfomation1.setIsDeleted(1L);
            fileInfomationService.updateFileInfomation(fileInfomation1);
        }


        //封装文件信息
        FileInfomation fileInfomation=new FileInfomation();
        fileInfomation.setProjId( projId.longValue());
        fileInfomation.setFolderId(folderStructure.getFolderId());
        fileInfomation.setFilePath(fileName);
        fileInfomation.setFileName(fileName.substring(fileName.lastIndexOf("/") + 1));
        fileInfomation.setUpdateBy(SecurityUtils.getUsername());
        fileInfomationService.insertFileInfomation(fileInfomation);

        //修改投标项目信息
        tenderProject=new TenderProject();
        tenderProject.setTpId(tpId.longValue());
        tenderProject.setFielId(fileInfomation.getFileId());
        tenderProject.setFileName(fileInfomation.getFileName());
        tenderProject.setUploadBy(fileInfomation.getUpdateBy());
        tenderProject.setUploadTime(new Date());
        //已上传
        tenderProject.setStatus("1");
        int i = tenderProjectService.updateTenderProject(tenderProject);
        return toAjax(i);

    }

    /**
     * 查询是否已经投标了
     */
//    public TenderProject selectByProjId(@RequestBody TenderProject tenderProject){
////        List<TenderProject> tenderProjectList=tenderProjectService.selectTenderProjectByProjId(tenderProject);
////        return tenderProjectList.get(0);
//        return null;
//    }

}
