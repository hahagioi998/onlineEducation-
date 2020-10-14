package com.hnguigu.api.cms;

import com.hnguigu.domain.cms.CmsConfig;
import io.swagger.annotations.Api;

@Api(value="cms配置管理接口",description = "cms配置管理接口，提供数据模型的管理、查询接口")
public interface CmsConfigControllerApi {
    //根据id查询CMS配置信息
    public CmsConfig getmodel(String id);
}
