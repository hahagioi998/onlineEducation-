package com.hnguigu.order.dao;

import com.hnguigu.domain.task.XcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface XcTaskRepository extends JpaRepository<XcTask,Integer> {
    Page<XcTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);
}
