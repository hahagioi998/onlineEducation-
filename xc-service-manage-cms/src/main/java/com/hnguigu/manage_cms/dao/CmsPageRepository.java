package com.hnguigu.manage_cms.dao;

import com.hnguigu.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
    //根据页面名称查询
    CmsPage findByPageName(String pageName);

    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String webPath);
}
