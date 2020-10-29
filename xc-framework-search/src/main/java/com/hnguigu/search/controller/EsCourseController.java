package com.hnguigu.search.controller;


import com.hnguigu.api.search.EsCourseControllerApi;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.domain.course.CoursePub;
import com.hnguigu.domain.course.TeachplanMediaPub;
import com.hnguigu.domain.search.CourseSearchParam;
import com.hnguigu.search.service.impl.EsCourseServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Override
    @ResponseBody
    @GetMapping("/getall/{id}")
    public Map<String, CoursePub> getAll(@PathVariable String id) {
        return esCourseService.getAll(id);
    }


    @Override
    @GetMapping("/getmedia/{teachplanId}")
    public TeachplanMediaPub getMedia(@PathVariable(value = "teachplanId") String id) {
        String [] teacplans=new String[]{id};
        QueryResponseResult<TeachplanMediaPub> getmedia = esCourseService.getmedia(teacplans);
        QueryResult<TeachplanMediaPub> queryResult = getmedia.getQueryResult();
        if(queryResult!=null){
            List<TeachplanMediaPub> list = queryResult.getList();
            if(list!=null&&list.size()>0){
                   return list.get(0);
            }
        }
        return null;
    }
}
