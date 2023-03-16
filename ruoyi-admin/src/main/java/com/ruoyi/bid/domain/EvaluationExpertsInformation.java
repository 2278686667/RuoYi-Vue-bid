package com.ruoyi.bid.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评审专家信息对象 evaluation_experts_information
 * 
 * @author ruoyi
 * @date 2023-02-23
 */
public class EvaluationExpertsInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评审id */
    private Long reviewId;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projId;

    /** 评审专家Id */
    @Excel(name = "评审专家Id")
    private Long userId;

    /** 是否签到（0 未签到 1 已签到） */
    @Excel(name = "是否签到", readConverterExp = "0=,未=签到,1=,已=签到")
    private String signIn;

    /** 文件id */
    @Excel(name = "文件id")
    private Long fileId;

    /** 评审状态（0未提交 1 已提交） */
    @Excel(name = "评审状态", readConverterExp = "0=未提交,1=,已=提交")
    private String status;

    /** 是否删除(0-未删, 1-已删) */
    @Excel(name = "是否删除(0-未删, 1-已删)")
    private Long isDeleted;
    /** 是否删除(0-未删, 1-已删) */
    @Excel(name = "项目状态（0待发售 1发售中 2开标中 3 待评标 4评审中 5评审完成 6已终止 7已完成")
    private Integer bidStatus;

    private String isGroupLeaders;

    public String getIsGroupLeaders() {
        return isGroupLeaders;
    }

    public void setIsGroupLeaders(String isGroupLeaders) {
        this.isGroupLeaders = isGroupLeaders;
    }

    public Integer getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(Integer bidStatus) {
        this.bidStatus = bidStatus;
    }

    public void setReviewId(Long reviewId)
    {
        this.reviewId = reviewId;
    }

    public Long getReviewId() 
    {
        return reviewId;
    }
    public void setProjId(Long projId) 
    {
        this.projId = projId;
    }

    public Long getProjId() 
    {
        return projId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setSignIn(String signIn) 
    {
        this.signIn = signIn;
    }

    public String getSignIn() 
    {
        return signIn;
    }
    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
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
            .append("reviewId", getReviewId())
            .append("projId", getProjId())
            .append("userId", getUserId())
            .append("signIn", getSignIn())
            .append("fileId", getFileId())
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
