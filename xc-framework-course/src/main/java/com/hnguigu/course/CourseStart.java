package com.hnguigu.course;


import com.hnguigu.common.interceptor.FeignClientInterceptor;
import io.minio.MinioClient;
import org.mybatis.spring.annotation.MapperScan;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@EntityScan("com.hnguigu.domain.course")
@MapperScan("com.hnguigu.course.repository")
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.hnguigu.api"})
@ComponentScan(basePackages = {"com.hnguigu.course"})
@PropertySource(value = "classpath:minio.properties")
public class CourseStart {

    @Value("${url}")
    private String url;

    @Value("${port}")
    private String port;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Bean
    public MinioClient getminio(){
        try {
            MinioClient minioClient = new MinioClient(url,9000,accessKey,secretKey);
            return minioClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SpringApplication.run(CourseStart.class,args);
    }

    @Bean
    public FeignClientInterceptor getFeignClientInterceptor(){
        return new FeignClientInterceptor();
    }
}
