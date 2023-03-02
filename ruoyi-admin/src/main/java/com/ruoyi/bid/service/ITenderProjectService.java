package com.ruoyi.bid.service;

import java.util.List;
import com.ruoyi.bid.domain.TenderProject;

/**
 * 投标项目Service接口
 * 
 * @author ruoyi
 * @date 2023-02-24
 */
public interface ITenderProjectService 
{
    /**
     * 查询投标项目
     * 
     * @param tpId 投标项目主键
     * @return 投标项目
     */
    public TenderProject selectTenderProjectByTpId(Long tpId);

    /**
     * 查询投标项目列表
     * 
     * @param tenderProject 投标项目
     * @return 投标项目集合
     */
    public List<TenderProject> selectTenderProjectList(TenderProject tenderProject);

    /**
     * 新增投标项目
     * 
     * @param tenderProject 投标项目
     * @return 结果
     */
    public int insertTenderProject(TenderProject tenderProject);

    /**
     * 修改投标项目
     * 
     * @param tenderProject 投标项目
     * @return 结果
     */
    public int updateTenderProject(TenderProject tenderProject);

    /**
     * 批量删除投标项目
     * 
     * @param tpIds 需要删除的投标项目主键集合
     * @return 结果
     */
    public int deleteTenderProjectByTpIds(Long[] tpIds);

    /**
     * 删除投标项目信息
     * 
     * @param tpId 投标项目主键
     * @return 结果
     */
    public int deleteTenderProjectByTpId(Long tpId);

    List<TenderProject> selectTenderProjectByProjId(Long[] projId);


    TenderProject selectTenderProjectByProjIdOne(Long projId);
}
