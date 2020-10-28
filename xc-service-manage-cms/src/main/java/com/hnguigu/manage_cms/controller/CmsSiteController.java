package com.hnguigu.manage_cms.controller;

import com.hnguigu.api.cms.CmsSiteControllerApi;
import com.hnguigu.domain.cms.CmsSite;
import com.hnguigu.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements CmsSiteControllerApi {

    @Autowired
    private PageService pageService;

    /**
     * 查询站点信息
     * @return
     */
    @GetMapping("/getSite")
    @Override
    public List<CmsSite> queryAllSite() {
        return pageService.queryAllSite();
    }
}
