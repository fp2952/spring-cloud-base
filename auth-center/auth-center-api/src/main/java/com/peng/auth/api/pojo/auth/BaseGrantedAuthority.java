package com.peng.auth.api.pojo.auth;

import com.peng.main.api.mapper.model.BaseRole;
import com.peng.main.api.mapper.model.BaseUser;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by fp295 on 2018/4/16.
 */
public class BaseGrantedAuthority implements GrantedAuthority {

    private BaseUser baseUser;

    private BaseRole baseRole;

    public BaseGrantedAuthority(){}

    public BaseGrantedAuthority(BaseUser baseUser, BaseRole baseRole){
        this.baseUser = baseUser;
        this.baseRole = baseRole;
    }

    @Override
    public String getAuthority() {
        return baseRole.getRoleCode();
    }

    public BaseUser getBaseUser() {
        return baseUser;
    }

    public void setBaseUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    public BaseRole getBaseRole() {
        return baseRole;
    }

    public void setBaseRole(BaseRole baseRole) {
        this.baseRole = baseRole;
    }
}
