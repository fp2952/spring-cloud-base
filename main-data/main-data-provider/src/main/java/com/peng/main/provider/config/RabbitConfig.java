package com.peng.main.provider.config;

import com.peng.common.constant.MqQueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by fp295 on 2018/6/26.
 * rabbit初始化
 */
@Configuration
public class RabbitConfig {
    /**
     * 初始化 log队列
     *
     * @return
     */
    @Bean
    public Queue initLogQueue() {
        return new Queue(MqQueueConstant.LOG_QUEUE);
    }

    /**
     * 初始化 手机短信通道
     *
     * @return
     */
    @Bean
    public Queue initMobileCodeQueue() {
        return new Queue(MqQueueConstant.MOBILE_QUEUE);
    }

    /**
     * 初始化服务状态改变队列
     *
     * @return
     */
    @Bean
    public Queue initServiceStatusChangeQueue() {
        return new Queue(MqQueueConstant.SERVICE_STATUS_CHANGE);
    }

}
