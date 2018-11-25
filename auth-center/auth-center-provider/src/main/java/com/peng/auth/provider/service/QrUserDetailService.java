package com.peng.auth.provider.service;

import com.peng.main.api.mapper.model.BaseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by fp295 on 2018/11/25.
 */
@Service
public class QrUserDetailService extends BaseUserDetailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected BaseUser getUser(String qrCode) {
        return null;
    }
}
