package com.peng.main.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by fp295 on 2018/4/9.
 */
@EnableResourceServer
@SpringCloudApplication
public class MainDataApplication {
    public static void main (String[] args){
        SpringApplication.run(MainDataApplication.class, args);
    }
}
