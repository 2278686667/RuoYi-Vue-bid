package com.ruoyi.bid.mapper;

import java.util.List;
import com.ruoyi.bid.domain.MarkSheet;

/**
 * 评分表Mapper接口
 * 
 * @author chb
 * @date 2023-04-15
 */
public interface MarkSheetMapper 
{
    /**
     * 查询评分表
     * 
     * @param id 评分表主键
     * @return 评分表
     */
    public MarkSheet selectMarkSheetById(Long id);

    /**
     * 查询评分表列表
     * 
     * @param markSheet 评分表
     * @return 评分表集合
     */
    public List<MarkSheet> selectMarkSheetList(MarkSheet markSheet);

    /**
     * 新增评分表
     * 
     * @param markSheet 评分表
     * @return 结果
     */
    public int insertMarkSheet(MarkSheet markSheet);

    /**
     * 修改评分表
     * 
     * @param markSheet 评分表
     * @return 结果
     */
    public int updateMarkSheet(MarkSheet markSheet);

    /**
     * 删除评分表
     * 
     * @param id 评分表主键
     * @return 结果
     */
    public int deleteMarkSheetById(Long id);

    /**
     * 批量删除评分表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMarkSheetByIds(Long[] ids);
}
