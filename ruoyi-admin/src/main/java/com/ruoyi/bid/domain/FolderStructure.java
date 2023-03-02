package com.ruoyi.bid.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹结构信息对象 folder_structure
 * 
 * @author chb
 * @date 2023-01-31
 */
public class FolderStructure extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long folderId;

    /** 父级文件夹id */
    @Excel(name = "父级文件夹id")
    private Long parentId;

    /** 项目id */
    @Excel(name = "项目id")
    private String projId;

    /** 文件夹名称 */
    @Excel(name = "文件夹名称")
    private String folderName;

    /** 文件存储路径 */
    @Excel(name = "文件存储路径")
    private String folderPath;

    /** 文件状态（0未归档 1 已归档） */
    @Excel(name = "文件状态", readConverterExp = "0=未归档,1=,已=归档")
    private String status;

    /** 是否删除(0-未删, 1-已删) */
    @Excel(name = "是否删除(0-未删, 1-已删)")
    private Long isDeleted;
    /**
     * 子部门
     */
    private List<FolderStructure> children=new ArrayList<>();

    public void setFolderId(Long folderId) 
    {
        this.folderId = folderId;
    }

    public Long getFolderId() 
    {
        return folderId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setProjId(String projId) 
    {
        this.projId = projId;
    }

    public String getProjId() 
    {
        return projId;
    }
    public void setFolderName(String folderName) 
    {
        this.folderName = folderName;
    }

    public String getFolderName() 
    {
        return folderName;
    }
    public void setFolderPath(String folderPath) 
    {
        this.folderPath = folderPath;
    }

    public String getFolderPath() 
    {
        return folderPath;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<FolderStructure> getChildren() {
        return children;
    }

    public void setChildren(List<FolderStructure> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("folderId", getFolderId())
            .append("parentId", getParentId())
            .append("projId", getProjId())
            .append("folderName", getFolderName())
            .append("folderPath", getFolderPath())
            .append("status", getStatus())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .append("remark", getRemark())
            .toString();
    }
}
