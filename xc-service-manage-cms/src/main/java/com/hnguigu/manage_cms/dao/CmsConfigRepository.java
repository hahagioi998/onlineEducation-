package com.hnguigu.manage_cms.dao;


import com.hnguigu.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
