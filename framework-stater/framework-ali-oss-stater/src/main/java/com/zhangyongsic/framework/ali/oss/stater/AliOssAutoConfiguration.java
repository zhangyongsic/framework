package com.zhangyongsic.framework.ali.oss.stater;

import com.zhangyongsic.framework.ali.oss.AliOssConfig;
import com.zhangyongsic.framework.ali.oss.AliOssService;
import com.zhangyongsic.framework.ali.oss.DefaultAliOssConfig;
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
@EnableConfigurationProperties(AliOssProperties.class)
public class AliOssAutoConfiguration {

    @Autowired
    private AliOssProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public AliOssService aliOssService() {
        DefaultAliOssConfig config = new DefaultAliOssConfig();
        config.setDomain(properties.getDomain());
        config.setEndPoint(properties.getEndPoint());
        config.setBucketName(properties.getBucketName());
        config.setAccessKeyId(properties.getAccessKeyId());
        config.setAccessKeySecret(properties.getAccessKeySecret());
        AliOssService ossService = new AliOssService(config);
        return ossService;
    }

}
