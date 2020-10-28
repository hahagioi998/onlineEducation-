package com.hnguigu.api.search;


import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.domain.course.CoursePub;
import com.hnguigu.domain.search.CourseSearchParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * Created by Administrator.
 */
@Api(value = "课程搜索",description = "课程搜索",tags = {"课程搜索"})
public interface EsCourseControllerApi {
    //搜索课程信息
    @ApiOperation("课程综合搜索")
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam);

    @ApiOperation("根据课程id查询课程信息")
    public Map<String,CoursePub>  getAll(String id);
}
