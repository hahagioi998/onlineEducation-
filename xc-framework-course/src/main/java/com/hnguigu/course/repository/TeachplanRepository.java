package com.hnguigu.course.repository;


import com.hnguigu.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
*  课程计划
* */
public interface TeachplanRepository extends JpaRepository<Teachplan,String>, JpaSpecificationExecutor<Teachplan> {

}
