package com.ruoyi.bid.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import java.util.List;

import com.ruoyi.bid.domain.EvaluationExpertsInformation;
import com.ruoyi.bid.mapper.EvaluationExpertsInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bid.mapper.MarkSheetMapper;
import com.ruoyi.bid.domain.MarkSheet;
import com.ruoyi.bid.service.IMarkSheetService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评分表Service业务层处理
 *
 * @author chb
 * @date 2023-04-15
 */
@Service
public class MarkSheetServiceImpl implements IMarkSheetService {
    @Autowired
    private MarkSheetMapper markSheetMapper;

    @Autowired
    private EvaluationExpertsInformationMapper evaluationExpertsInformationMapper;

    /**
     * 查询评分表
     *
     * @param id 评分表主键
     * @return 评分表
     */
    @Override
    public MarkSheet selectMarkSheetById(Long id) {
        return markSheetMapper.selectMarkSheetById(id);
    }

    /**
     * 查询评分表列表
     *
     * @param markSheet 评分表
     * @return 评分表
     */
    @Override
    public List<MarkSheet> selectMarkSheetList(MarkSheet markSheet) {
        return markSheetMapper.selectMarkSheetList(markSheet);
    }

    /**
     * 新增评分表
     *
     * @param markSheet 评分表
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMarkSheet(MarkSheet markSheet) {

        BigDecimal price = markSheet.getPrice();
        BigDecimal business = markSheet.getBusiness();
        BigDecimal techology = markSheet.getTechology();
        BigDecimal after = markSheet.getAfter();

        BigDecimal divide = price.add(business).add(techology).add(after).divide(BigDecimal.valueOf(4));
        markSheet.setTotal(divide);
        Long reviewId = markSheet.getReviewId();

        EvaluationExpertsInformation evaluationExpertsInformation = new EvaluationExpertsInformation();
        evaluationExpertsInformation.setReviewId(reviewId);
        evaluationExpertsInformation.setStatus("1");
        int i = evaluationExpertsInformationMapper.updateEvaluationExpertsInformation(evaluationExpertsInformation);

        return markSheetMapper.insertMarkSheet(markSheet);
    }

    /**
     * 修改评分表
     *
     * @param markSheet 评分表
     * @return 结果
     */
    @Override
    public int updateMarkSheet(MarkSheet markSheet) {
        return markSheetMapper.updateMarkSheet(markSheet);
    }

    /**
     * 批量删除评分表
     *
     * @param ids 需要删除的评分表主键
     * @return 结果
     */
    @Override
    public int deleteMarkSheetByIds(Long[] ids) {
        return markSheetMapper.deleteMarkSheetByIds(ids);
    }

    /**
     * 删除评分表信息
     *
     * @param id 评分表主键
     * @return 结果
     */
    @Override
    public int deleteMarkSheetById(Long id) {
        return markSheetMapper.deleteMarkSheetById(id);
    }
}
