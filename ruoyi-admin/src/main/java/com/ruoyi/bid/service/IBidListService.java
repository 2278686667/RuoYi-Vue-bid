package com.ruoyi.bid.service;

import java.util.List;
import com.ruoyi.bid.domain.BidList;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 招投标Service接口
 * 
 * @author chb
 * @date 2022-11-27
 */
public interface IBidListService 
{
    /**
     * 查询招投标
     * 
     * @param projId 招投标主键
     * @return 招投标
     */
    public BidList selectBidListByProjId(Long projId);

    /**
     * 查询招投标列表
     * 
     * @param bidList 招投标
     * @return 招投标集合
     */
    public List<BidList> selectBidListList(BidList bidList);

    /**
     * 新增招投标
     * 
     * @param bidList 招投标
     * @return 结果
     */
    public int insertBidList(BidList bidList);

    /**
     * 修改招投标
     * 
     * @param bidList 招投标
     * @return 结果
     */
    public int updateBidList(BidList bidList);

    /**
     * 批量删除招投标
     * 
     * @param projIds 需要删除的招投标主键集合
     * @return 结果
     */
    public int deleteBidListByProjIds(Long[] projIds);

    /**
     * 删除招投标信息
     * 
     * @param projId 招投标主键
     * @return 结果
     */
    public int deleteBidListByProjId(Long projId);

    AjaxResult updateByStatus(BidList bidList);
}
