package com.hnguigu.manage_cms.controller;

import com.hnguigu.api.cms.CmsPageControllerApi;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.cms.CmsPage;
import com.hnguigu.domain.cms.CmsPageParam;
import com.hnguigu.domain.cms.request.QueryPageRequest;
import com.hnguigu.domain.cms.response.CmsPageResult;
import com.hnguigu.manage_cms.dao.CmsPageRepository;
import com.hnguigu.manage_cms.service.PageService;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    PageService pageService;

    /**
     * 条件分页查询页面信息
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    @GetMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable("page") Integer page,@PathVariable("size") Integer size, QueryPageRequest queryPageRequest) {
        return pageService.findList(page,size,queryPageRequest);
    }

    /**
     * 添加页面信息
     * @param cmsPage
     * @return
     */
    @PostMapping("/add")
    @Override
    public CmsPageResult addPage(@RequestBody CmsPage cmsPage) {
        return pageService.addPage(cmsPage);
    }

    /**
     * 修改页面信息
     * @param id
     * @param cmsPage
     * @return
     */
    @PutMapping("/edit/{id}")
    @Override
    public CmsPageResult updatePage(@PathVariable("id")String id,@RequestBody CmsPage cmsPage) {
        return pageService.updatePage(id,cmsPage);
    }

    /**
     * 根据编号查询页面信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @Override
    public CmsPage findById(@PathVariable("id") String id) {
        return pageService.findById(id);
    }

    /**
     * 删除页面信息
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    @Override
    public ResponseResult deletePage(@PathVariable("id") String id) {
        return pageService.deletePage(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @PostMapping("/generateHtml/{id}")
    @Override
    public String generateHtml(@PathVariable("id") String id) {
        return null;
    }

    @GetMapping("/getHtml/{id}")
    @Override
    public String getHtml(@PathVariable("id") String id) {
        return null;
    }

    /**
     * 发布页面
     * @param id
     * @return
     */
    @PostMapping("/postPage/{id}")
    @Override
    public ResponseResult postPage(@PathVariable("id") String id) {
        return pageService.post(id);
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody  CmsPage cmsPage) {
        return pageService.save(cmsPage);
    }
}
