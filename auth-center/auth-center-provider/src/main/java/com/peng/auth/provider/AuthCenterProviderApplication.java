package com.peng.auth.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Created by fp295 on 2018/4/2.
 */
@SpringCloudApplication
public class AuthCenterProviderApplication {
   public static void main(String[] args){
       SpringApplication.run(AuthCenterProviderApplication.class, args);
   }
}
