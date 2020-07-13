package com.zhangyongsic.framework.ali.sms.stater;

import com.zhangyongsic.framework.ali.sms.AliSmsService;
import com.zhangyongsic.framework.ali.sms.DefaultAliSmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: smdh2
 * @description: 阿里云OSS默认配置
 * @author: zhang yong
 * @create: 2019/10/23
 */
@Configuration
@EnableConfigurationProperties(AliSmsProperties.class)
public class AliSmsAutoConfiguration {

    @Autowired
    private AliSmsProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public AliSmsService aliSmsService() {
        DefaultAliSmsConfig config = new DefaultAliSmsConfig();
        config.setAccessKeyId(properties.getAccessKeyId());
        config.setAccessKeySecret(properties.getAccessKeySecret());
        AliSmsService ossService = new AliSmsService(config);
        return ossService;
    }

}
