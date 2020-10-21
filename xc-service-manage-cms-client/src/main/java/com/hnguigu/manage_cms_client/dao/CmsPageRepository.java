package com.hnguigu.manage_cms_client.dao;

import com.hnguigu.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
}
