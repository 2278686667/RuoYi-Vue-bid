package com.ruoyi.bid.controller;

import com.ruoyi.bid.domain.BidList;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/piechart")
public class PieChartController {
    @Autowired
    public IBidListService iBidListService;
    /**
     * 查询所有项目信息
     * @return
     */
    @RequestMapping("/returnstatus")
    public AjaxResult returnStatus(){
        List<BidList> bidLists = iBidListService.selectBidListList(null);
        Map<String,Object> map=new HashMap<>();
        bidLists.stream().map(new Function<BidList, Map<String,Object>>() {
            @Override
            public Map<String,Object> apply(BidList bidList) {
                System.out.println(bidList);
                String statusStr = bidList.getStatus();
                Integer status = Integer.valueOf(statusStr);
                String str="";
                if (status.equals(0)){
                    str="pending";
                }else if (status.equals(1)){
                    str="onSale";
                }else if (status.equals(2)) {
                    str = "bidOpening";
                }else if (status.equals(3)){
                    str="bidEvaluated";
                }else if (status.equals(4)){
                    str="underReview";
                }else if (status.equals(5)){
                    str="reviewCompleted";
                }else if (status.equals(6)){
                    str="terminated";
                }else if (status.equals(7)){
                    str="completed";
                }
                Integer values = (Integer) map.get(status);
                map.put(str,values==null?1:(values)++);
                return map;
            }
        }).collect(Collectors.toList());
        return AjaxResult.success(map);

    }
}
