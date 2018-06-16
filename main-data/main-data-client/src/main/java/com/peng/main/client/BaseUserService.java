package com.peng.main.client;

import com.peng.common.pojo.ResponseData;
import com.peng.main.api.InterfaceService;
import com.peng.main.api.mapper.model.BaseUser;
import com.peng.main.api.pojo.ResponseCode;
import com.peng.main.api.service.BaseUserRemoteService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by fp295 on 2018/4/9.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseUserService.HystrixClientFallback.class)
public interface BaseUserService extends BaseUserRemoteService {

    class HystrixClientFallback implements BaseUserService{

        @Override
        public ResponseData<BaseUser> getUserByUserName(@PathVariable("userName") String userName) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }

        @Override
        public ResponseData<BaseUser> getUserByPhone(@PathVariable("phone") String phone) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
        }
    }
}
