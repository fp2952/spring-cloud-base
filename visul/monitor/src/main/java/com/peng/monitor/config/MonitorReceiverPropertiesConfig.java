package com.peng.monitor.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 服务监控接受者
 */
@Configuration
@ConditionalOnExpression("!'${receiver}'.isEmpty()")
@ConfigurationProperties(prefix = "receiver")
public class MonitorReceiverPropertiesConfig {
    private String[] mobiles;

    private String[] emails;

    public String[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }
}
