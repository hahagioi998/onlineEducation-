package com.hnguigu.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@EntityScan(value={"com.hnguigu.domain.order","com.hnguigu.domain.task"})//扫描实体类
@ComponentScan(basePackages={"com.hnguigu.common"})//扫描framework中通用类
@ComponentScan(basePackages={"com.hnguigu.order"})//扫描本项目下的所有类
@ComponentScan(basePackages={"com.hnguigu.api"})//扫描接口
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ManageOrderApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ManageOrderApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
