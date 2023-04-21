package com.jinli.jinrui.controller;

import com.jinli.jinrui.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/*
* 文件上传与下载
* */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${jinrui.path}")
    private String basePath;

    /*
    * 文件上传
    * */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        log.info(file.toString());

        try {
            //将临时文件保存到指定位置
            file.transferTo(new File(basePath + "hello.jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
