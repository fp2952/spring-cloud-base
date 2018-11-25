package com.peng.auth.provider.config.auth.filter;

import com.peng.auth.provider.config.auth.token.QrAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fp295 on 2018/6/16.
 * 二维码登陆：
 *       post: /qrLogin?qrCode=token
 *  生成二维码，建立 websocket - 后台
 *  用户在移动端扫码，回调事件到后台
 *  后台通过websocket通知前台登陆
 *  前台post:/qrLogin, qrCode为生成二维码时后台生成的key
 */
public class QrLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SPRING_SECURITY_RESTFUL_QR_CODE_KEY = "qrCode";

    private static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/qrLogin";
    private boolean postOnly = true;

    public QrLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        AbstractAuthenticationToken authRequest;
        String principal;
        String credentials;

        // 二维码
        principal = obtainParameter(request, SPRING_SECURITY_RESTFUL_QR_CODE_KEY);
        credentials = null;

        principal = principal.trim();
        authRequest = new QrAuthenticationToken(principal, credentials);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainParameter(HttpServletRequest request, String parameter) {
        String result =  request.getParameter(parameter);
        return result == null ? "" : result;
    }
}
