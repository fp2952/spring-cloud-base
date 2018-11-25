package com.peng.auth.provider.config.auth.token;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by fp295 on 2018/11/25.
 * 手机验证码token
 */
public class PhoneAuthenticationToken extends MyAuthenticationToken {

    public PhoneAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PhoneAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
