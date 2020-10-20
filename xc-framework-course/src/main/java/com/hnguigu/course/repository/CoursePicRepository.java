package com.hnguigu.course.repository;


import com.hnguigu.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
*   课程图片
* */
public interface CoursePicRepository extends JpaRepository<CoursePic,String>, JpaSpecificationExecutor<CoursePic> {

}
