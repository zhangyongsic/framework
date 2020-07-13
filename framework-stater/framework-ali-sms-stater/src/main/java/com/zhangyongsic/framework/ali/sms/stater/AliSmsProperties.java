package com.zhangyongsic.framework.ali.sms.stater;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/07/13
 */
@Data
@ConfigurationProperties(prefix = "ali.sms")
public class AliSmsProperties implements Serializable {
    private String accessKeyId;
    private String accessKeySecret;
}
