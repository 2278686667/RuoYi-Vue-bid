package com.ruoyi.bid.controller;

import com.ruoyi.bid.utils.FolderToZipUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
@Controller("/test")
public class TestController {
    @ApiOperation(value = "打包文件并下载zip文件")
    @GetMapping(value = "/downloadZip")
    public void downloadZip(HttpServletResponse response) {
        String zipPath = "D:\\ruoyi\\uploadPath\\upload";
        File file = new File(zipPath);//创建指定目录和文件名称的文件对象
        try {
            FolderToZipUtil.zip(zipPath,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
