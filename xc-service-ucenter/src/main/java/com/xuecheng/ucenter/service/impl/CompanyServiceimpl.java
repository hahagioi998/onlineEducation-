package com.xuecheng.ucenter.service.impl;

import com.hnguigu.common.exception.CustomException;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.ucenter.XcCompany;
import com.xuecheng.ucenter.dao.XcCompanyRepository;
import com.xuecheng.ucenter.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

@Service
public class CompanyServiceimpl implements CompanyService {

    @Autowired
    private XcCompanyRepository xcCompanyRepository;

    @Override
    public ResponseResult addAndUpdate(XcCompany xcCompany) {
        XcCompany base = null;
        AddCourseResult addCourseResult = null;
        if(xcCompany!=null){
            base = xcCompanyRepository.save(xcCompany);
        }else{
            new CustomException(CommonCode.INVALID_PARAM);
        }
        ResultCode resultCode =null;
        if(base!=null){
            addCourseResult = new AddCourseResult(CommonCode.SUCCESS,base.getId());
        }else{
            addCourseResult = new AddCourseResult(CommonCode.FAIL,null);
        }
        return addCourseResult;
    }
}
