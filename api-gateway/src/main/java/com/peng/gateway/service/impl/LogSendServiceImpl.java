package com.peng.gateway.service.impl;

import com.netflix.zuul.context.RequestContext;
import com.peng.common.constant.MqQueueConstant;
import com.peng.gateway.service.LogSendService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fp295 on 2018/6/26.
 * 消息队列发送类
 */
/*@Component
public class LogSendServiceImpl implements LogSendService{

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Override
    public void send(RequestContext requestContext) {
        rabbitTemplate.convertAndSend(MqQueueConstant.LOG_QUEUE, requestContext.getRequest().getParameterMap().toString());
    }
}*/
