package com.hnguigu.course.repository;


import com.hnguigu.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
*   课程营销信息
* */
public interface CourseMarketRepository extends JpaRepository<CourseMarket,Integer>, JpaSpecificationExecutor<CourseMarket> {

}
