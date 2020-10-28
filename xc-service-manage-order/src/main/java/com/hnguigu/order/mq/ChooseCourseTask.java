
package com.hnguigu.order.mq;

import com.hnguigu.domain.task.XcTask;
import com.hnguigu.order.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@Component
public class ChooseCourseTask {

    private static final Logger LOGGER= LoggerFactory.getLogger(ChooseCourseTask.class);

    @Autowired
    private TaskService taskService;

    @Scheduled(cron = "0/3 * * * * *")
            public void sendChooseCourseTask(){
            Calendar calendar=new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.set(GregorianCalendar.MINUTE,-1);
    Date time = calendar.getTime();
    List<XcTask> xcTaskList = taskService.findXcTaskList(time, 100);
        System.out.println(xcTaskList);
}

/*//    @Scheduled(cron = "0/3 * * * * *")
    @Scheduled(fixedRate = 3000)
    public void task1(){
        LOGGER.info("测试任务1开始");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("测试任务1结束");
    }

    @Scheduled(fixedRate = 3000)
    public void task2(){
        LOGGER.info("测试任务2开始");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("测试任务2结束");
    }*/
}
