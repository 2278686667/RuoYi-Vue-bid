package com.ruoyi.bid;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.alibaba.fastjson2.JSON;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test02 {
    @Test
    void contextLoads() {
        Map<String,Object> payload=new HashMap<>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, new Date());
        payload.put("key","admin");
        String token = JWTUtil.createToken(payload, "chb".getBytes(StandardCharsets.UTF_8));
        System.out.println(token);
        JWT jwt = JWTUtil.parseToken(token);
        jwt.getHeader(JWTHeader.TYPE);
        Object sub = jwt.getPayload("key");
        System.out.println(sub);

    }
    public static void main(String[] args) {
//        test01();
//        test02();
//        String a = insertComma("01000000", 9);
//        System.out.println(a);
        test02();
    }

    private static void test01() {
        String[] res=new String[]{"A01000000","A01010000","A01010100","A01010200","A","A01010201"};
        Integer[] dataArr =new Integer[res.length];
        Arrays.fill(dataArr ,0);
        List<Integer> list=Arrays.asList(dataArr);
        for (int i = 0; i < res.length; i++) {
            //只要数字
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(res[i]);
            String result = m.replaceAll("").trim();
            if (result.isEmpty()){
                list.set(i,Integer.MAX_VALUE);
                System.out.println(list.get(i));
                continue;
            }
            Integer integer = Integer.valueOf(result);
            while (true){
                if (integer%10==0){
                    integer=integer/10;
                    Integer integer1 = list.get(i);
                    list.set(i, ++integer1);
                }else {
                    break;
                }
            }
            System.out.println(list.get(i));
        }
    }

    public static void test02(){
        String[] res=new String[]{"A01000000","A01010000","A01010100","A01010200","A","A01010201"};
        for (String re : res) {
            String[] split = re.split("0+$");
            for (int i = 0; i < split.length; i++) {
                if (split[0].equals("A")){
                    System.out.println(split[0]);
                    continue;
                }
                String substring = split[0].substring(0, split[0].length() - 2);
                StringBuilder stringBuilder=new StringBuilder(substring);
                StringBuilder append=null;
                for (int j = 0; j < 9-substring.length(); j++) {
                     append = stringBuilder.append("0");
                }
                System.out.println(re);
                System.out.println(substring);
                System.out.println(append);
            }
            System.out.println("-----------");
        }
    }


    public static String insertComma(String s, int len) {
            if (s == null || s.length() < 1) {
                return "";
            }
            NumberFormat formater = null;
            double num = Double.parseDouble(s);
            if (len == 0) {
                formater = new DecimalFormat("###,###");

            } else {
                StringBuffer buff = new StringBuffer();
                buff.append("###,###.");
                for (int i = 0; i < len; i++) {
                    buff.append("#");
                }
                formater = new DecimalFormat(buff.toString());
            }
            return formater.format(num);
    }
}
