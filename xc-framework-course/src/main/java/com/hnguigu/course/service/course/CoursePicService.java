package com.hnguigu.course.service.course;

import com.hnguigu.domain.course.CoursePic;

public interface CoursePicService {

    //根据id查询图片
    CoursePic findCoursePicBycourseId(String id);
}
