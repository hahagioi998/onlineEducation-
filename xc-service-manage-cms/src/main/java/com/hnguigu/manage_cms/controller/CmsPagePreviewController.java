package com.hnguigu.manage_cms.controller;

import com.hnguigu.common.web.BaseController;
import com.hnguigu.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/cms/preview")
public class CmsPagePreviewController extends BaseController {

    @Autowired
    private PageService pageService;

    /**
     *  页面预览
     */
    @GetMapping("/{id}")
    public void preview(@PathVariable("id") String id){
        String html = pageService.getHtml(id);

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content‐type","text/html;charset=utf‐8");
            outputStream.write(html.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
