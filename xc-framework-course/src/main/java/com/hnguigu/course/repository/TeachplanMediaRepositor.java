package com.hnguigu.course.repository;

import com.hnguigu.domain.course.TeachplanMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeachplanMediaRepositor extends JpaRepository<TeachplanMedia,String>, JpaSpecificationExecutor<TeachplanMedia> {

}
