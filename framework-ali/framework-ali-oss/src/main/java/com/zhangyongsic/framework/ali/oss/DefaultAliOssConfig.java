package com.zhangyongsic.framework.ali.oss;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/29
 */
@Data
public class DefaultAliOssConfig implements AliOssConfig, Serializable {
    private String domain;
    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
