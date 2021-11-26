package com.zhangyongsic.framework.lib.helper;

import okhttp3.*;
import okhttp3.Request.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyong
 */
public class OKHttpHelper {

    private static Logger logger = LoggerFactory.getLogger(OKHttpHelper.class);

    private static OkHttpClient getClient() {
        // 超时10s
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();
        return client;
    }

    private static Builder builderUrlAndHeader(String url, Map<String, String> header) {
        logger.info("请求地址:" + url);
        logger.info("请求头:" + header);
        Builder builder = new Request.Builder().url(url);
        if (header != null && header.size() > 0) {
            for (String headerKey : header.keySet()) {
                builder.addHeader(headerKey, header.get(headerKey));
            }
        }
        return builder;
    }

    /**
     * 执行request 返回数据
     *
     * @param request
     * @return
     */
    private static String execute(Request request) {
        OkHttpClient okHttpClient = getClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * get请求 参数拼接在url当中
     *
     * @param url
     * @param header
     * @return
     */
    public static String get(String url, Map<String, String> header) {
        Builder builder = builderUrlAndHeader(url, header);
        Request request = builder.build();
        return execute(request);
    }


    public static String get(String url) {
        return get(url, null);
    }

    /**
     * post表单数据
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static String postForm(String url, Map<String, String> header, Map<String, Object> params) {
        Builder builder = builderUrlAndHeader(url, header);
        okhttp3.FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (String paramKey : params.keySet()) {
                formBuilder.add(paramKey, params.get(paramKey).toString());
            }
        }
        Request request = builder.post(formBuilder.build()).build();
        return execute(request);
    }

    /**
     * post Json数据
     *
     * @param url
     * @param header
     * @param json   提交的json数据
     * @return
     */
    public static String postJson(String url, Map<String, String> header, String json) {
        Builder builder = builderUrlAndHeader(url, header);
        Request request = builder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json == null ? "" : json)).build();
        return execute(request);
    }

    /**
     * 支持多文件
     *
     * @param url
     * @param header
     * @param files
     * @return
     */
    public static String postFile(String url, Map<String, String> header, File[] files) {
        Builder builder = builderUrlAndHeader(url, header);
        okhttp3.MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        if (files != null && files.length > 0) {
            for (File file : files) {
                multipartBodyBuilder.addFormDataPart(file.getName(), file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        }
        Request request = builder.post(multipartBodyBuilder.build()).build();
        return execute(request);
    }

    /**
     * 提交xml数据
     *
     * @param url
     * @param header
     * @param xml
     * @return
     */
    public static String postXml(String url, Map<String, String> header, String xml) {
        Builder builder = builderUrlAndHeader(url, header);
        Request request = builder.post(RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml)).build();
        return execute(request);
    }

    public static String postXml(String url, String xml) {
        return postXml(url, null, xml);
    }

    public static void main(String[] args) {
        String rtn = OKHttpHelper.get("http://apis.juhe.cn/lottery/history?key=e3e50a79ecfd9ab6e2ace760ddc7fd07&lottery_id=ssq&page_size=5&page=1", null);
        System.out.println(rtn);
        rtn = OKHttpHelper.postJson("http://apis.juhe.cn/lottery/types?key=e3e50a79ecfd9ab6e2ace760ddc7fd07", null, "");
        System.out.println(rtn);
    }

}
