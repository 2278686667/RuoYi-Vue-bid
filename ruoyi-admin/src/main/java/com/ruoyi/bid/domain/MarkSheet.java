package com.ruoyi.bid.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评分表对象 mark_sheet
 *
 * @author chb
 * @date 2023-04-15
 */
public class MarkSheet extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 价格因素
     */
    @Excel(name = "价格因素")
    private BigDecimal price;

    /**
     * 商务因素
     */
    @Excel(name = "商务因素")
    private BigDecimal business;

    /**
     * 技术因素
     */
    @Excel(name = "技术因素")
    private BigDecimal techology;

    /**
     * 售后服务
     */
    @Excel(name = "售后服务")
    private BigDecimal after;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private Long projId;

    /**
     * 专家
     */
    @Excel(name = "专家")
    private Long reviewId;

    private BigDecimal total;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setBusiness(BigDecimal business) {
        this.business = business;
    }

    public BigDecimal getBusiness() {
        return business;
    }

    public void setTechology(BigDecimal techology) {
        this.techology = techology;
    }

    public BigDecimal getTechology() {
        return techology;
    }

    public void setAfter(BigDecimal after) {
        this.after = after;
    }

    public BigDecimal getAfter() {
        return after;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("price", getPrice())
                .append("business", getBusiness())
                .append("techology", getTechology())
                .append("after", getAfter())
                .append("projId", getProjId())
                .append("reviewId", getReviewId())
                .toString();
    }
}
