package com.hnguigu.search.controller;


import com.hnguigu.api.search.EsCourseControllerApi;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.domain.course.CoursePub;
import com.hnguigu.domain.search.CourseSearchParam;
import com.hnguigu.search.service.impl.EsCourseServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {
    @Autowired
    private EsCourseServiceimpl esCourseService;

    @Override
    @GetMapping(value="/list/{page}/{size}")
    public QueryResponseResult<CoursePub> list(@PathVariable("page") int page, @PathVariable("size") int size, CourseSearchParam courseSearchParam) {
        return esCourseService.list(page,size,courseSearchParam);
    }

}
