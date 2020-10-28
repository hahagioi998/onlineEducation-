package com.hnguigu.api.cms;


import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.cms.CmsPage;
import com.hnguigu.domain.cms.request.QueryPageRequest;
import com.hnguigu.domain.cms.response.CmsPageResult;
import com.hnguigu.domain.cms.response.CmsPostPageResult;
import io.swagger.annotations.Api;

import javax.management.Query;

@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //页面列表
    QueryResponseResult findList(Integer page, Integer size, QueryPageRequest queryPageRequest);

    //页面添加
    CmsPageResult addPage(CmsPage cmsPage);

    //页面修改
    CmsPageResult updatePage(String id, CmsPage cmsPage);

    //页面查询
    CmsPage findById(String id);

    //删除页面
    ResponseResult deletePage(String id);

    //生成静态文件
    String generateHtml(String id);

    //取出静态文件
    String getHtml(String id);

    //发布页面
    ResponseResult postPage(String id);

    //保存页面
    CmsPageResult save(CmsPage cmsPage);

    //一键发布页面
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
