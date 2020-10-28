package com.hnguigu.teacher.repository;

import com.hnguigu.domain.ucenter.XcTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface teacherRepository extends JpaRepository<XcTeacher,String>, JpaSpecificationExecutor<XcTeacher> {
}
