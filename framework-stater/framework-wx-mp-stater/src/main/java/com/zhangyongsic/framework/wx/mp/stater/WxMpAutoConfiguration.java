package com.zhangyongsic.framework.wx.mp.stater;

import com.zhangyongsic.framework.wx.mp.config.IWxMpConfig;
import com.zhangyongsic.framework.wx.mp.config.WxMpInMemoryConfig;
import com.zhangyongsic.framework.wx.mp.service.IWxMpService;
import com.zhangyongsic.framework.wx.mp.service.impl.WxMpServiceImpl;
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
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpAutoConfiguration {

    @Autowired
    private WxMpProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public IWxMpService wxMpService() {
        IWxMpService wxMpService = new WxMpServiceImpl();
        IWxMpConfig wxMpConfig = new WxMpInMemoryConfig();
        ((WxMpInMemoryConfig) wxMpConfig).setAppId(properties.getAppId());
        ((WxMpInMemoryConfig) wxMpConfig).setAppSecret(properties.getAppSecret());
        wxMpService.setWxMpConfig(wxMpConfig);
        return wxMpService;
    }

}
