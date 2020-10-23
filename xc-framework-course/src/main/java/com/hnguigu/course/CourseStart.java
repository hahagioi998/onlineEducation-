package com.hnguigu.course;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan("com.hnguigu.domain.course")
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.hnguigu.api"})
@ComponentScan(basePackages = {"com.hnguigu.course"})
public class CourseStart {

    public static void main(String[] args) {
        SpringApplication.run(CourseStart.class,args);
    }
}
