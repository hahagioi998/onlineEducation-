package com.hnguigu.dictionary.dao;

import com.hnguigu.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
}
