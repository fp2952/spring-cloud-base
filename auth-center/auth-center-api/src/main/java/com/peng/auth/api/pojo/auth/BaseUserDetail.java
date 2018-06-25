package com.peng.auth.api.pojo.auth;

import com.peng.main.api.mapper.model.BaseUser;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created by fp295 on 2018/4/29.
 * 包装org.springframework.security.core.userdetails.User类
 * 新增 baseUser 用于生成 jwt 的用户信息
 */
public class BaseUserDetail implements UserDetails, CredentialsContainer {

    private final BaseUser baseUser;
    private final org.springframework.security.core.userdetails.User user;

    public BaseUserDetail(BaseUser baseUser, User user) {
        this.baseUser = baseUser;
        this.user = user;
    }


    @Override
    public void eraseCredentials() {
        user.eraseCredentials();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public BaseUser getBaseUser() {
        return baseUser;
    }
}
