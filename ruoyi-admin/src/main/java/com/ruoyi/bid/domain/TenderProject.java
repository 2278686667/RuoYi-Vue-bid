package com.ruoyi.bid.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 投标项目对象 tender_project
 * 
 * @author ruoyi
 * @date 2023-02-24
 */
public class TenderProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long tpId;

    /** 投标用户id */
    @Excel(name = "投标用户id")
    private Long bidderId;

    /** 招标项目id */
    @Excel(name = "招标项目id")
    private Long projId;

    /** 文件id */
    @Excel(name = "文件id")
    private Long fielId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projName;

    /** 招标编号 */
    @Excel(name = "招标编号")
    private String projNumber;

    /** 采购人 */
    @Excel(name = "采购人")
    private String purchaser;

    /** 投标公司名称 */
    @Excel(name = "投标公司名称")
    private String bidderCompany;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 上传人 */
    @Excel(name = "上传人")
    private String uploadBy;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /** 文件状态（0未上传 1已上传） */
    @Excel(name = "文件状态", readConverterExp = "0=未上传,1=已上传")
    private String status;

    private String bidStatus;


    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }

    public void setTpId(Long tpId)
    {
        this.tpId = tpId;
    }

    public Long getTpId() 
    {
        return tpId;
    }
    public void setBidderId(Long bidderId) 
    {
        this.bidderId = bidderId;
    }

    public Long getBidderId() 
    {
        return bidderId;
    }
    public void setProjId(Long projId) 
    {
        this.projId = projId;
    }

    public Long getProjId() 
    {
        return projId;
    }
    public void setFielId(Long fielId) 
    {
        this.fielId = fielId;
    }

    public Long getFielId() 
    {
        return fielId;
    }
    public void setProjName(String projName) 
    {
        this.projName = projName;
    }

    public String getProjName() 
    {
        return projName;
    }
    public void setProjNumber(String projNumber) 
    {
        this.projNumber = projNumber;
    }

    public String getProjNumber() 
    {
        return projNumber;
    }
    public void setPurchaser(String purchaser) 
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser() 
    {
        return purchaser;
    }
    public void setBidderCompany(String bidderCompany) 
    {
        this.bidderCompany = bidderCompany;
    }

    public String getBidderCompany() 
    {
        return bidderCompany;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setUploadBy(String uploadBy) 
    {
        this.uploadBy = uploadBy;
    }

    public String getUploadBy() 
    {
        return uploadBy;
    }
    public void setUploadTime(Date uploadTime) 
    {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() 
    {
        return uploadTime;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tpId", getTpId())
            .append("bidderId", getBidderId())
            .append("projId", getProjId())
            .append("fielId", getFielId())
            .append("projName", getProjName())
            .append("projNumber", getProjNumber())
            .append("purchaser", getPurchaser())
            .append("bidderCompany", getBidderCompany())
            .append("fileName", getFileName())
            .append("uploadBy", getUploadBy())
            .append("uploadTime", getUploadTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .toString();
    }
}
