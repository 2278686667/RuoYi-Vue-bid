package com.ruoyi.bid.mapper;

import com.ruoyi.bid.domain.EvaluationExpertsInformation;

import java.util.List;

/**
 * 评审专家信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-23
 */
public interface EvaluationExpertsInformationMapper 
{
    /**
     * 查询评审专家信息
     * 
     * @param reviewId 评审专家信息主键
     * @return 评审专家信息
     */
    public EvaluationExpertsInformation selectEvaluationExpertsInformationByReviewId(Long reviewId);

    /**
     * 查询评审专家信息列表
     * 
     * @param evaluationExpertsInformation 评审专家信息
     * @return 评审专家信息集合
     */
    public List<EvaluationExpertsInformation> selectEvaluationExpertsInformationList(EvaluationExpertsInformation evaluationExpertsInformation);

    /**
     * 新增评审专家信息
     * 
     * @param evaluationExpertsInformation 评审专家信息
     * @return 结果
     */
    public int insertEvaluationExpertsInformation(EvaluationExpertsInformation evaluationExpertsInformation);

    /**
     * 修改评审专家信息
     * 
     * @param evaluationExpertsInformation 评审专家信息
     * @return 结果
     */
    public int updateEvaluationExpertsInformation(EvaluationExpertsInformation evaluationExpertsInformation);

    /**
     * 删除评审专家信息
     * 
     * @param reviewId 评审专家信息主键
     * @return 结果
     */
    public int deleteEvaluationExpertsInformationByReviewId(Long reviewId);

    /**
     * 批量删除评审专家信息
     * 
     * @param reviewIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEvaluationExpertsInformationByReviewIds(Long[] reviewIds);
}
