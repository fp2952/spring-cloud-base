package com.peng.zipkin.db;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;


/**
 * Created by fp295 on 2018/6/28.
 * zipkin mysql 存储实现
 */
@SpringCloudApplication
@EnableZipkinStreamServer
public class ZipkinDbApplication {
    public static void main (String[] args){
        SpringApplication.run(ZipkinDbApplication.class, args);
    }
}
