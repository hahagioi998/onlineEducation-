package com.hnguigu.api.course;

import com.hnguigu.domain.course.ext.CategoryNode;

public interface CategoryControllerApi {

    //查询课程分类，并显示在新增课程页面
    CategoryNode findList();
}
