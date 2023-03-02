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
import com.ruoyi.bid.domain.FileInfomation;
import com.ruoyi.bid.service.IFileInfomationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件信息Controller
 * 
 * @author ruoyi
 * @date 2023-02-17
 */
@RestController
@RequestMapping("/system/fileInfomation")
public class FileInfomationController extends BaseController
{
    @Autowired
    private IFileInfomationService fileInfomationService;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:infomation:list')")
    @GetMapping("/list")
    public TableDataInfo list(FileInfomation fileInfomation)
    {
        startPage();
        List<FileInfomation> list = fileInfomationService.selectFileInfomationList(fileInfomation);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:infomation:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FileInfomation fileInfomation)
    {
        List<FileInfomation> list = fileInfomationService.selectFileInfomationList(fileInfomation);
        ExcelUtil<FileInfomation> util = new ExcelUtil<FileInfomation>(FileInfomation.class);
        util.exportExcel(response, list, "文件信息数据");
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:infomation:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return success(fileInfomationService.selectFileInfomationByFileId(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:infomation:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FileInfomation fileInfomation)
    {
        return toAjax(fileInfomationService.insertFileInfomation(fileInfomation));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:infomation:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FileInfomation fileInfomation)
    {
        return toAjax(fileInfomationService.updateFileInfomation(fileInfomation));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:infomation:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(fileInfomationService.deleteFileInfomationByFileIds(fileIds));
    }
}
