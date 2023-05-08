package com.ruoyi.bid.service.impl;

import java.util.List;

import com.ruoyi.bid.domain.NewZbggIndex;
import com.ruoyi.bid.mapper.NewZbggIndexMapper;
import com.ruoyi.bid.service.INewZbggIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-02
 */
@Service
public class NewZbggIndexServiceImpl implements INewZbggIndexService
{
    @Autowired
    private NewZbggIndexMapper newZbggIndexMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param ggcode 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public NewZbggIndex selectNewZbggIndexByGgcode(String ggcode)
    {
        return newZbggIndexMapper.selectNewZbggIndexByGgcode(ggcode);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param newZbggIndex 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<NewZbggIndex> selectNewZbggIndexList(NewZbggIndex newZbggIndex)
    {
        return newZbggIndexMapper.selectNewZbggIndexList(newZbggIndex);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param newZbggIndex 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertNewZbggIndex(NewZbggIndex newZbggIndex)
    {
        return newZbggIndexMapper.insertNewZbggIndex(newZbggIndex);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param newZbggIndex 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateNewZbggIndex(NewZbggIndex newZbggIndex)
    {
        return newZbggIndexMapper.updateNewZbggIndex(newZbggIndex);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ggcodes 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteNewZbggIndexByGgcodes(String[] ggcodes)
    {
        return newZbggIndexMapper.deleteNewZbggIndexByGgcodes(ggcodes);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param ggcode 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteNewZbggIndexByGgcode(String ggcode)
    {
        return newZbggIndexMapper.deleteNewZbggIndexByGgcode(ggcode);
    }
}
