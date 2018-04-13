package com.peng.db.spring.boot.autoconfigure.config.pagehelper;

/**
 * Created by fp295 on 2018/4/7.
 */

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * 自定注入分页插件
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(PageHelperProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class PageHelperAutoConfiguration {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    private PageHelperProperties properties;

    /**
     * 接受分页插件额外的属性
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = PageHelperProperties.PAGEHELPER_PREFIX)
    public Properties pageHelperProperties() {
        return new Properties();
    }

    @PostConstruct
    public void addPageInterceptor() {
        PageInterceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        //先把一般方式配置的属性放进去
        properties.putAll(pageHelperProperties());
        //在把特殊配置放进去，由于close-conn 利用上面方式时，属性名就是 close-conn 而不是 closeConn，所以需要额外的一步
        properties.putAll(this.properties.getProperties());
        interceptor.setProperties(properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }

}