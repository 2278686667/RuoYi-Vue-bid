package com.ruoyi.bid.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件信息对象 file_infomation
 * 
 * @author ruoyi
 * @date 2023-02-17
 */
public class FileInfomation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件id */
    private Long fileId;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projId;

    /** 文件夹id */
    @Excel(name = "文件夹id")
    private Long folderId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件密码 */
    @Excel(name = "文件密码")
    private String filePwd;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long orderNum;

    /** 文件状态（0未加密 1 已解密） */
    @Excel(name = "文件状态", readConverterExp = "0=未加密,1=,已=解密")
    private String status;

    /** 是否删除(0-未删, 1-已删) */
    @Excel(name = "是否删除(0-未删, 1-已删)")
    private Long isDeleted;

    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setProjId(Long projId)
    {
        this.projId = projId;
    }

    public Long getProjId()
    {
        return projId;
    }
    public void setFolderId(Long folderId) 
    {
        this.folderId = folderId;
    }

    public Long getFolderId() 
    {
        return folderId;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setFilePwd(String filePwd) 
    {
        this.filePwd = filePwd;
    }

    public String getFilePwd() 
    {
        return filePwd;
    }
    public void setOrderNum(Long orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() 
    {
        return orderNum;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setIsDeleted(Long isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("projId", getProjId())
            .append("folderId", getFolderId())
            .append("fileName", getFileName())
            .append("filePath", getFilePath())
            .append("filePwd", getFilePwd())
            .append("orderNum", getOrderNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .append("remark", getRemark())
            .toString();
    }
}
