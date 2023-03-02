package com.ruoyi.bid.controller;

import com.github.pagehelper.PageInfo;
import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.domain.TenderProject;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.bid.service.ITenderProjectService;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.controller.BaseController;
import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 投标信息
 */

@RestController
@RequestMapping("/tender/info")
public class EvaluationBidController extends BaseController{

    @Resource
    private IBidListService iBidListService;

    @Autowired
    private ITenderProjectService tenderProjectService;

    //获取正在发售中信息
    @PreAuthorize("@ss.hasPermi('tender:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(BidList bidList)
    {
        bidList.setStatus("1");
        startPage();
        List<BidList> list=iBidListService.selectBidBySale(bidList);

        Long[] projIds=new Long[list.size()];

        //保存所有projId
        for (int i = 0; i < list.size(); i++) {
            projIds[i]=list.get(i).getProjId();
        }
        //查询我的投标列表
        List<TenderProject> tenderProjectList = tenderProjectService.selectTenderProjectByProjId(projIds);
        if (StringUtils.isNull(tenderProjectList)){
            return null;
        }
        List<Long> collect = tenderProjectList.stream().map(TenderProject::getProjId).collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            //如果存在说明已经投标了，移除
            if (collect.contains(list.get(i).getProjId())){
                list.remove(i);
            }
        }

        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('tender:info:deliver')")
    @GetMapping("/deliver")
    public TableDataInfo deliver(BidList bidList)
    {
        return null;
    }
}
