package com.peng.auth.api.token;

import com.peng.auth.api.pojo.Constant;
import com.peng.auth.api.pojo.auth.BaseGrantedAuthority;
import com.peng.common.utils.JsonUtils;
import com.peng.main.api.mapper.model.BaseUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import java.util.Map;

/**
 * Created by fp295 on 2018/4/16.
 */
public class JwtAccessToken extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);

        // 设置用户信息
        BaseGrantedAuthority baseGrantedAuthority = (BaseGrantedAuthority)authentication.getAuthorities().toArray()[0];
        BaseUser baseUser = baseGrantedAuthority.getBaseUser();
        baseUser.setPassword(null);
        defaultOAuth2AccessToken.getAdditionalInformation().put(Constant.USER_INFO, baseUser);

        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map){
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        convertData(oauth2AccessToken, oauth2AccessToken.getAdditionalInformation());
        return oauth2AccessToken;
    }

    private void convertData(OAuth2AccessToken accessToken,  Map<String, ?> map) {
        accessToken.getAdditionalInformation().put(Constant.USER_INFO,convertUserData(map.get(Constant.USER_INFO)));

    }

    private BaseUser convertUserData(Object map) {
        String json = JsonUtils.deserializer(map);
        BaseUser user = JsonUtils.serializable(json, BaseUser.class);
        return user;
    }
}
