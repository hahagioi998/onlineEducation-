package com.hnguigu.course.controller;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hnguigu.api.course.CourseControllerApi;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.common.web.BaseController;
import com.hnguigu.course.repository.FilesystemRepositor;
import com.hnguigu.course.service.CourseService;
import com.hnguigu.course.service.course.CourseBaseService;
import com.hnguigu.course.service.course.CourseMarketService;
import com.hnguigu.course.service.course.CoursePicService;
import com.hnguigu.course.service.course.TeachplanService;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.CoursePic;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.course.service.course.*;
import com.hnguigu.domain.course.*;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.ext.CourseView;
import com.hnguigu.domain.course.ext.TeachplanNode;
import com.hnguigu.domain.course.request.CourseListRequest;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.CoursePublishResult;
import com.hnguigu.domain.course.response.DeleteCourseResult;
import com.hnguigu.domain.filesystem.FileSystem;
import com.hnguigu.utils.XcOauth2Util;
import io.minio.MinioClient;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping("/course")
public class courseController extends BaseController implements CourseControllerApi {
    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private CourseMarketService courseMarketService;

    @Autowired
    private TeachplanService teachplanService;

    @Autowired
    private CoursePicService coursePicService;

    @Autowired
    private FilesystemRepositor filesystemRepositor;

    @Autowired
    CourseService courseService;
    private CourseOffService courseOffService;

    @GetMapping("/coursebase/list/{page}/{size}")
    @ResponseBody
    @Override
    public QueryResult<CourseInfo> queryPageCourseBase(@PathVariable Integer page,@PathVariable Integer size,@Param(value = "userId") String userId) {
        //查询course_base表数据
        QueryResult<CourseInfo> queryResult = courseBaseService.queryPageCourseBase(page,size,userId);
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
                    //向off表中添加一条数据
                    //需要base表数据根据id查询，需要market表（上面已有） 需要过期时间 date 需要课程图片(根据课程id查询) 需要课程计划（根据课程id查询课程计划并且转换成字符串）
                    CourseBase courseBase = courseBaseService.queryCourseBaseByid(courseid);
                    CoursePic coursePic = coursePicService.findCoursePicBycourseId(courseid);
                    TeachplanNode teachplanNode = teachplanService.queryTeachplanBycourseid(courseid);
                    Gson gson = new Gson();
                    String json = gson.toJson(teachplanNode);
                    //获取上面所有数据开始添加
                    boolean b1 = courseOffService.addCourseOff(courseBase, courseMarket, date, coursePic, json);
                    if(b1==true){
                        //超过结束时间则删除改营销计划，并且向course_off表添加一条已经过期的数据，方便查看
                        boolean b = courseMarketService.deleteCourseMarket(courseMarket);
                    }
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

    //当用户拥有course_teachplan_list权限的时候方可访问此方法
//    @PreAuthorize("hasAuthority('course_teachplan_list')")
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

    @PreAuthorize("hasAuthority('course_teachplan_add')")
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

    @GetMapping("/teachplan/TeachplanByid/{id}")
    @ResponseBody
    public Teachplan TeachplanQueryByid(@PathVariable  String id) {
        Teachplan teachplanByid = teachplanService.findTeachplanByid(id);
        return teachplanByid;
    }

    @PutMapping("/teachplan/update")
    @ResponseBody
    public AddCourseResult updateTeachplan(@RequestBody Teachplan teachplan) {
        AddCourseResult addCourseResult = teachplanService.UpdateTeachplan(teachplan);
        return addCourseResult;
    }

    @PostMapping("/coursepic/add")
    @ResponseBody
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        ResponseResult responseResult = coursePicService.addCoursepic(courseId, pic);
        return responseResult;
        //保存课程图片 return courseService.saveCoursePic(courseId,pic);
    }

    //当用户拥有course_pic_list权限的时候方可访问此方法
    @PreAuthorize("hasAuthority('course_pic_list')")
    @GetMapping("/coursepic/list/{courseId}")
    @ResponseBody
    public CoursePic findCoursePic(@PathVariable String courseId) {
        CoursePic coursePicBycourseId = coursePicService.findCoursePicBycourseId(courseId);
        return coursePicBycourseId;
    }

    @DeleteMapping("/coursepic/delete")
    @ResponseBody
    public ResponseResult DeleteCoursePicBycourseId(@RequestParam(value = "courseId") String courseId) {
         String url = "http://127.0.0.1:9000";  //minio服务的IP端口
         String accessKey = "minioadmin";
         String secretKey = "minioadmin";
        try {
            MinioClient minioClient = new MinioClient(url,accessKey,secretKey);
            CoursePic pic = coursePicService.findCoursePicBycourseId(courseId);
            String pic1 = pic.getPic();
            Optional<FileSystem> system = filesystemRepositor.findById(pic1);
            FileSystem fileSystem = null;
            if(system.isPresent()){
                fileSystem = system.get();
            }
            if(fileSystem!=null){
                minioClient.removeObject("course",fileSystem.getFileName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseResult responseResult = coursePicService.deleteCoursePic(courseId);
        return responseResult;
    }
    //俩个一样的请求
    @GetMapping("/coursebases/list/{page}/{size}")
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable("page") int page,@PathVariable("size") int size, CourseListRequest courseListRequest){
            //获取当前用户信息
            XcOauth2Util xcOauth2Util = new XcOauth2Util();
            XcOauth2Util.UserJwt userJwt = xcOauth2Util.getUserJwtFromHeader(request);
            //当前所属单位的id
            String company_id = userJwt.getCompanyId();
            QueryResponseResult<CourseInfo> courseList = courseService.findCourseList(company_id, page, size, courseListRequest);
            return courseList;
    }
    //删除filesystem表的数据//根据地址删除
    @DeleteMapping("/Deletefilesystem")
    @ResponseBody
    public void deletefilesystem (@RequestParam(value = "pic") String pic) {
        filesystemRepositor.deleteById(pic);
    }
    @Override
    @ResponseBody
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

    @Override
    @ResponseBody
    @PostMapping("/publish/{id}")
    public CoursePublishResult prelish(@PathVariable String id) {
        return courseBaseService.publish(id);
    }
}

