package com.hnguigu.course.repository;


import com.hnguigu.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysDictionaryRepositor extends MongoRepository<SysDictionary,String> {

}
