package com.hnguigu.api.cms;


import com.hnguigu.domain.cms.CmsSite;

import java.util.List;

public interface CmsSiteControllerApi {

    /**
     * 查询所有站点
     * @return
     */
    List<CmsSite> queryAllSite();
}
