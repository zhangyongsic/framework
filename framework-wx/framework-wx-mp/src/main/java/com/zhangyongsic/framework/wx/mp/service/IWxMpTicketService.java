package com.zhangyongsic.framework.wx.mp.service;

import com.zhangyongsic.framework.wx.mp.entity.WxJsapiSignature;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
public interface IWxMpTicketService {
    String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
    String getJsapiTicket();
    WxJsapiSignature createJsapiSignature(String url);
}
