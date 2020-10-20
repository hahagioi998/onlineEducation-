package com.hnguigu.course.repository;

import com.hnguigu.domain.course.CourseOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
*   课程基本信息
* */
public interface CourseOffRepository extends JpaRepository<CourseOff,String>, JpaSpecificationExecutor<CourseOff> {

}
