package com.peng.monitor.config;

import com.peng.monitor.filter.ReceiverNotifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * Created by fp295 on 2018/6/27.
 * 监控提醒
 */
@Configuration
@EnableScheduling
public class NotifierConfiguration {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MonitorReceiverPropertiesConfig monitorReceiverPropertiesConfig;
    @Bean
    @Primary
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remindingNotifier = new RemindingNotifier(mobileNotifier());
        remindingNotifier.setReminderPeriod(TimeUnit.MINUTES.toMillis(10));
        return remindingNotifier;
    }

    @Bean
    public ReceiverNotifier mobileNotifier(){
        return new ReceiverNotifier(monitorReceiverPropertiesConfig,rabbitTemplate);
    }

    @Scheduled(fixedRate = 60_000L)
    public void remind() {
        remindingNotifier().sendReminders();
    }
}
