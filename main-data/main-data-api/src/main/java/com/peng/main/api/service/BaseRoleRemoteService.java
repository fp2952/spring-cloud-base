package com.peng.main.api.service;

import com.peng.common.pojo.ResponseData;
import com.peng.main.api.mapper.model.BaseRole;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by fp295 on 2018/4/16.
 */
public interface BaseRoleRemoteService {

    /**
     * 根据userId查询角色
     * @param userId
     * @return
     */
    @RequestMapping(value = "/role/user/{userId}", method = RequestMethod.GET)
    ResponseData<List<BaseRole>> getRoleByUserId(@PathVariable("userId")String userId);
}
