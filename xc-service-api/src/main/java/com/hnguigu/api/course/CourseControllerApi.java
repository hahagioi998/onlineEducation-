package com.hnguigu.api.course;

import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.ext.TeachplanNode;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.DeleteCourseResult;

import java.util.List;

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

    //根据课程id查询当前课程的课程计划
    TeachplanNode queryTeachplanBycourseid(String courseid);

    //添加课程计划
    AddCourseResult addTeachplan(Teachplan teachplan);

    //根据根节点的id查询所有的子节点
    List<Teachplan> findTeachplanBytesPoint(String id);

    //删除课程计划
    DeleteCourseResult  deleteTeachplan(TeachplanNode teachplanNode);

}
