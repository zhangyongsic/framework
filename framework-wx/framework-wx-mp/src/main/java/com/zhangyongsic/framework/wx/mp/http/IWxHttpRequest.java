package com.zhangyongsic.framework.wx.mp.http;

import com.zhangyongsic.framework.wx.mp.entity.WxError;

public interface IWxHttpRequest {
    <T extends WxError> T get(String url, Class<T> tClass);
    <T extends WxError> T post(String url,String body, Class<T> tClass);
}
