package com.ruoyi.bid.mapper;

import java.util.List;
import com.ruoyi.bid.domain.FileInfomation;
import org.apache.ibatis.annotations.Param;

/**
 * 文件信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-17
 */
public interface FileInfomationMapper 
{
    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public FileInfomation selectFileInfomationByFileId(Long fileId);

    /**
     * 查询文件信息列表
     * 
     * @param fileInfomation 文件信息
     * @return 文件信息集合
     */
    public List<FileInfomation> selectFileInfomationList(FileInfomation fileInfomation);

    /**
     * 新增文件信息
     * 
     * @param fileInfomation 文件信息
     * @return 结果
     */
    public int insertFileInfomation(FileInfomation fileInfomation);

    /**
     * 修改文件信息
     * 
     * @param fileInfomation 文件信息
     * @return 结果
     */
    public int updateFileInfomation(FileInfomation fileInfomation);

    /**
     * 删除文件信息
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
    public int deleteFileInfomationByFileId(Long fileId);

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFileInfomationByFileIds(Long[] fileIds);

    /**
     * 根据文件夹id查询所有信息
     * @param folderId 文件夹id
     * @return
     */
    List<FileInfomation> selectFileInfomationByFolderId(Long folderId);

    /**
     * 根据文件夹id查询所有信息
     * @param folderId 文件夹id
     * @return
     */
    List<FileInfomation> selectFileInfomationByFolderIdAndProjId(@Param("folderId") Long folderId,@Param("projId") String projId);
}
