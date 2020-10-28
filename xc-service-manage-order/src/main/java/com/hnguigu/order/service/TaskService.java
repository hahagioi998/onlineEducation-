package com.hnguigu.order.service;

import com.hnguigu.domain.task.XcTask;
import com.hnguigu.order.dao.XcTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private XcTaskRepository xcTaskRepository;

    public List<XcTask> findXcTaskList(Date updateTime,int size){
        Pageable pageable=PageRequest.of(0,size);
        Page<XcTask> p = xcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);

        return p.getContent();
    }
}
