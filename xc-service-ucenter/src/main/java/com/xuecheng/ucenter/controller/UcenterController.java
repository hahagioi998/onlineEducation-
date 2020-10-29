package com.xuecheng.ucenter.controller;

import com.hnguigu.api.ucenter.UcenterControllerApi;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.ucenter.XcCompany;
import com.hnguigu.domain.ucenter.XcCompanyUser;
import com.hnguigu.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.XcCompanyRepository;
import com.xuecheng.ucenter.dao.XcCompanyUserRepository;
import com.xuecheng.ucenter.service.CompanyService;
import com.xuecheng.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
    @Autowired
    UserService userService;

    @Autowired
    private XcCompanyUserRepository xcCompanyUserRepository;

    @Autowired
    private XcCompanyRepository xcCompanyRepository;

    @Autowired
    private CompanyService companyService;

    @Override
    @GetMapping("/getuserext")
    public XcUserExt getUserext(@RequestParam("username") String username) {
        return userService.getUserExt(username);
    }

    @GetMapping("/findcompanyByuserId/{userId}")
    @Override
    public XcCompanyUser findByuserId(@PathVariable String userId) {
        XcCompanyUser xcCompanyUser = xcCompanyUserRepository.findByUserId(userId);
        return xcCompanyUser;
    }

    @GetMapping("/findcompany/{userId}")
    @Override
    public XcCompany findCompany(@PathVariable String userId) {
        XcCompanyUser xcCompanyUser = xcCompanyUserRepository.findByUserId(userId);
        String companyId = xcCompanyUser.getCompanyId();
        Optional<XcCompany> xcCompany = xcCompanyRepository.findById(companyId);
        XcCompany xcCompany1 = null;
        if(xcCompany.isPresent()){
            xcCompany1 = xcCompany.get();
        }
        return xcCompany1;
    }

    @PostMapping("/updatecompany")
    @Override
    public ResponseResult addAndUpdate(@RequestBody XcCompany xcCompany) {
        ResponseResult responseResult = companyService.addAndUpdate(xcCompany);
        return responseResult;
    }
}
