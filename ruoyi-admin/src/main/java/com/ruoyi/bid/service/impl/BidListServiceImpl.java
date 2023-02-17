package com.ruoyi.bid.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.bid.domain.FileInfomation;
import com.ruoyi.bid.domain.FolderStructure;
import com.ruoyi.bid.enums.FolderStructureState;
import com.ruoyi.bid.mapper.FileInfomationMapper;
import com.ruoyi.bid.mapper.FolderStructureMapper;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bid.mapper.BidListMapper;
import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.service.IBidListService;

import javax.annotation.Resource;

/**
 * 招投标Service业务层处理
 * 
 * @author chb
 * @date 2022-11-27
 */
@Slf4j
@Service
public class BidListServiceImpl implements IBidListService 
{
    @Autowired
    private BidListMapper bidListMapper;

    @Resource
    private FileInfomationMapper fileInfomationMapper;

    @Resource
    private FolderStructureMapper folderStructureMapper;

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
        int i = bidListMapper.insertBidList(bidList);

        //文件柜中添加招标文件数据
        instFolderStructureFile(bidList,FolderStructureState.BIDDING_DOCUMENT,2L);
        //文件柜中添加投标文件数据
        instFolderStructureFile(bidList,FolderStructureState.TENDER_DOCUMENT,1L);
        return i;
    }
    //对文件夹进行添加
    private void instFolderStructureFile(BidList bidList,FolderStructureState folderStructureState,Long parentIdTBZB) {

        //文件夹信息
        FolderStructure folderStructure=new FolderStructure();
        folderStructure.setProjId(bidList.getProjId().toString()); //项目id
        //父级id 投标类
        folderStructure.setParentId(parentIdTBZB);
        folderStructure.setFolderName(folderStructureState.getType());
        folderStructure.setFolderPath(RuoYiConfig.getDownloadPath()); //路径
        log.info("添加文件夹信息{}",folderStructure);
        //对文件中招标文件添加
        instFileInfomation(bidList, folderStructure,bidList.getProjTender(),bidList.getTenderTemp());
        folderStructureMapper.insertFolderStructure(folderStructure);

    }
    //对文件进行添加
    private void instFileInfomation(BidList bidList, FolderStructure folderStructure,String ProjTender,String enderTemp) {
        //添加文件信息
        FileInfomation fileInfomation=new FileInfomation();
        fileInfomation.setProjId(bidList.getProjId());//项目id
        fileInfomation.setFolderId(folderStructure.getFolderId());//文件夹id
        //文件名称或者文件模板名称
        fileInfomation.setFileName(ProjTender.substring(ProjTender.lastIndexOf("/") + 1));
        fileInfomation.setFilePath(enderTemp.substring(enderTemp.lastIndexOf("/") + 1));
        fileInfomationMapper.insertFileInfomation(fileInfomation);
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
//        if (bidList.getProjEnd().compareTo(new Date())<0){
//            System.out.println(bidList.getProjEnd().compareTo(new Date()));
//
//            return AjaxResult.error("时间过期了，修改投标截止时间");
//        }
        bidListMapper.updateByStatus(bidList);
        return AjaxResult.success("发售成功");
    }

    @Override
    public List<BidList> seleValidBidList() {
        List<BidList> bidLists = bidListMapper.seleValidBidList();
        return bidLists;
    }
}
