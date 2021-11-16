package com.zhangyongsic.framework.wx.mp.service;

import com.zhangyongsic.framework.wx.mp.config.IWxMpConfig;
import com.zhangyongsic.framework.wx.mp.entity.WxMaJscode2Session;
import com.zhangyongsic.framework.wx.mp.http.IWxHttpRequest;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
public interface IWxMpService {
    String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    String JSCODE_TO_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    String getAccessToken();
    WxMaJscode2Session getWxMaJscode2Session(String code);


    void setWxMpConfig(IWxMpConfig wxMpConfig);
    IWxMpConfig getWxMpConfig();

    IWxHttpRequest getWxHttpRequest();
    void setWxHttpRequest(IWxHttpRequest wxHttpRequest);


    IWxMpTicketService getWxMpTicketService();



}
