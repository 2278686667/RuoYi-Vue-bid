package com.ruoyi.bid.service.impl;

import com.ruoyi.bid.TreeSelect;
import com.ruoyi.bid.domain.FolderStructure;
import com.ruoyi.bid.mapper.FolderStructureMapper;
import com.ruoyi.bid.service.IFolderStructureService;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件夹结构信息Service业务层处理
 * 
 * @author chb
 * @date 2023-01-31
 */
@Service
public class FolderStructureServiceImpl implements IFolderStructureService 
{
    @Autowired
    private FolderStructureMapper folderStructureMapper;

    /**
     * 查询文件夹结构信息
     * 
     * @param folderId 文件夹结构信息主键
     * @return 文件夹结构信息
     */
    @Override
    public FolderStructure selectFolderStructureByFolderId(Long folderId)
    {
        return folderStructureMapper.selectFolderStructureByFolderId(folderId);
    }

    /**
     * 查询文件夹结构信息列表
     * 
     * @param folderStructure 文件夹结构信息
     * @return 文件夹结构信息
     */
    @Override
    public List<FolderStructure> selectFolderStructureList(FolderStructure folderStructure)
    {
        return folderStructureMapper.selectFolderStructureList(folderStructure);
    }

    /**
     * 新增文件夹结构信息
     * 
     * @param folderStructure 文件夹结构信息
     * @return 结果
     */
    @Override
    public int insertFolderStructure(FolderStructure folderStructure)
    {
        folderStructure.setCreateTime(DateUtils.getNowDate());
        return folderStructureMapper.insertFolderStructure(folderStructure);
    }

    /**
     * 修改文件夹结构信息
     * 
     * @param folderStructure 文件夹结构信息
     * @return 结果
     */
    @Override
    public int updateFolderStructure(FolderStructure folderStructure)
    {
        folderStructure.setUpdateTime(DateUtils.getNowDate());
        return folderStructureMapper.updateFolderStructure(folderStructure);
    }

    /**
     * 批量删除文件夹结构信息
     * 
     * @param folderIds 需要删除的文件夹结构信息主键
     * @return 结果
     */
    @Override
    public int deleteFolderStructureByFolderIds(Long[] folderIds)
    {
        return folderStructureMapper.deleteFolderStructureByFolderIds(folderIds);
    }

    /**
     * 删除文件夹结构信息信息
     * 
     * @param folderId 文件夹结构信息主键
     * @return 结果
     */
    @Override
    public int deleteFolderStructureByFolderId(Long folderId)
    {
        return folderStructureMapper.deleteFolderStructureByFolderId(folderId);
    }

    @Override
    public List<TreeSelect> selectFoldTreeList(FolderStructure folderStructure) {
        List<FolderStructure> folderStructures=this.selectFolderStructureList(folderStructure);

        return buildFoldTreeSelect(folderStructures);
    }

    @Override
    public FolderStructure selectFolderByProjId(FolderStructure folderStructure) {
        return folderStructureMapper.selectFolderStructureByProjId(folderStructure);
    }

    private List<TreeSelect> buildFoldTreeSelect(List<FolderStructure> folderStructures) {
        List<FolderStructure> foldTree = buildFoldTree(folderStructures);
        return foldTree.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    private List<FolderStructure> buildFoldTree(List<FolderStructure> folderStructures) {
        List<FolderStructure> returnList=new ArrayList<>();
        List<Long> tempList=new ArrayList<>();
        for (FolderStructure folderStructure : folderStructures) {
            tempList.add(folderStructure.getFolderId());
        }
        for (FolderStructure folderStructure : folderStructures) {
            //如果是顶级节点，遍历该父节点的所有子节点
            if (!tempList.contains(folderStructure.getParentId())){
                recursionFn(folderStructures,folderStructure);
                returnList.add(folderStructure);
            }
        }
        if(returnList.isEmpty()){
            returnList=folderStructures;
        }
        return returnList;
    }

    /**
     * 递归列表
     * @param folderStructures
     * @param folderStructure
     */
    private void recursionFn(List<FolderStructure> folderStructures, FolderStructure folderStructure) {
        //得到子节点列表
        List<FolderStructure> childList = getChildList(folderStructures, folderStructure);
        folderStructure.setChildren(childList);
        for (FolderStructure tChild : childList) {
            //判断是否有子节点
            if (hasChild(folderStructures,tChild)){
                recursionFn(folderStructures,tChild);
            }
        }
    }

    /**
     * 判断是否有子节点
     * @param folderStructures
     * @param tChild
     * @return
     */
    private boolean hasChild(List<FolderStructure> folderStructures, FolderStructure tChild) {
        return getChildList(folderStructures,tChild).size()>0;
    }

    /**
     * 得到子节点列表
     * @param folderStructures
     * @param folderStructure
     */
    private List<FolderStructure> getChildList(List<FolderStructure> folderStructures, FolderStructure folderStructure) {
        List<FolderStructure> tList=new ArrayList<>();
        Iterator<FolderStructure> it = folderStructures.iterator();
        while (it.hasNext()){
            FolderStructure n = it.next();
            //如果有父节点id和当前id相同，记录
            if (StringUtils.isNotNull(n.getParentId())&&n.getParentId().longValue()==folderStructure.getFolderId().longValue()){
                tList.add(n);
            }
        }
        return tList;
    }


}
