package com.hnguigu.api.auth;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.ucenter.request.LoginRequest;
import com.hnguigu.domain.ucenter.response.JwtResult;
import com.hnguigu.domain.ucenter.response.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator.
 */
@Api(value = "用户认证",description = "用户认证接口")
public interface AuthControllerApi {
    @ApiOperation("登录")
    public LoginResult login(LoginRequest loginRequest);

    @ApiOperation("退出")
    public ResponseResult logout();

    @ApiOperation("查询用户jwt令牌")
    public JwtResult userjwt();

    @ApiOperation("查询用户jwt令牌带参")
    public JwtResult userjwtbyid(String uid);
}
