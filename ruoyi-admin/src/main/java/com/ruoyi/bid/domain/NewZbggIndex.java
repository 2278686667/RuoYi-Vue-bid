package com.ruoyi.bid.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 new_zbgg_index
 * 
 * @author ruoyi
 * @date 2023-05-02
 */
public class NewZbggIndex extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告编号必须以SD开头后面用guid即可保证在咱们的表里面不重复，必要 */
    @Excel(name = "公告编号必须以SD开头后面用guid即可保证在咱们的表里面不重复，必要")
    private String ggcode;

    /** 公告名称，必要 */
    @Excel(name = "公告名称，必要")
    private String ggname;

    /** 公告发布时间，必要 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "公告发布时间，必要", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ggfbtime;

    /** 行业名称，必要 */
    @Excel(name = "行业名称，必要")
    private String hyname;

    /** 招标人名称，非必要 */
    @Excel(name = "招标人名称，非必要")
    private String zbr;

    /** 招标代理名称，非必要 */
    @Excel(name = "招标代理名称，非必要")
    private String zbdl;

    /** 企业采购标记，非必要 */
    @Excel(name = "企业采购标记，非必要")
    private String govchild;

    /** 公告类型（zbgg招标公告），必要公告类型名称，必要 */
    @Excel(name = "公告类型", readConverterExp = "z=bgg招标公告")
    private String ggtype;

    /** 公告类型名称，必要 */
    @Excel(name = "公告类型名称，必要")
    private String typename;

    /** 公告截至日期，必要 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "公告截至日期，必要", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ggendtime;

    /** 公告内容富文本，必要 */
    @Excel(name = "公告内容富文本，必要")
    private String ggcontent;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date addtime;

    public void setGgcode(String ggcode) 
    {
        this.ggcode = ggcode;
    }

    public String getGgcode() 
    {
        return ggcode;
    }
    public void setGgname(String ggname) 
    {
        this.ggname = ggname;
    }

    public String getGgname() 
    {
        return ggname;
    }
    public void setGgfbtime(Date ggfbtime) 
    {
        this.ggfbtime = ggfbtime;
    }

    public Date getGgfbtime() 
    {
        return ggfbtime;
    }
    public void setHyname(String hyname) 
    {
        this.hyname = hyname;
    }

    public String getHyname() 
    {
        return hyname;
    }
    public void setZbr(String zbr) 
    {
        this.zbr = zbr;
    }

    public String getZbr() 
    {
        return zbr;
    }
    public void setZbdl(String zbdl) 
    {
        this.zbdl = zbdl;
    }

    public String getZbdl() 
    {
        return zbdl;
    }
    public void setGovchild(String govchild) 
    {
        this.govchild = govchild;
    }

    public String getGovchild() 
    {
        return govchild;
    }
    public void setGgtype(String ggtype) 
    {
        this.ggtype = ggtype;
    }

    public String getGgtype() 
    {
        return ggtype;
    }
    public void setTypename(String typename) 
    {
        this.typename = typename;
    }

    public String getTypename() 
    {
        return typename;
    }
    public void setGgendtime(Date ggendtime) 
    {
        this.ggendtime = ggendtime;
    }

    public Date getGgendtime() 
    {
        return ggendtime;
    }
    public void setGgcontent(String ggcontent) 
    {
        this.ggcontent = ggcontent;
    }

    public String getGgcontent() 
    {
        return ggcontent;
    }
    public void setAddtime(Date addtime) 
    {
        this.addtime = addtime;
    }

    public Date getAddtime() 
    {
        return addtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ggcode", getGgcode())
            .append("ggname", getGgname())
            .append("ggfbtime", getGgfbtime())
            .append("hyname", getHyname())
            .append("zbr", getZbr())
            .append("zbdl", getZbdl())
            .append("govchild", getGovchild())
            .append("ggtype", getGgtype())
            .append("typename", getTypename())
            .append("ggendtime", getGgendtime())
            .append("ggcontent", getGgcontent())
            .append("addtime", getAddtime())
            .toString();
    }
}
