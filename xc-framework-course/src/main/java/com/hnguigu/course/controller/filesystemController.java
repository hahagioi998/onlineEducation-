package com.hnguigu.course.controller;

import com.alibaba.fastjson.JSON;
import com.hnguigu.api.course.FilesystemControllerApi;
import com.hnguigu.course.repository.FilesystemRepositor;
import com.hnguigu.domain.filesystem.FileSystem;
import com.hnguigu.domain.filesystem.response.UploadFileResult;
import io.minio.errors.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import io.minio.MinioClient;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Controller
@RequestMapping("/filesystem")
public class filesystemController implements FilesystemControllerApi {

    private static String url = "http://127.0.0.1:9000";  //minio服务的IP端口
    private static String accessKey = "minioadmin";
    private static String secretKey = "minioadmin";

    @Autowired
    private FilesystemRepositor filesystemRepositor;

    @PostMapping("/upload")
    @ResponseBody
    @Override
    public UploadFileResult upload(
            @RequestParam("multipartFile") MultipartFile multipartFile,
            @RequestParam(value = "filetag", required = true) String filetag,
            @RequestParam(value = "businesskey", required = false) String businesskey,
            @RequestParam(value = "metedata", required = false) String metadata
    ) {
        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
            InputStream is = multipartFile.getInputStream(); //得到文件流
            String fileName = multipartFile.getOriginalFilename(); //文件名
            String contentType = multipartFile.getContentType();  //类型
            minioClient.putObject("course", fileName, is, contentType); //把文件放置Minio桶(文件夹)

            String url = minioClient.presignedGetObject("course", fileName);
            FileSystem fileSystem = new FileSystem();
            fileSystem.setFileId(url);
            fileSystem.setFilePath(url);
            //业务标识
            fileSystem.setBusinesskey(businesskey);
            // 标签
            fileSystem.setFiletag(filetag);
            // 元数据
            if(StringUtils.isNotEmpty(metadata)){
                try {
                    Map map = JSON.parseObject(metadata, Map.class);
                    fileSystem.setMetadata(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //名称
            fileSystem.setFileName(multipartFile.getOriginalFilename());
            // 大小
            fileSystem.setFileSize(multipartFile.getSize());
            // 文件类型
            fileSystem.setFileType(multipartFile.getContentType());
            FileSystem save = filesystemRepositor.save(fileSystem);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
