package com.jinli.jinrui.controller;

import com.jinli.jinrui.common.Result;
import com.sun.net.httpserver.spi.HttpServerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

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
    public Result<String> upload(MultipartFile file) {
        //file 是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会被删除
        log.info(file.toString());

        //原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新编写文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString()+ suffix ;
        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在需要创建
            dir.mkdirs();
        }
        try {
            //将临时文件保存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(fileName);
    }

    /*
     * 文件下载
     * */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            //输入流，通过输入流读取内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流将文件写回浏览器，在浏览器显示图片
            ServletOutputStream outputStream = response.getOutputStream();

            //设置响应回来的文件类型
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            //不等于-1时代表还没有读取完
            while ((len = fileInputStream.read(bytes)) != -1){
                //通过输出流 write 来写，从0开始写，len表示长度
                outputStream.write(bytes,0,len);
                //通过flush来刷新
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
