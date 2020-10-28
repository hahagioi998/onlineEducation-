package com.hnguigu.learning.mq;

import com.alibaba.fastjson.JSON;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.task.XcTask;
import com.hnguigu.learning.config.RabbitMQConfig;
import com.hnguigu.learning.service.LearningService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@Component
public class ChooseCourseTask {

    @Autowired
    LearningService learningService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 接受选课任务
     * @param xcTask
     */
    @RabbitListener(queues = RabbitMQConfig.XC_LEARNING_ADDCHOOSECOURSE)
    public void receiveChoosecourseTask(XcTask xcTask){

        //取出消息的内容
        String requestBody = xcTask.getRequestBody();
        Map map = JSON.parseObject(requestBody, Map.class);
        String userId = (String) map.get("userId");
        String courseId = (String) map.get("courseId");
//        String valId = (String) map.get("valId");
//        Date endTime = (Date) map.get("endTime");

        //添加选课
        ResponseResult addCourse = learningService.addCourse(userId, courseId, null, new Date(), null, xcTask);
        if(addCourse.isSuccess()){
            //添加选课成功，要向mq发送完成添加选课的消息
            rabbitTemplate.convertAndSend(RabbitMQConfig.EX_LEARNING_ADDCHOOSECOURSE,RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE_KEY,xcTask);
        }
    }
}
