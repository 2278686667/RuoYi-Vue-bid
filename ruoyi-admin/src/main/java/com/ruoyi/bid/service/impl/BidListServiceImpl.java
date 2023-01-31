package com.ruoyi.bid.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bid.mapper.BidListMapper;
import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.service.IBidListService;

/**
 * 招投标Service业务层处理
 * 
 * @author chb
 * @date 2022-11-27
 */
@Service
public class BidListServiceImpl implements IBidListService 
{
    @Autowired
    private BidListMapper bidListMapper;

    /**
     * 查询招投标
     * 
     * @param projId 招投标主键
     * @return 招投标
     */
    @Override
    public BidList selectBidListByProjId(Long projId)
    {
        return bidListMapper.selectBidListByProjId(projId);
    }

    /**
     * 查询招投标列表
     * 
     * @param bidList 招投标
     * @return 招投标
     */
    @Override
    public List<BidList> selectBidListList(BidList bidList)
    {
        return bidListMapper.selectBidListList(bidList);
    }

    /**
     * 新增招投标
     * 
     * @param bidList 招投标
     * @return 结果
     */
    @Override
    public int insertBidList(BidList bidList)
    {
        bidList.setCreateTime(DateUtils.getNowDate());
        return bidListMapper.insertBidList(bidList);
    }

    /**
     * 修改招投标
     * 
     * @param bidList 招投标
     * @return 结果
     */
    @Override
    public int updateBidList(BidList bidList)
    {
        bidList.setUpdateTime(DateUtils.getNowDate());
        return bidListMapper.updateBidList(bidList);
    }

    /**
     * 批量删除招投标
     * 
     * @param projIds 需要删除的招投标主键
     * @return 结果
     */
    @Override
    public int deleteBidListByProjIds(Long[] projIds)
    {
        return bidListMapper.deleteBidListByProjIds(projIds);
    }

    /**
     * 删除招投标信息
     * 
     * @param projId 招投标主键
     * @return 结果
     */
    @Override
    public int deleteBidListByProjId(Long projId)
    {
        return bidListMapper.deleteBidListByProjId(projId);
    }

    /**
     * status状态自增
     * @param bidList
     * @return
     */
    @Override
    public AjaxResult updateByStatus(BidList bidList) {

        bidList.setStatus((Integer.valueOf(bidList.getStatus())+1)+"");
//        时间大于0表似乎时间过期了，
        if (bidList.getProjEnd().compareTo(new Date())<0){
            System.out.println(bidList.getProjEnd().compareTo(new Date()));

            return AjaxResult.error("时间过期了，修改投标截止时间");
        }
        bidListMapper.updateByStatus(bidList);
        return AjaxResult.success("发售成功");
    }
}
