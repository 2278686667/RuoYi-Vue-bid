package com.ruoyi.bid.service;

import java.util.List;
import com.ruoyi.bid.domain.FileInfomation;

/**
 * 文件信息Service接口
 * 
 * @author chb
 * @date 2023-02-17
 */
public interface IFileInfomationService 
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
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息主键集合
     * @return 结果
     */
    public int deleteFileInfomationByFileIds(Long[] fileIds);

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
    public int deleteFileInfomationByFileId(Long fileId);

    /**
     * 根据fileid和projid下载文件
     * @param fileId
     * @param projId
     * @return
     */
    FileInfomation selectFileInfomationByFileIdAndProjId(Long fileId, Long projId);
}
