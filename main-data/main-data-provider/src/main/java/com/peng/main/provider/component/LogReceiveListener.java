package com.peng.main.provider.component;

import com.peng.common.constant.MqQueueConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by fp295 on 2018/6/26.
 */
@Component
@RabbitListener(queues = MqQueueConstant.LOG_QUEUE)
public class LogReceiveListener {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver : " + hello);
    }
}
