package com.zhangyongsic.framework.ali.sms;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/29
 */
@Data
public class DefaultAliSmsConfig implements AliSmsConfig, Serializable {
    private String accessKeyId;
    private String accessKeySecret;
}
