package com.hnguigu.course.service.course;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.course.CourseMarket;

public interface CourseMarketService {

    //根据id查询营销课程
    CourseMarket findByid(String id);

    //添加或者修改
    ResponseResult addAndupdate(CourseMarket courseMarket);

    //根据id删除营销课程
    boolean deleteCourseMarket(CourseMarket courseMarket);
}
