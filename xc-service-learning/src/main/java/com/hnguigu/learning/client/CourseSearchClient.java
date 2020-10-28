package com.hnguigu.learning.client;

import com.hnguigu.common.client.XcServiceList;
import com.hnguigu.domain.course.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator.
 */
@FeignClient(value= XcServiceList.XC_SERVICE_SEARCH)
public interface CourseSearchClient {

    //根据课程计划id查询课程媒资
    @GetMapping("/search/course/getmedia/{teachplanId}")
    public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId);
}
