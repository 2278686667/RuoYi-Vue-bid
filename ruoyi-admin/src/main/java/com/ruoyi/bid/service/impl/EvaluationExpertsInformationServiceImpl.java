package com.ruoyi.bid.service.impl;

import java.util.List;

import com.ruoyi.bid.domain.EvaluationExpertsInformation;
import com.ruoyi.bid.mapper.EvaluationExpertsInformationMapper;
import com.ruoyi.bid.service.IEvaluationExpertsInformationService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 评审专家信息Service业务层处理
 * 
 * @author chb
 * @date 2023-02-23
 */
@Service
public class EvaluationExpertsInformationServiceImpl implements IEvaluationExpertsInformationService
{
    @Autowired
    private EvaluationExpertsInformationMapper evaluationExpertsInformationMapper;

    /**
     * 查询评审专家信息
     * 
     * @param reviewId 评审专家信息主键
     * @return 评审专家信息
     */
    @Override
    public EvaluationExpertsInformation selectEvaluationExpertsInformationByReviewId(Long reviewId)
    {
        return evaluationExpertsInformationMapper.selectEvaluationExpertsInformationByReviewId(reviewId);
    }

    /**
     * 查询评审专家信息列表
     * 
     * @param evaluationExpertsInformation 评审专家信息
     * @return 评审专家信息
     */
    @Override
    public List<EvaluationExpertsInformation> selectEvaluationExpertsInformationList(EvaluationExpertsInformation evaluationExpertsInformation)
    {
        return evaluationExpertsInformationMapper.selectEvaluationExpertsInformationList(evaluationExpertsInformation);
    }

    /**
     * 新增评审专家信息
     * 
     * @param evaluationExpertsInformation 评审专家信息
     * @return 结果
     */
    @Override
    public int insertEvaluationExpertsInformation(EvaluationExpertsInformation evaluationExpertsInformation)
    {
        evaluationExpertsInformation.setCreateTime(DateUtils.getNowDate());
        evaluationExpertsInformation.setStatus("0");
        evaluationExpertsInformation.setSignIn("0");
        return evaluationExpertsInformationMapper.insertEvaluationExpertsInformation(evaluationExpertsInformation);
    }

    /**
     * 修改评审专家信息
     * 
     * @param evaluationExpertsInformation 评审专家信息
     * @return 结果
     */
    @Override
    public int updateEvaluationExpertsInformation(EvaluationExpertsInformation evaluationExpertsInformation)
    {
        evaluationExpertsInformation.setUpdateTime(DateUtils.getNowDate());
        return evaluationExpertsInformationMapper.updateEvaluationExpertsInformation(evaluationExpertsInformation);
    }

    /**
     * 批量删除评审专家信息
     * 
     * @param reviewIds 需要删除的评审专家信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluationExpertsInformationByReviewIds(Long[] reviewIds)
    {
        return evaluationExpertsInformationMapper.deleteEvaluationExpertsInformationByReviewIds(reviewIds);
    }

    /**
     * 删除评审专家信息信息
     * 
     * @param reviewId 评审专家信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluationExpertsInformationByReviewId(Long reviewId)
    {
        return evaluationExpertsInformationMapper.deleteEvaluationExpertsInformationByReviewId(reviewId);
    }
}
