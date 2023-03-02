package com.ruoyi.bid;

import com.ruoyi.bid.domain.FileInfomation;
import com.ruoyi.bid.domain.FolderStructure;
import com.ruoyi.bid.service.IBidListService;
import com.ruoyi.bid.common.ScheduleTest;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class Test01 {
    @Autowired
    private IBidListService iBidListService;

    @Autowired
    private ISysUserService userService;

    @Test
    public void test01(){
        FolderStructure folderStructure=new FolderStructure();
        folderStructure.setFolderId(1L);
        folderStructure.setParentId(2L);
        folderStructure.setProjId("3");
        FileInfomation fileInfomation=new FileInfomation();
        fileInfomation.setProjId(Long.valueOf(folderStructure.getProjId()));
        BeanUtils.copyProperties(folderStructure,fileInfomation);
        System.out.println(fileInfomation);

    }

    public static void main(String[] args) {
//        fileName();
        List<Integer> givenList = Arrays.asList(1, 2, 3,4,5);
        int n=5;
        List<Integer> randoms = createRandoms(givenList, n);
        for (Integer random : randoms) {
            System.out.println(random);
        }
    }

    private static void fileName() {
        String s="/profile/upload/2023/02/17/%2fnx%2f1.6货物类政府采购询价文件标准文本_20230217162000A001.doc";
        ;
        String substring = s.substring(s.lastIndexOf("/") + 1);
        System.out.println(substring);
    }

    public static void test02(){
        Long data=5L;
//        List<SysUser> sysUsers = userService.selectUserList(new SysUser());
//        List<SysUser> userList=userService.selectByTender();
//        List<Long> randomId = userList.stream().map(SysUser::getUserId).collect(Collectors.toList());
        List<Long> randomId=new ArrayList<>();
        randomId.add(1L);
        randomId.add(2L);
        randomId.add(3L);
        randomId.add(4L);
        randomId.add(5L);
        Random random=new Random();
        Long along[]=new Long[data.intValue()];
        for (int i = 0; i < data; i++) {
            int nextInt = random.nextInt(randomId.size());
            along[i] = randomId.get(nextInt);
            System.out.println(along[i]);

        }

    }
    /**
     * 从集合中随机取出N个不重复的元素
     * @param list 需要被取出数据的集合
     * @param n 取出的元素数量
     * @return
     */
    private static List<Integer>  createRandoms(List<Integer> list, int n) {
        Map<Integer,String> map = new HashMap();
        List<Integer> news = new ArrayList();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int)(Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    news.add(list.get(random));
                }
            }
            return news;
        }
    }


}
