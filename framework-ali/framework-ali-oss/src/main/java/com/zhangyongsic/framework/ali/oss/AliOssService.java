package com.zhangyongsic.framework.ali.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/29
 */
public class AliOssService {
    private AliOssConfig config;
    private OSS client;

    public AliOssService(AliOssConfig config) {
        this.config = config;
        this.client = new OSSClientBuilder().build(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
    }
    /**
     * 上传
     *
     * @param data
     * @param key
     * @return
     */
    public String upload(byte[] data, String key) {
        return ossUpload(new ByteArrayInputStream(data), key);
    }
    /**
     * 上传
     *
     * @param inputStream
     * @return
     */
    public String upload(InputStream inputStream, String key) {
        return ossUpload(inputStream, key);
    }
    /**
     * 上传
     *
     * @param inputStream
     * @param key
     * @return
     */
    private String ossUpload(InputStream inputStream, String key) {
        client.putObject(config.getBucketName(), key, inputStream);
        return config.getDomain() + "/" + key;
    }


    /**
     * 删除
     *
     * @param key
     */
    public void delete(String key) {
        client.deleteObject(config.getBucketName(), key);
    }

}
