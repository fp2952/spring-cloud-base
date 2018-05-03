package com.peng.auth.spring.boot.autoconfigure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;

/**
 * Created by fp295 on 2018/4/15.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http    // 配置授权管理器
                .authorizeRequests().accessDecisionManager(getAccessDecisionManager())
                // 匹配全部请求鉴权认证
                .antMatchers("/**").authenticated()
                // 由于使用的是JWT，我们这里不需要csrf
                .and().csrf().disable();
    }

    @Bean
    public AccessDecisionManager getAccessDecisionManager() {
        return new AccessDecisionManagerIml();
    }

    @Bean
    public TokenExtractor getTokenExtractor() {
        return new BearerTokenExtractor();
    }

}
