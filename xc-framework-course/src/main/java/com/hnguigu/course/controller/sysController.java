package com.hnguigu.course.controller;

import com.hnguigu.api.course.SysControllerApi;
import com.hnguigu.course.service.sys.sysDictionaryService;
import com.hnguigu.domain.system.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys")
public class sysController implements SysControllerApi {

    @Autowired
    private sysDictionaryService sysDictionaryService;

    @GetMapping("/dictionary/get/{dType}")
    @ResponseBody
    @Override
    public SysDictionary findSysByDtype(@PathVariable String dType) {
        //查询mongoDB里面的sys_dictionary数据字典
        return sysDictionaryService.findSysByDtype(dType);
    }

}
