package com.ruoyi.bid.common.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationExpertsInfomationVO {
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

    private Integer bidStatus;

    private String isGroupLeaders;

    /** id */
    private Long tpId;

    /** 投标用户id */
    @Excel(name = "投标用户id")
    private Long bidderId;


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

}
