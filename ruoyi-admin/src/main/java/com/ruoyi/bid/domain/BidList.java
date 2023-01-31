package com.ruoyi.bid.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 招投标对象 bid_list
 * 
 * @author chb
 * @date 2022-11-27
 */
public class BidList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目id */
    private Long projId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projName;

    /** 招标编号 */
    @Excel(name = "招标编号")
    private String projNumber;

    /** 采购代理机构 */
    @Excel(name = "采购代理机构")
    private String purchaserOrg;

    /** 采购人 */
    @Excel(name = "采购人")
    private String purchaser;

    /** 招标文件 */
    @Excel(name = "招标文件")
    private String projTender;

    /** 投标文件模板 */
    @Excel(name = "投标文件模板")
    private String tenderTemp;

    /** 提取码 */
    @Excel(name = "提取码")
    private String projPwd;

    /** 投标截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投标截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date projEnd;

    /** 项目状态（0待发售 1发售中 2开标中 3 待评标 4评审中 5评审完成 6已终止 7已完成 */
    @Excel(name = "项目状态", readConverterExp = "项目状态（0待发售 1发售中 2开标中 3 待评标 4评审中 5评审完成 6已终止 7已完成")
    private String status;

    public void setProjId(Long projId) 
    {
        this.projId = projId;
    }

    public Long getProjId() 
    {
        return projId;
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
    public void setPurchaserOrg(String purchaserOrg) 
    {
        this.purchaserOrg = purchaserOrg;
    }

    public String getPurchaserOrg() 
    {
        return purchaserOrg;
    }
    public void setPurchaser(String purchaser) 
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser() 
    {
        return purchaser;
    }
    public void setProjTender(String projTender) 
    {
        this.projTender = projTender;
    }

    public String getProjTender() 
    {
        return projTender;
    }
    public void setTenderTemp(String tenderTemp) 
    {
        this.tenderTemp = tenderTemp;
    }

    public String getTenderTemp() 
    {
        return tenderTemp;
    }
    public void setProjPwd(String projPwd) 
    {
        this.projPwd = projPwd;
    }

    public String getProjPwd() 
    {
        return projPwd;
    }
    public void setProjEnd(Date projEnd) 
    {
        this.projEnd = projEnd;
    }

    public Date getProjEnd() 
    {
        return projEnd;
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
            .append("projId", getProjId())
            .append("projName", getProjName())
            .append("projNumber", getProjNumber())
            .append("purchaserOrg", getPurchaserOrg())
            .append("purchaser", getPurchaser())
            .append("projTender", getProjTender())
            .append("tenderTemp", getTenderTemp())
            .append("projPwd", getProjPwd())
            .append("projEnd", getProjEnd())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
