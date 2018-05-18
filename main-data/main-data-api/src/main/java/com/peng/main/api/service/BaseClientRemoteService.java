package com.peng.main.api.service;

import com.peng.common.pojo.ResponseData;
import com.peng.main.api.mapper.model.OauthClientDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by fp295 on 2018/5/18.
 */
public interface BaseClientRemoteService {

    @RequestMapping(value = "/client/all", method = RequestMethod.GET)
    ResponseData<List<OauthClientDetails>> getAllClient();

}
