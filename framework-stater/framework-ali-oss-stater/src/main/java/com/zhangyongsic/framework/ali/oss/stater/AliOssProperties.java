package com.zhangyongsic.framework.ali.oss.stater;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/29
 */
@Data
@ConfigurationProperties(prefix = "ali.oss")
public class AliOssProperties implements Serializable {
    private String domain;
    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
