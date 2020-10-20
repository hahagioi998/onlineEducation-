package com.hnguigu.course.repository;

import com.hnguigu.domain.course.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
*   课程分类表
* */
public interface CategoryRepository extends JpaRepository<Category,String>, JpaSpecificationExecutor<Category> {

}
