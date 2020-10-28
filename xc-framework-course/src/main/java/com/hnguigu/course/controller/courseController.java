package com.hnguigu.course.controller;


import com.hnguigu.api.course.CourseControllerApi;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.course.service.course.CourseBaseService;
import com.hnguigu.course.service.course.CourseMarketService;
import com.hnguigu.course.service.course.CoursePicService;
import com.hnguigu.course.service.course.TeachplanService;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.TeachplanMedia;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.ext.CourseView;
import com.hnguigu.domain.course.ext.TeachplanNode;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.CoursePublishResult;
import com.hnguigu.domain.course.response.DeleteCourseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/course")
public class courseController implements CourseControllerApi {



    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private CourseMarketService courseMarketService;

    @Autowired
    private TeachplanService teachplanService;

    @Autowired
    private CoursePicService coursePicService;

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
        CourseMarket courseMarket = courseMarketService.findByid(courseid);
        if(courseMarket!=null){
            //判断查询出来的数据里面的结束时间是否已经超过了当前时间
            Date endTime = courseMarket.getEndTime();
            if(endTime!=null){
                Date date = new Date();
                if(!date.before(endTime)){
                    //超过结束时间则删除改营销计划，并且向course_off表添加一条已经过期的数据，方便查看
                    boolean b = courseMarketService.deleteCourseMarket(courseMarket);
                }
            }
        }
        return courseMarket;
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

    @GetMapping("/teachplan/list/{courseid}")
    @ResponseBody
    @Override
    public TeachplanNode queryTeachplanBycourseid(@PathVariable String courseid) {
        TeachplanNode teachplanNode = teachplanService.queryTeachplanBycourseid(courseid);
        return teachplanNode;
    }

    @GetMapping("/teachplan/TeachplanBytesPoint/{id}")
    @ResponseBody
    @Override
    public List<Teachplan> findTeachplanBytesPoint(@PathVariable String id) {
        List<Teachplan> teachplan = teachplanService.findTeachplan(id);
        return teachplan;
    }

    @PostMapping("/teachplan/add")
    @ResponseBody
    @Override
    public AddCourseResult addTeachplan(@RequestBody  Teachplan teachplan) {
        AddCourseResult addCourseResult = teachplanService.addTeachplan(teachplan);
        return addCourseResult;
    }

    @PostMapping("/teachplan/deleteteachplan")
    @ResponseBody
    @Override
    public DeleteCourseResult deleteTeachplan(@RequestBody TeachplanNode teachplanNode) {
        DeleteCourseResult deleteCourseResult = teachplanService.deleteTheachplan(teachplanNode);
        return deleteCourseResult;
    }

    @Override
    @PostMapping("savemedia")
    public ResponseResult saveMedia(@RequestBody TeachplanMedia teachplanMedia) {
        return teachplanService.saveMdia(teachplanMedia);
    }

    @Override
    @ResponseBody
    @GetMapping("/courseview/{id}")
    public CourseView courseview(@PathVariable("id") String id) {
        return courseBaseService.getCoruseView(id);
    }

    @Override
    @ResponseBody
    @PostMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable String id) {
        return courseBaseService.preview(id);
    }
}

