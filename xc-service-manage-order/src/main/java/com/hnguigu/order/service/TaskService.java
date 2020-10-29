package com.hnguigu.order.service;

import com.hnguigu.domain.task.XcTask;
import com.hnguigu.domain.task.XcTaskHis;
import com.hnguigu.order.config.RabbitMQConfig;
import com.hnguigu.order.dao.XcTaskHisRepository;
import com.hnguigu.order.dao.XcTaskRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private XcTaskRepository xcTaskRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private XcTaskHisRepository xcTaskHisRepository;

    /**
     *  查询某个时间之间的前n条任务
     * @param updateTime
     * @param size
     * @return
     */
    public List<XcTask> findXcTaskList(Date updateTime,int size){
        Pageable pageable=PageRequest.of(0,size);
        Page<XcTask> p = xcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);

        return p.getContent();
    }

    /**
     * 发布消息
     * @param xcTask
     * @param ex
     * @param routingKey
     */
    public void publish(XcTask xcTask,String ex,String routingKey){
        Optional<XcTask> optional = xcTaskRepository.findById(xcTask.getId());
        if (optional.isPresent()){
            rabbitTemplate.convertAndSend(ex,routingKey,xcTask);
            XcTask xcTask1 = optional.get();
            xcTask1.setUpdateTime(new Date());
            xcTaskRepository.save(xcTask1);
        }
    }

    /**
     * 获取任务
     * @param id
     * @param version
     * @return
     */
    @Transactional
    public int getTask(String id,int version){
        //通过乐观锁的方式来更新数据
        int i = xcTaskRepository.updateTaskVersion(id, version);
        return i;
    }

    /**
     * 完成任务
     * @param taskId
     */
    @Transactional
    public void finishTask(String taskId){
        Optional<XcTask> optional = xcTaskRepository.findById(taskId);
        if (optional.isPresent()){
            XcTask xcTask = optional.get();

            //添加历史任务
            XcTaskHis xcTaskHis=new XcTaskHis();
            BeanUtils.copyProperties(xcTask,xcTaskHis);
            xcTaskHisRepository.save(xcTaskHis);
            //删除当前任务
            xcTaskRepository.delete(xcTask);
        }
    }
}
