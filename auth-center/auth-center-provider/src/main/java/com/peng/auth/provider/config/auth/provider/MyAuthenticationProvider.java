package com.peng.auth.provider.config.auth.provider;

import com.peng.auth.provider.config.auth.filter.MyLoginAuthenticationFilter;
import com.peng.auth.provider.config.auth.token.MyAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * Created by fp295 on 2018/6/16.
 * 自定义密码验证
 * 实现自定义的 MyAbstractUserDetailsAuthenticationProvider 抽象类
 * 根据登陆的类型 执行不同的校验
 */
public class MyAuthenticationProvider extends MyAbstractUserDetailsAuthenticationProvider {

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    private PasswordEncoder passwordEncoder;
    private String userNotFoundEncodedPassword;
    private SaltSource saltSource;
    private UserDetailsService userDetailsService;

    public MyAuthenticationProvider() {
        this.setPasswordEncoder((PasswordEncoder)(new PlaintextPasswordEncoder()));
    }

    /**
     * 自定义验证
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails, MyAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;
        if(this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }

        if(authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();

            // 验证开始
            if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_PHONE.equals(authentication.getType())){
                // 验证码验证，调用公共服务查询 key 为authentication.getPrincipal()的value， 并判断其与验证码是否匹配
                if(!"1000".equals(presentedPassword)){
                    this.logger.debug("Authentication failed: verifyCode does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad verifyCode"));
                }
            }else if(MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_QR.equals(authentication.getType())){
                // 二维码只需要根据 SPRING_SECURITY_RESTFUL_QR_CODE_KEY 查询到用户即可，所以此处无需验证
            }
            else {
                // 用户名密码验证
                if(!this.passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
                    this.logger.debug("Authentication failed: password does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                }
            }
        }
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected final UserDetails retrieveUser(String username, MyAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            // 调用loadUserByUsername时加入type前缀
            loadedUser = this.getUserDetailsService().loadUserByUsername(authentication.getType() + ":" + username);
        } catch (UsernameNotFoundException var6) {
            if(authentication.getCredentials() != null) {
                String presentedPassword = authentication.getCredentials().toString();
                this.passwordEncoder.isPasswordValid(this.userNotFoundEncodedPassword, presentedPassword, (Object)null);
            }

            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }

        if(loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    }

    public void setPasswordEncoder(Object passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        if(passwordEncoder instanceof PasswordEncoder) {
            this.setPasswordEncoder((PasswordEncoder)passwordEncoder);
        } else if(passwordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
            final org.springframework.security.crypto.password.PasswordEncoder delegate = (org.springframework.security.crypto.password.PasswordEncoder)passwordEncoder;
            this.setPasswordEncoder(new PasswordEncoder() {
                public String encodePassword(String rawPass, Object salt) {
                    this.checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                    this.checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }

                private void checkSalt(Object salt) {
                    Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
                }
            });
        } else {
            throw new IllegalArgumentException("passwordEncoder must be a PasswordEncoder instance");
        }
    }

    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.userNotFoundEncodedPassword = passwordEncoder.encodePassword("userNotFoundPassword", (Object)null);
        this.passwordEncoder = passwordEncoder;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    protected SaltSource getSaltSource() {
        return this.saltSource;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }
}
