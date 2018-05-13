package com.peng.main.provider.controller;

import com.peng.auth.spring.boot.autoconfigure.utils.AccessTokenUtils;
import com.peng.common.pojo.ResponseData;
import com.peng.main.api.mapper.model.BaseModuleResources;
import com.peng.main.api.pojo.ResponseCode;
import com.peng.main.api.service.BaseModuleResourcesRemoteService;
import com.peng.main.provider.service.BaseModuleResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fp295 on 2018/4/17.
 */
@RestController
public class BaseModuleResourceController implements BaseModuleResourcesRemoteService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseModuleResourceService baseModuleResourceService;

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    @Override
    public ResponseData<List<BaseModuleResources>> getMenusByUserId(@PathVariable("userId") String userId) {
        logger.debug("根据用户查询菜单");
        List<BaseModuleResources> list;
        try {
            list = baseModuleResourceService.getMenusByUserId(userId);
        }catch (Exception e){
            logger.error("根据用户查询菜单错误");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/menu")
    public ResponseData<List<BaseModuleResources>> getCurrentMenu() {
        logger.debug("查询当前用户菜单");
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), accessTokenUtils.getMenuInfo());
    }
}
