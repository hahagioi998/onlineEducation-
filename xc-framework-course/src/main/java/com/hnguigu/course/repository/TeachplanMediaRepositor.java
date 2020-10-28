package com.hnguigu.course.repository;

import com.hnguigu.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TeachplanMediaRepositor extends JpaRepository<TeachplanMedia,String>, JpaSpecificationExecutor<TeachplanMedia> {
      //根据课程id查询列表
     List<TeachplanMedia> findByCourseId(String id);
}
