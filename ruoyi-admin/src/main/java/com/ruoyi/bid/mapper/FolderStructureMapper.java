package com.ruoyi.bid.mapper;

import java.util.List;
import com.ruoyi.bid.domain.FolderStructure;

/**
 * 文件夹结构信息Mapper接口
 * 
 * @author chb
 * @date 2023-01-31
 */
public interface FolderStructureMapper 
{
    /**
     * 查询文件夹结构信息
     * 
     * @param folderId 文件夹结构信息主键
     * @return 文件夹结构信息
     */
    public FolderStructure selectFolderStructureByFolderId(Long folderId);

    /**
     * 查询文件夹结构信息列表
     * 
     * @param folderStructure 文件夹结构信息
     * @return 文件夹结构信息集合
     */
    public List<FolderStructure> selectFolderStructureList(FolderStructure folderStructure);

    /**
     * 新增文件夹结构信息
     * 
     * @param folderStructure 文件夹结构信息
     * @return 结果
     */
    public int insertFolderStructure(FolderStructure folderStructure);

    /**
     * 修改文件夹结构信息
     * 
     * @param folderStructure 文件夹结构信息
     * @return 结果
     */
    public int updateFolderStructure(FolderStructure folderStructure);

    /**
     * 删除文件夹结构信息
     * 
     * @param folderId 文件夹结构信息主键
     * @return 结果
     */
    public int deleteFolderStructureByFolderId(Long folderId);

    /**
     * 批量删除文件夹结构信息
     * 
     * @param folderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFolderStructureByFolderIds(Long[] folderIds);

    //根据项目id查询文件柜详细信息
    FolderStructure selectFolderStructureByProjId(FolderStructure folderStructure);
}
