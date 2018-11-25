package com.peng.auth.provider.config.auth;


import com.peng.auth.provider.config.auth.filter.PhoneLoginAuthenticationFilter;
import com.peng.auth.provider.config.auth.filter.QrLoginAuthenticationFilter;
import com.peng.auth.provider.config.auth.handler.MyLoginAuthSuccessHandler;
import com.peng.auth.provider.config.auth.provider.PhoneAuthenticationProvider;
import com.peng.auth.provider.config.auth.provider.QrAuthenticationProvider;
import com.peng.auth.provider.service.PhoneUserDetailService;
import com.peng.auth.provider.service.QrUserDetailService;
import com.peng.auth.provider.service.UsernameUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.*;

/**
 * Created by fp295 on 2018/4/15.
 */
@Configuration
@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 自动注入UserDetailsService
    @Autowired
    private UsernameUserDetailService usernameUserDetailService;

    @Autowired
    private PhoneUserDetailService phoneUserDetailService;

    @Autowired
    private QrUserDetailService qrUserDetailService;



    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //.addFilterAt(getMyLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(getPhoneLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(getQrLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 配置登陆页/login并允许访问
                .formLogin().loginPage("/login").permitAll()
                // 登出页
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/backReferer")
                // 其余所有请求全部需要鉴权认证
                .and().authorizeRequests().anyRequest().authenticated()
                // 由于使用的是JWT，我们这里不需要csrf
                .and().csrf().disable();
    }

    /**
     * 用户验证
     * @param auth
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(phoneAuthenticationProvider());
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(qrAuthenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder myEncoder(){
        return new BCryptPasswordEncoder(6);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider1 = new DaoAuthenticationProvider();
        // 设置userDetailsService
        provider1.setUserDetailsService(usernameUserDetailService);
        // 禁止隐藏用户未找到异常
        provider1.setHideUserNotFoundExceptions(false);
        // 使用BCrypt进行密码的hash
        provider1.setPasswordEncoder(myEncoder());
        return provider1;
    }


    @Bean
    public PhoneAuthenticationProvider phoneAuthenticationProvider(){
        PhoneAuthenticationProvider provider = new PhoneAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(phoneUserDetailService);
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public QrAuthenticationProvider qrAuthenticationProvider(){
        QrAuthenticationProvider provider = new QrAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(qrUserDetailService);
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }


    /**
     * 手机验证码登陆过滤器
     * @return
     */
    @Bean
    public PhoneLoginAuthenticationFilter getPhoneLoginAuthenticationFilter() {
        PhoneLoginAuthenticationFilter filter = new PhoneLoginAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(new MyLoginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
        return filter;
    }

    @Bean
    public QrLoginAuthenticationFilter getQrLoginAuthenticationFilter() {
        QrLoginAuthenticationFilter filter = new QrLoginAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(new MyLoginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
        return filter;
    }
}
