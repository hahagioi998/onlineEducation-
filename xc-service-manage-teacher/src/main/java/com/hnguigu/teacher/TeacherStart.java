package com.hnguigu.teacher;


import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EntityScan("com.hnguigu.domain.ucenter")
@ComponentScan(basePackages = {"com.hnguigu.api.teacher"})
@ComponentScan(basePackages = {"com.hnguigu.teacher"})
@PropertySource(value = "classpath:minio.properties")
@EnableFeignClients
public class TeacherStart {

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

   /* @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }*/

    public static void main(String[] args) {
        SpringApplication.run(TeacherStart.class,args);
    }
}
