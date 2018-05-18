package com.peng.main.client;

import com.peng.common.pojo.ResponseData;
import com.peng.main.api.InterfaceService;
import com.peng.main.api.mapper.model.BaseUser;
import com.peng.main.api.mapper.model.OauthClientDetails;
import com.peng.main.api.pojo.ResponseCode;
import com.peng.main.api.service.BaseClientRemoteService;
import com.peng.main.api.service.BaseUserRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseClientService.HystrixClientFallback.class)
public interface BaseClientService extends BaseClientRemoteService {

    class HystrixClientFallback implements BaseClientService {

        @Override
        public ResponseData<List<OauthClientDetails>> getAllClient() {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
