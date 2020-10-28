package com.hnguigu.course.service.course;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.course.CoursePic;

public interface CoursePicService {

    //根据id查询图片
    CoursePic findCoursePicBycourseId(String id);

    //添加
    ResponseResult addCoursepic(String courseId,String pic);

    //删除图片
    ResponseResult deleteCoursePic(String courseId);
}
