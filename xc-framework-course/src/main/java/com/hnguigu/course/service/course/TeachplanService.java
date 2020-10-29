package com.hnguigu.course.service.course;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.TeachplanMedia;
import com.hnguigu.domain.course.ext.TeachplanNode;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.DeleteCourseResult;

import java.util.List;

public interface TeachplanService {

    //根据id查询所有的课程计划
    TeachplanNode queryTeachplanBycourseid(String id);

    //查询添加需要的字节点数据
    List<Teachplan> findTeachplan(String id);

    AddCourseResult addTeachplan(Teachplan teachplan);

    DeleteCourseResult deleteTheachplan(TeachplanNode teachplanNode);

    ResponseResult saveMdia(TeachplanMedia teachplanMedia);

    Teachplan findTeachplanByid(String id);

    //添加teachplan数据
    AddCourseResult UpdateTeachplan(Teachplan teachplan);
}
