package com.leyou.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName LyUploadApplication
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/4 22:38
 * @Version 1.0
 */
@EnableEurekaClient
@SpringBootApplication
public class LyUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyUploadApplication.class, args);
    }
}
