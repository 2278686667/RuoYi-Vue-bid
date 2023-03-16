package com.ruoyi.bid.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bid.mapper.TenderProjectMapper;
import com.ruoyi.bid.domain.TenderProject;
import com.ruoyi.bid.service.ITenderProjectService;

/**
 * 投标项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-24
 */
@Service
public class TenderProjectServiceImpl implements ITenderProjectService 
{
    @Autowired
    private TenderProjectMapper tenderProjectMapper;

    /**
     * 查询投标项目
     * 
     * @param tpId 投标项目主键
     * @return 投标项目
     */
    @Override
    public TenderProject selectTenderProjectByTpId(Long tpId)
    {
        return tenderProjectMapper.selectTenderProjectByTpId(tpId);
    }

    /**
     * 查询投标项目列表
     * 
     * @param tenderProject 投标项目
     * @return 投标项目
     */
    @Override
    public List<TenderProject> selectTenderProjectList(TenderProject tenderProject)
    {
        return tenderProjectMapper.selectTenderProjectList(tenderProject);
    }

    /**
     * 新增投标项目
     * 
     * @param tenderProject 投标项目
     * @return 结果
     */
    @Override
    public int insertTenderProject(TenderProject tenderProject)
    {
        return tenderProjectMapper.insertTenderProject(tenderProject);
    }

    /**
     * 修改投标项目
     * 
     * @param tenderProject 投标项目
     * @return 结果
     */
    @Override
    public int updateTenderProject(TenderProject tenderProject)
    {
        return tenderProjectMapper.updateTenderProject(tenderProject);
    }

    /**
     * 批量删除投标项目
     * 
     * @param tpIds 需要删除的投标项目主键
     * @return 结果
     */
    @Override
    public int deleteTenderProjectByTpIds(Long[] tpIds)
    {
        return tenderProjectMapper.deleteTenderProjectByTpIds(tpIds);
    }

    /**
     * 删除投标项目信息
     * 
     * @param tpId 投标项目主键
     * @return 结果
     */
    @Override
    public int deleteTenderProjectByTpId(Long tpId)
    {
        return tenderProjectMapper.deleteTenderProjectByTpId(tpId);
    }

    @Override
    public List<TenderProject> selectTenderProjectByProjId(Long[] projIds) {
        List<TenderProject> tenderProjectList=null;
        Long userId = SecurityUtils.getUserId();
        if (projIds.length>0){
            Map<String,Object> map=new HashMap<>();
            map.put("projIds",projIds);
            map.put("userId",userId);

//            tenderProjectList= tenderProjectMapper.selectTenderProjectByProjId(projIds);
            tenderProjectList= tenderProjectMapper.selectTenderProjectByMap(map);
        }

        return tenderProjectList;

    }

    @Override
    public TenderProject selectTenderProjectByProjIdOne(Long projId) {

        return tenderProjectMapper.selectTenderProjectByProjIdOne(projId);
    }
}
