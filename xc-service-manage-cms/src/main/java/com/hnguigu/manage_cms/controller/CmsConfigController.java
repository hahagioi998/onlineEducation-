package com.hnguigu.manage_cms.controller;


import com.hnguigu.api.cms.CmsConfigControllerApi;
import com.hnguigu.domain.cms.CmsConfig;
import com.hnguigu.domain.media.MediaFile;
import com.hnguigu.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {
    @Autowired
    private PageService pageService;

    @GetMapping("/getmodel/{id}")
    @Override
    public CmsConfig getmodel(@PathVariable("id") String id) {
        return pageService.getConfigById(id);
    }
}
