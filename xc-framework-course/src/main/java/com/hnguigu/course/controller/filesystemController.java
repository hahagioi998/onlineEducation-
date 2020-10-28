package com.hnguigu.course.controller;

import com.alibaba.fastjson.JSON;
import com.hnguigu.api.course.FilesystemControllerApi;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.course.repository.FilesystemRepositor;
import com.hnguigu.domain.filesystem.FileSystem;
import com.hnguigu.domain.filesystem.response.UploadFileResult;
import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

@Controller
@RequestMapping("/filesystem")
public class filesystemController implements FilesystemControllerApi {

    @Autowired
    private MinioClient client;

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
        UploadFileResult uploadFileResult = null;
        try {
            String bucketName = "course";
            InputStream is = multipartFile.getInputStream(); //得到文件流
            String fileName = multipartFile.getOriginalFilename(); //文件名
            String contentType = multipartFile.getContentType();  //类型
            if(!client.bucketExists(bucketName)){
                //创建桶
                client.makeBucket(bucketName);
                //给同设置策略（读写权限）
                client.setBucketPolicy(bucketName,"*", PolicyType.READ_WRITE);
            }else{
                //获取策略
                PolicyType bucketPolicy = client.getBucketPolicy(bucketName, "*");
                //判断策略是否是有读写权限，没有则给他读写权限
                if(bucketPolicy!=PolicyType.READ_WRITE){
                    client.setBucketPolicy(bucketName,"*", PolicyType.READ_WRITE);
                }
            }
            client.putObject(bucketName, fileName, is,multipartFile.getSize(), contentType); //把文件放置Minio桶(文件夹)
            //拼凑地址
            String url = "http://127.0.0.1:9000/"+bucketName+"/"+fileName;
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
            filesystemRepositor.save(fileSystem);
            uploadFileResult = new UploadFileResult(CommonCode.SUCCESS,fileSystem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadFileResult;
    }

    @PostMapping("/teacherupload")
    @ResponseBody
    @Override
    public UploadFileResult teacherupload(
            @RequestParam("multipartFile") MultipartFile multipartFile,
            @RequestParam(value = "filetag", required = true) String filetag,
            @RequestParam(value = "businesskey", required = false) String businesskey,
            @RequestParam(value = "metedata", required = false) String metadata
    ){
        UploadFileResult uploadFileResult = null;
        try {
            String bucketName = "teacher";
            InputStream is = multipartFile.getInputStream(); //得到文件流
            String fileName = multipartFile.getOriginalFilename(); //文件名
            String contentType = multipartFile.getContentType();  //类型

            if(!client.bucketExists(bucketName)) {
                boolean b = client.bucketExists(bucketName);

                if (!b) {

                    //创建桶
                    client.makeBucket(bucketName);
                    //给同设置策略（读写权限）
                    client.setBucketPolicy(bucketName, "*", PolicyType.READ_WRITE);
                } else {
                    //获取策略
                    PolicyType bucketPolicy = client.getBucketPolicy(bucketName, "*");
                    //判断策略是否是有读写权限，没有则给他读写权限
                    if (bucketPolicy != PolicyType.READ_WRITE) {
                        client.setBucketPolicy(bucketName, "*", PolicyType.READ_WRITE);
                    }
                }
            }
            client.putObject(bucketName, fileName, is,multipartFile.getSize(), contentType); //把文件放置Minio桶(文件夹)
            //拼凑地址
            String url = "http://127.0.0.1:9000/"+bucketName+"/"+fileName;
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
            filesystemRepositor.save(fileSystem);
            uploadFileResult = new UploadFileResult(CommonCode.SUCCESS,fileSystem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadFileResult;
    }
}
