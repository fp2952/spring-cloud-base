package com.peng.auth.provider.service;

import com.peng.common.pojo.ResponseData;
import com.peng.main.api.mapper.model.BaseModuleResources;
import com.peng.main.api.mapper.model.BaseRole;
import com.peng.main.api.mapper.model.BaseUser;
import com.peng.main.api.pojo.ResponseCode;
import com.peng.main.client.BaseModuleResourceService;
import com.peng.main.client.BaseRoleService;
import com.peng.main.client.BaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fp295 on 2018/4/16.
 */
@Service
public class BaseUserDetailService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private BaseRoleService baseRoleService;
    @Autowired
    private BaseModuleResourceService baseModuleResourceService;
    @Autowired
    private RedisTemplate<String, BaseRole> redisTemplate;
    @Autowired
    private RedisTemplate<String, BaseModuleResources> resourcesTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResponseData<BaseUser> baseUserResponseData = baseUserService.getUserByUserName(username);
        if(baseUserResponseData.getData() == null || baseUserResponseData.getCode() != ResponseCode.SUCCESS.getCode()){
            logger.error("找不到该用户，用户名：" + username);
            throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
        }
        BaseUser baseUser = baseUserResponseData.getData();

        ResponseData<List<BaseRole>> baseRoleListResponseData = baseRoleService.getRoleByUserId(baseUser.getId());
        List<BaseRole> roles;
        if(baseRoleListResponseData.getData() == null || baseRoleListResponseData.getCode() != ResponseCode.SUCCESS.getCode()){
            logger.error("查询角色失败！");
            roles = new ArrayList<>();
        }else {
            roles = baseRoleListResponseData.getData();
        }


        return null;
    }
}
