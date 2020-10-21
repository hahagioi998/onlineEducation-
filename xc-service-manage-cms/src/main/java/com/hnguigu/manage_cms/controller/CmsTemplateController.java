package com.hnguigu.manage_cms.controller;

import com.hnguigu.api.cms.CmsTemplateControllerApi;
import com.hnguigu.domain.cms.CmsTemplate;
import com.hnguigu.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Autowired
    private PageService pageService;


    /**
     * 查询模板信息
     * @return
     */
    @GetMapping("/getTemplate")
    @Override
    public List<CmsTemplate> queryAllTemplate() {
        return pageService.queryAllTemplate();
    }
}
