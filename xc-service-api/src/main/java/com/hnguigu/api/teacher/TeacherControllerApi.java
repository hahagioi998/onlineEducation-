package com.hnguigu.api.teacher;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.ucenter.XcTeacher;

public interface TeacherControllerApi {


    /*跟老师页面的所有请求*/
    XcTeacher findTeacherByuserId(String userId);

    //添加或者修改老师信息
    ResponseResult updateaddTeacher(XcTeacher xcTeacher);
    //图片地址保存到sql
    ResponseResult addTeacherPic(String userId, String pic);
    //图片地址删除sql
    ResponseResult DeleteTeacherPic(String userId, String pic);
}
