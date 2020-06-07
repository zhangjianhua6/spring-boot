package com.jason.web.controller;

import com.jason.web.annotation.AuthIgnore;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author: zhangjianhua
 * @date: 2020-03-05 14:51
 **/
@RestController
public class FileController {

    @GetMapping("/downloadFile")
    @AuthIgnore
    public void downloadFile(HttpServletRequest request, HttpServletResponse response){
        response.reset();
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setContentType("multipart/form-data");
        //设置压缩包的名字
        String downloadName =  "对账单-20191223.pdf";
        String fileName = null;
        try {
            fileName =  URLEncoder.encode(downloadName, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

        //将来文件流写入response中
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {

            //获取文件输入流
            URL url = new URL("http://172.20.11.233:6001/static/template/pdf/20191223.pdf");
            URLConnection conn = url.openConnection();
            inputStream = conn.getInputStream();
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //流的关闭
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
