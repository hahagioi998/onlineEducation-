package com.hnguigu.api.course;

import com.hnguigu.domain.system.SysDictionary;

public interface SysControllerApi {

    //查询sys_dictionary里面Dtype等于200的数据
    SysDictionary findSysByDtype(String Dtype);
}
