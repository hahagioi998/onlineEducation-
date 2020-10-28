package com.hnguigu.teacher.controller;


import com.hnguigu.api.teacher.TeacherControllerApi;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.ucenter.XcTeacher;
import com.hnguigu.teacher.feign.FileSystemFeign;
import com.hnguigu.teacher.service.teacher.teacherService;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/teacher")
public class teacherController implements TeacherControllerApi {


    @Autowired
    private teacherService teacherService;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private FileSystemFeign fileSystemFeign;

    /*@Autowired
    private RestTemplate restTemplate;*/
    //跟老师页面的所有请求
    @GetMapping("/teacherByuserId/{userId}")
    @ResponseBody
    @Override
    public XcTeacher findTeacherByuserId(@PathVariable String userId) {
        XcTeacher teacher = teacherService.findTeacherByuserid(userId);
        return teacher;
    }

    //添加或者修改老师信息
    @PutMapping("/updateteacher/update")
    @ResponseBody
    @Override
    public ResponseResult updateaddTeacher(@RequestBody XcTeacher xcTeacher) {
        //添加或者修改
        ResponseResult responseResult = teacherService.addandUpdate(xcTeacher);
        return responseResult;
    }

    @Override
    @PostMapping("/teacherpic/add")
    @ResponseBody
    public ResponseResult addTeacherPic(@RequestParam("userId") String userId, @RequestParam("pic") String pic) {
        //根据userId查询teacher表
        XcTeacher teacher = teacherService.findTeacherByuserid(userId);
        teacher.setPic(pic);
        ResponseResult responseResult = teacherService.addandUpdate(teacher);
        //保存课程图片 return courseService.saveCoursePic(courseId,pic);
        return  responseResult;
    }

    @DeleteMapping("/Deleteteacherpic/Delete")
    @ResponseBody
    @Override
    public ResponseResult DeleteTeacherPic(@RequestParam("userId") String userId, @RequestParam("pic") String pic) {
        try {
          /*  String url ="http://127.0.0.1:2323/course/Deletefilesystem?pic=" + pic;
            restTemplate.getForObject(url,void.class);*/
            String decode = URLDecoder.decode(pic);
            fileSystemFeign.deletefilesystem(decode);
            minioClient.removeObject("teacher",pic);
            //根据userId查询teacher表
            XcTeacher teacher = teacherService.findTeacherByuserid(userId);
            teacher.setPic("");
            ResponseResult responseResult = teacherService.addandUpdate(teacher);
            //保存课程图片 return courseService.saveCoursePic(courseId,pic);
            return  responseResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
