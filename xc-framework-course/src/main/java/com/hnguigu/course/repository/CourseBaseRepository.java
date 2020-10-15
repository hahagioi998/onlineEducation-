package com.hnguigu.course.repository;

import com.hnguigu.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
*   课程基本信息
* */
public interface CourseBaseRepository extends JpaRepository<CourseBase,Integer>, JpaSpecificationExecutor<CourseBase> {

}
