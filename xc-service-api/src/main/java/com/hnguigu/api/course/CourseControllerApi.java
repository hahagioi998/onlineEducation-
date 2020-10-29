package com.hnguigu.api.course;

import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.course.*;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.ext.CourseView;
import com.hnguigu.domain.course.ext.TeachplanNode;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.CoursePublishResult;
import com.hnguigu.domain.course.response.DeleteCourseResult;

import com.hnguigu.domain.ucenter.XcTeacher;
import io.swagger.annotations.ApiOperation;

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

    //查询修改需要的teachplan
    Teachplan TeachplanQueryByid(String id);

    //修改teachplan
    AddCourseResult updateTeachplan(Teachplan teachplan);

    //图片地址保存到sql
    ResponseResult addCoursePic(String courseId,String pic);

    //根据id查询图片地址
    CoursePic findCoursePic(String CourseId);
    //删除上传图片
    ResponseResult DeleteCoursePicBycourseId (String courseId);

    @ApiOperation("保存课程计划与媒资文件关联")
    public  ResponseResult saveMedia(TeachplanMedia teachplanMedia);

    @ApiOperation("课程视图查询")
    public CourseView courseview(String id);

    @ApiOperation("预览课程")
    public CoursePublishResult preview(String id);

    @ApiOperation("课程发布")
    public CoursePublishResult prelish(String id);

}
