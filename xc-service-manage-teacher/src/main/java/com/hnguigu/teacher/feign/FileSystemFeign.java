package com.hnguigu.teacher.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xc-framework-course")
public interface FileSystemFeign {
    //删除filesystem表的数据//根据地址删除
    @DeleteMapping("/course/Deletefilesystem")
    void deletefilesystem (@RequestParam(value = "pic") String pic);
}
