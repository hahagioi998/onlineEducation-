package com.hnguigu.course.service.sys;

import com.hnguigu.domain.system.SysDictionary;

public interface sysDictionaryService {

    //查询sys_dictionary里面Dtype等于200的数据
    SysDictionary findSysByDtype(String Dtype);
}
