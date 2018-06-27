package com.peng.gateway.service;

import com.netflix.zuul.context.RequestContext;

/**
 * Created by fp295 on 2018/6/26.
 */
public interface LogSendService {

    /**
     * 往消息通道发消息
     * @param requestContext
     */
    void send(RequestContext requestContext);
}
