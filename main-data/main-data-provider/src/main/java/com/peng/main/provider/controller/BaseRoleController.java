package com.peng.main.provider.controller;

import com.peng.common.pojo.ResponseData;
import com.peng.main.api.mapper.model.BaseRole;
import com.peng.main.api.pojo.ResponseCode;
import com.peng.main.api.service.BaseRoleRemoteService;
import com.peng.main.provider.service.BaseRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fp295 on 2018/4/17.
 */
@RestController
public class BaseRoleController implements BaseRoleRemoteService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseRoleService baseRoleService;

    @Override
    public ResponseData<List<BaseRole>> getRoleByUserId(@PathVariable("userId") String userId) {
        logger.debug("根据用户查询角色");
        List<BaseRole> list;
        try {
            list = baseRoleService.getRoleByUserId(userId);
        }catch (Exception e){
            logger.error("根据用户查询角色失败");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }
}
