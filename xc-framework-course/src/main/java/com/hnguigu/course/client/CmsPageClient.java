package com.hnguigu.course.client;

import com.hnguigu.domain.cms.CmsPage;
import com.hnguigu.domain.cms.response.CmsPageResult;
import com.hnguigu.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "xc-service-manage-cms")
public interface CmsPageClient {

    @PostMapping("/cms/page/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage);

    //一键发布页面
    @PostMapping("/cms/page/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);


}
