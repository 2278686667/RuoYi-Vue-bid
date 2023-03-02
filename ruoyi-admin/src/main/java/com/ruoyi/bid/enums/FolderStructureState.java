package com.ruoyi.bid.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FolderStructureState {


    /**
     * 招标类文件
     */
    BIDDING_DOCUMENT("招标类文件"),

    /**
     * 投标文件
     */
    TENDER_DOCUMENT("投标文件"),
    /**
     * 评审文件
     */
    REVIEW_DOCUMENT("评审文件"),
    /**
     * 阅读文件
     */
    READ_DOCUMENT("阅读文件");
    /**
     * 类型
     */
    private final String type;
}
