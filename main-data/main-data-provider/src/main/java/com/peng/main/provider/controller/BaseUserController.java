package com.peng.main.provider.controller;

import com.peng.common.pojo.ResponseData;
import com.peng.main.api.mapper.model.BaseUser;
import com.peng.main.api.pojo.ResponseCode;
import com.peng.main.api.service.BaseUserRemoteService;
import com.peng.main.provider.service.BaseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fp295 on 2018/4/9.
 */
@RestController
public class BaseUserController implements BaseUserRemoteService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseUserService baseUserService;

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    @Override
    public ResponseData<BaseUser> getUserByUserName(@PathVariable("userName") String userName) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUserName(userName);
        baseUser = baseUserService.selectOne(baseUser);
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), baseUser);
    }
}
