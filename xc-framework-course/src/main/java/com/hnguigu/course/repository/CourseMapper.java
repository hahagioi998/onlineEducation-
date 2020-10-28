package com.hnguigu.course.repository;

import com.github.pagehelper.Page;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {
    Page<CourseInfo> findCourseList(CourseListRequest courseListRequest);
}
