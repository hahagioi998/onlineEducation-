package com.hnguigu.course.service.course;

import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.CoursePic;

import java.util.Date;

public interface CourseOffServcie {

    //添加数据
    void addCourseOff(CourseBase courseBase, CourseMarket courseMarket, Date date, CoursePic coursePic);
}
