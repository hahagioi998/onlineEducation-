package com.hnguigu.course.controller;


import com.hnguigu.api.course.CourseControllerApi;
import com.hnguigu.course.service.course.CourseBaseService;
import com.hnguigu.course.service.course.CourseMarketService;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.ext.CategoryNode;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.response.AddCourseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping("/course")
public class courseController implements CourseControllerApi {

    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private CourseMarketService courseMarketService;

    @GetMapping("/coursebase/list/{page}/{size}")
    @ResponseBody
    @Override
    public QueryResult<CourseInfo> queryPageCourseBase(@PathVariable Integer page,@PathVariable Integer size,@Param(value = "userId") String userId) {
        //查询course_base表数据
        QueryResult<CourseInfo> queryResult = courseBaseService.queryPageCourseBase(page,size);

        return queryResult;
    }

    @PostMapping("/coursebase/add")
    @ResponseBody
    @Override
    public AddCourseResult addCourseBase(@RequestBody CourseBase courseBase) {
        //添加到CourseBase表
        return courseBaseService.addCourseBase(courseBase);
    }

    @GetMapping("/coursebase/courseByid/{courseid}")
    @ResponseBody
    @Override
    public CourseBase queryCourseBaseByid(@PathVariable  String courseid) {
        //根据id查询基本课程信息
        CourseBase courseBase = courseBaseService.queryCourseBaseByid(courseid);
        return courseBase;
    }

    //营销课程
    @GetMapping("/coursemarket/courseMarketByid/{courseid}")
    @ResponseBody
    @Override
    public CourseMarket findByid(@PathVariable  String courseid) {
        return courseMarketService.findByid(courseid);
    }

    @PostMapping("/coursemarket/addAndupdateMarket")
    @ResponseBody
    @Override
    public ResponseResult addAndupdate(@RequestBody CourseMarket courseMarket) {
        ResponseResult responseResult = courseMarketService.addAndupdate(courseMarket);
        return responseResult;
    }




    @PutMapping("/coursebase/updateCourseBase")
    @ResponseBody
    @Override
    public AddCourseResult updateCourseBase(@RequestBody CourseBase courseBase){
        //修改CourseBase表
        AddCourseResult addCourseResult = courseBaseService.updateCourseBase(courseBase);
        return addCourseResult;
    }


}
