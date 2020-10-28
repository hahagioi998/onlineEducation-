package com.hnguigu.course.repository;


import com.hnguigu.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilesystemRepositor extends MongoRepository<FileSystem,String> {

}
