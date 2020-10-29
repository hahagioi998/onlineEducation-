package com.hnguigu.api.learning;

import com.hnguigu.domain.learning.respones.GetMediaResult;
import org.springframework.web.bind.annotation.PathVariable;

public interface CourseLearningControllerApi {

    public GetMediaResult getmedia(@PathVariable("courseId") String courseId,
                                   @PathVariable("teachplanId")String teachplanId);
}
