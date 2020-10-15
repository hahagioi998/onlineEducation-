package com.hnguigu.api.course;

import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.ext.CategoryNode;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.response.AddCourseResult;

public interface CourseControllerApi {

    //查询课程基本信息并且分页
    QueryResult<CourseInfo> queryPageCourseBase(Integer page, Integer size,String userId);

    //添加CourseBase数据
    AddCourseResult addCourseBase(CourseBase courseBase);

    //根据id查询基本课程信息
    CourseBase queryCourseBaseByid(String id);

    //根据id查询营销课程
    CourseMarket findByid(String id);

    //添加或者修改
    ResponseResult addAndupdate(CourseMarket courseMarket);

    //修改CourseBase数据
    AddCourseResult updateCourseBase(CourseBase courseBase);

}
