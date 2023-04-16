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
    @PreAuthorize("@ss.hasPermi('sheet:sheet:edit')")
    @Log(title = "评分表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MarkSheet markSheet) {
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
}
