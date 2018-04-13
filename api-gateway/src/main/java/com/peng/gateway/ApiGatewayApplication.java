package com.peng.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by fp295 on 2018/4/10.
 */
@EnableZuulProxy
@SpringCloudApplication
public class ApiGatewayApplication {
    public static void main(String[] args){
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
