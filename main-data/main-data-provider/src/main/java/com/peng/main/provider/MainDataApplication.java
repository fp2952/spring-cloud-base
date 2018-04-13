package com.peng.main.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Created by fp295 on 2018/4/9.
 */
@SpringCloudApplication
public class MainDataApplication {
    public static void main (String[] args){
        SpringApplication.run(MainDataApplication.class, args);
    }
}
