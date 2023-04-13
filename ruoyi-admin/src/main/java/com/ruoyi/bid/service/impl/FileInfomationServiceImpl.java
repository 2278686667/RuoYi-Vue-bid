package com.ruoyi.bid.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bid.mapper.FileInfomationMapper;
import com.ruoyi.bid.domain.FileInfomation;
import com.ruoyi.bid.service.IFileInfomationService;

/**
 * 文件信息Service业务层处理
 * 
 * @author chb
 * @date 2023-02-17
 */
@Service
public class FileInfomationServiceImpl implements IFileInfomationService 
{
    @Autowired
    private FileInfomationMapper fileInfomationMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    @Override
    public FileInfomation selectFileInfomationByFileId(Long fileId)
    {
        return fileInfomationMapper.selectFileInfomationByFileId(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param fileInfomation 文件信息
     * @return 文件信息
     */
    @Override
    public List<FileInfomation> selectFileInfomationList(FileInfomation fileInfomation)
    {
        return fileInfomationMapper.selectFileInfomationList(fileInfomation);
    }

    /**
     * 新增文件信息
     * 
     * @param fileInfomation 文件信息
     * @return 结果
     */
    @Override
    public int insertFileInfomation(FileInfomation fileInfomation)
    {
        fileInfomation.setCreateTime(DateUtils.getNowDate());
        return fileInfomationMapper.insertFileInfomation(fileInfomation);
    }

    /**
     * 修改文件信息
     * 
     * @param fileInfomation 文件信息
     * @return 结果
     */
    @Override
    public int updateFileInfomation(FileInfomation fileInfomation)
    {
        fileInfomation.setUpdateTime(DateUtils.getNowDate());
        return fileInfomationMapper.updateFileInfomation(fileInfomation);
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteFileInfomationByFileIds(Long[] fileIds)
    {
        return fileInfomationMapper.deleteFileInfomationByFileIds(fileIds);
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteFileInfomationByFileId(Long fileId)
    {
        return fileInfomationMapper.deleteFileInfomationByFileId(fileId);
    }

    @Override
    public FileInfomation selectFileInfomationByFileIdAndProjId(Long fileId, Long projId) {
        List<FileInfomation> fileInfomationList=fileInfomationMapper.selectFileInfomationByFileIdAndProjId(fileId,projId);
        return fileInfomationList.get(0);
    }
}
