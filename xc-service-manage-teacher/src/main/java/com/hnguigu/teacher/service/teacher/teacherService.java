package com.hnguigu.teacher.service.teacher;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.ucenter.XcTeacher;

public interface teacherService {

    //根据userId查询teacher表
    XcTeacher findTeacherByuserid(String id);

    //添加或者修改
    ResponseResult addandUpdate(XcTeacher xcTeacher);
}
