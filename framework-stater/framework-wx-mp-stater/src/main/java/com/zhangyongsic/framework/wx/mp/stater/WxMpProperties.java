package com.zhangyongsic.framework.wx.mp.stater;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties implements Serializable {
    private String appId;
    private String appSecret;
}
