package com.ruoyi.bid.mapper;

import java.util.List;
import com.ruoyi.bid.domain.BidList;

/**
 * 招投标Mapper接口
 * 
 * @author chb
 * @date 2022-11-27
 */
public interface BidListMapper 
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
     * 删除招投标
     * 
     * @param projId 招投标主键
     * @return 结果
     */
    public int deleteBidListByProjId(Long projId);

    /**
     * 批量删除招投标
     * 
     * @param projIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBidListByProjIds(Long[] projIds);

    Integer updateByStatus(BidList bidList);
}
