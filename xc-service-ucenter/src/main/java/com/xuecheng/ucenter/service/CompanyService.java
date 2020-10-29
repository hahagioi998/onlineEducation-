package com.xuecheng.ucenter.service;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.ucenter.XcCompany;

public interface CompanyService {

    //添加或者修改Company
    ResponseResult addAndUpdate(XcCompany xcCompany);
}
