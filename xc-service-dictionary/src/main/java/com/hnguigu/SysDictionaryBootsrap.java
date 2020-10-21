package com.hnguigu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EntityScan("com.hnguigu.domain.system")
@ComponentScan(basePackages = {"com.hnguigu.api"})
@ComponentScan(basePackages = {"com.hnguigu.dictionary"})
public class SysDictionaryBootsrap {
    public static void main(String[] args) {
        SpringApplication.run(SysDictionaryBootsrap.class,args);
    }
}
