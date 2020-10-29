package com.hnguigu.course.controller;

import com.hnguigu.course.service.course.CategoryService;
import com.hnguigu.domain.course.ext.CategoryNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/category")
public class categoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @ResponseBody
    public CategoryNode list(){
        CategoryNode categoryNode = categoryService.findList();
        return categoryNode;

    }
}
