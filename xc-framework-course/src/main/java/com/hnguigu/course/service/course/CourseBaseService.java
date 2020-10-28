package com.hnguigu.course.service.course;

import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.ext.CourseView;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.CoursePublishResult;


public interface CourseBaseService {

    //查询课程基本信息并且分页
    QueryResult<CourseInfo> queryPageCourseBase(Integer page, Integer size,String userId);

    //添加CourseBase数据
    AddCourseResult addCourseBase(CourseBase courseBase);

    //根据id查询基本课程信息
    CourseBase queryCourseBaseByid(String id);

    //修改CourseBase数据
    AddCourseResult updateCourseBase(CourseBase courseBase);

    CourseView getCoruseView(String id);

    CoursePublishResult preview(String id);
}
