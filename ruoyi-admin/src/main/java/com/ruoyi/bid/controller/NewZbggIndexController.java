package com.ruoyi.bid.controller;

import com.ruoyi.bid.domain.NewZbggIndex;
import com.ruoyi.bid.service.INewZbggIndexService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2023-05-02
 */
@RestController
@RequestMapping("/system/index")
public class NewZbggIndexController extends BaseController
{
    @Autowired
    private INewZbggIndexService newZbggIndexService;

    /**
     * 根据招标方式类型查询
     */

    @GetMapping("/list")
    public TableDataInfo list(NewZbggIndex newZbggIndex)
    {
        startPage();
        List<NewZbggIndex> list = newZbggIndexService.selectNewZbggIndexList(newZbggIndex);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:index:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewZbggIndex newZbggIndex)
    {
        List<NewZbggIndex> list = newZbggIndexService.selectNewZbggIndexList(newZbggIndex);
        ExcelUtil<NewZbggIndex> util = new ExcelUtil<NewZbggIndex>(NewZbggIndex.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */

    @GetMapping(value = "/{ggcode}")
    public AjaxResult getInfo(@PathVariable("ggcode") String ggcode)
    {
        return success(newZbggIndexService.selectNewZbggIndexByGgcode(ggcode));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:index:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewZbggIndex newZbggIndex)
    {
        return toAjax(newZbggIndexService.insertNewZbggIndex(newZbggIndex));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:index:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewZbggIndex newZbggIndex)
    {
        return toAjax(newZbggIndexService.updateNewZbggIndex(newZbggIndex));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:index:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ggcodes}")
    public AjaxResult remove(@PathVariable String[] ggcodes)
    {
        return toAjax(newZbggIndexService.deleteNewZbggIndexByGgcodes(ggcodes));
    }
}
