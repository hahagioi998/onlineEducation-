package com.hnguigu.course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.course.repository.CourseMapper;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.request.CourseListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseMapper courseMapper;

    //查询我的课程
    public QueryResponseResult<CourseInfo> findCourseList(String company_id, int page, int size, CourseListRequest courseListRequest){
        if(courseListRequest == null){
            courseListRequest = new CourseListRequest();
        }
        //将公司id参数传入dao
        courseListRequest.setCompanyId(company_id);
        //分页
        PageHelper.startPage(page,size);
        //调用dao
        Page<CourseInfo> courseList = courseMapper.findCourseList(courseListRequest);
        List<CourseInfo> list = courseList.getResult();
        long total = courseList.getTotal();
        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
        courseInfoQueryResult.setList(list);
        courseInfoQueryResult.setTotal(total);
        return new QueryResponseResult<CourseInfo>(CommonCode.SUCCESS,courseInfoQueryResult);
    }
}
