package com.ruoyi.bid;

import java.util.HashMap;
import java.util.Map;

public class Test03 {
    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<>();
        Integer asdf = (Integer) map.get("asdf");
        System.out.println(asdf);
    }
}
