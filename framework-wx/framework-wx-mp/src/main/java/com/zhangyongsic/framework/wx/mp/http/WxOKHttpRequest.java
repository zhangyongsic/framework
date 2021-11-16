package com.zhangyongsic.framework.wx.mp.http;

import com.alibaba.fastjson.JSONObject;
import com.zhangyongsic.framework.lib.util.OKHttpHelper;
import com.zhangyongsic.framework.wx.mp.entity.WxAccessToken;
import com.zhangyongsic.framework.wx.mp.entity.WxError;

public class WxOKHttpRequest implements IWxHttpRequest{
    @Override
    public <T extends WxError> T get(String url, Class<T> tClass) {
        String json = OKHttpHelper.get(url);
        T wxResult = JSONObject.parseObject(json,tClass);
        wxResult.check();
        return wxResult;
    }

    @Override
    public <T extends WxError> T post(String url, String body, Class<T> tClass) {
        String json = OKHttpHelper.postJson(url,null,body);
        T wxResult = JSONObject.parseObject(json,tClass);
        wxResult.check();
        return wxResult;
    }
}
