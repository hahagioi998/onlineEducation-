package com.hnguigu.course.repository;

import com.hnguigu.domain.course.TeachplanMediaPub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachplanMediaPubRepositor extends JpaRepository<TeachplanMediaPub,String> {
     //根据课程id删除记录
    long deleteByCourseId(String id);
}
