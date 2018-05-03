package com.peng.auth.spring.boot.autoconfigure.config;

import com.peng.auth.spring.boot.autoconfigure.utils.AccessTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * Created by fp295 on 2018/4/29.
 */
@ConfigurationProperties(prefix = "security")
public class AccessDecisionManagerIml  implements AccessDecisionManager {

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    private AntPathMatcher matcher = new AntPathMatcher();

    private String ignored;



    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
