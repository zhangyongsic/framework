package com.zhangyongsic.framework.wx.mp.service;

import com.zhangyongsic.framework.wx.mp.config.IWxMpConfig;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
public interface IWxMpService {
    String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    String getAccessToken();
    void setWxMpConfig(IWxMpConfig wxMpConfig);
    IWxMpConfig getWxMpConfig();
    IWxMpTicketService getWxMpTicketService();
}
