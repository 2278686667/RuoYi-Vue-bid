package com.ruoyi.bid.service;

import java.util.List;

import com.ruoyi.bid.domain.NewZbggIndex;


/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-05-02
 */
public interface INewZbggIndexService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param ggcode 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public NewZbggIndex selectNewZbggIndexByGgcode(String ggcode);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param newZbggIndex 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<NewZbggIndex> selectNewZbggIndexList(NewZbggIndex newZbggIndex);

    /**
     * 新增【请填写功能名称】
     * 
     * @param newZbggIndex 【请填写功能名称】
     * @return 结果
     */
    public int insertNewZbggIndex(NewZbggIndex newZbggIndex);

    /**
     * 修改【请填写功能名称】
     * 
     * @param newZbggIndex 【请填写功能名称】
     * @return 结果
     */
    public int updateNewZbggIndex(NewZbggIndex newZbggIndex);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ggcodes 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteNewZbggIndexByGgcodes(String[] ggcodes);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param ggcode 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteNewZbggIndexByGgcode(String ggcode);
}
