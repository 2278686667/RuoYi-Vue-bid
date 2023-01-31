package com.ruoyi.bid.common;

import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleTest {
    @Autowired
    IBidListService iBidListService;
    //每天晚上触发
    @Scheduled(cron = "0 0 0 * * ? ")
    public void execute(){
        List<BidList> bidLists = iBidListService.seleValidBidList();
        for (BidList bidList : bidLists) {
            AjaxResult ajaxResult = iBidListService.updateByStatus(bidList);
        }

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("定时任务scheduled");
        System.out.println(df.format(new Date()));
    }
}
