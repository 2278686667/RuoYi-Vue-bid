package com.ruoyi.bid.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.bid.domain.FileInfomation;
import com.ruoyi.bid.mapper.FileInfomationMapper;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.bid.domain.FolderStructure;
import com.ruoyi.bid.service.IFolderStructureService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件夹结构信息Controller
 * 
 * @author chb
 * @date 2023-01-31
 */
@RestController
@RequestMapping("/folder/folder")
public class FolderStructureController extends BaseController
{
    @Autowired
    private IFolderStructureService folderStructureService;

    @Resource
    private FileInfomationMapper fileInfomationMapper;

    /**
     * 查询文件夹结构信息列表
     */
    @PreAuthorize("@ss.hasPermi('folder:folder:list')")
    @GetMapping("/list")
    public TableDataInfo list(FolderStructure folderStructure)
    {
        String projId = folderStructure.getProjId();
        List<FileInfomation> fileInfomations=null;
        startPage();
        if (projId.isEmpty()){
            throw new RuntimeException("项目id为空");
        }else if (StringUtils.isNull(folderStructure.getFolderId())&& StringUtils.isNotNull(folderStructure.getProjId())){
            //拿到项目id
            FileInfomation fileInfomation=new FileInfomation();
            fileInfomation.setProjId(Long.valueOf(folderStructure.getProjId()));
            List<FileInfomation> fileInfomations1 = fileInfomationMapper.selectFileInfomationList(fileInfomation);
            return getDataTable(fileInfomations1);
        }else {
            fileInfomations = fileInfomationMapper.selectFileInfomationByFolderIdAndProjId(folderStructure.getFolderId(), folderStructure.getProjId());
            return getDataTable(fileInfomations);
        }





    }

    /**
     * 导出文件夹结构信息列表
     */
    @PreAuthorize("@ss.hasPermi('folder:folder:export')")
    @Log(title = "文件夹结构信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FolderStructure folderStructure)
    {
        List<FolderStructure> list = folderStructureService.selectFolderStructureList(folderStructure);
        ExcelUtil<FolderStructure> util = new ExcelUtil<FolderStructure>(FolderStructure.class);
        util.exportExcel(response, list, "文件夹结构信息数据");
    }

    /**
     * 获取文件夹结构信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('folder:folder:query')")
    @GetMapping(value = "/{folderId}")
    public AjaxResult getInfo(@PathVariable("folderId") Long folderId)
    {
        return success(folderStructureService.selectFolderStructureByFolderId(folderId));
    }

    /**
     * 新增文件夹结构信息
     */
    @PreAuthorize("@ss.hasPermi('folder:folder:add')")
    @Log(title = "文件夹结构信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FolderStructure folderStructure)
    {
        return toAjax(folderStructureService.insertFolderStructure(folderStructure));
    }

    /**
     * 修改文件夹结构信息
     */
    @PreAuthorize("@ss.hasPermi('folder:folder:edit')")
    @Log(title = "文件夹结构信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FolderStructure folderStructure)
    {
        return toAjax(folderStructureService.updateFolderStructure(folderStructure));
    }

    /**
     * 删除文件夹结构信息
     */
    @PreAuthorize("@ss.hasPermi('folder:folder:remove')")
    @Log(title = "文件夹结构信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{folderIds}")
    public AjaxResult remove(@PathVariable Long[] folderIds)
    {
        return toAjax(folderStructureService.deleteFolderStructureByFolderIds(folderIds));
    }
    /**
     * 获取文件树列表
     */
    @PreAuthorize("@ss.hasPermi('folder:folder:list')")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(FolderStructure f)
    {
        return success(folderStructureService.selectFoldTreeList(f));
    }
}
