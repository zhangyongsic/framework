package com.zhangyongsic.framework.ali.oss;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/29
 */
public interface AliOssConfig {
     String getDomain();
     String getEndPoint();
     String getAccessKeyId();
     String getAccessKeySecret();
     String getBucketName();
}
