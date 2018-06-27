package com.peng.mc.service.listener;

import com.peng.common.constant.MqQueueConstant;
import com.peng.common.pojo.MobileMsgTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 监听服务状态改变发送请求
 */
@Component
@RabbitListener(queues = MqQueueConstant.SERVICE_STATUS_CHANGE)
public class ServiceChangeReceiveListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void receive(MobileMsgTemplate mobileMsgTemplate) {
        long startTime = System.currentTimeMillis();
        logger.info("消息中心接收到短信发送请求-> 手机号：{} -> 验证码: {} ", mobileMsgTemplate.getMobile(), mobileMsgTemplate.getText());
        long useTime = System.currentTimeMillis() - startTime;
        logger.info("调用 {} 短信网关处理完毕，耗时 {}毫秒", mobileMsgTemplate.getType(), useTime);
    }
}
