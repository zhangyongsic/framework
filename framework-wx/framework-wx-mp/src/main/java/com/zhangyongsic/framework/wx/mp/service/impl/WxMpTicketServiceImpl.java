package com.zhangyongsic.framework.wx.mp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhangyongsic.framework.lib.crypto.SHA1;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import com.zhangyongsic.framework.lib.util.OKHttpHelper;
import com.zhangyongsic.framework.lib.util.RandomUtils;
import com.zhangyongsic.framework.wx.mp.entity.JsapiTicket;
import com.zhangyongsic.framework.wx.mp.entity.WxAccessToken;
import com.zhangyongsic.framework.wx.mp.entity.WxJsapiSignature;
import com.zhangyongsic.framework.wx.mp.service.IWxMpService;
import com.zhangyongsic.framework.wx.mp.service.IWxMpTicketService;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
public class WxMpTicketServiceImpl implements IWxMpTicketService {
    private IWxMpService wxMpService;

    public WxMpTicketServiceImpl(IWxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public String getJsapiTicket() {
        if (wxMpService.getWxMpConfig().isJsapiTicketExpired()){
            String url = String.format(GET_JSAPI_TICKET_URL,wxMpService.getAccessToken());
            String json = OKHttpHelper.get(url);
            JsapiTicket jsapiTicket = JSONObject.parseObject(json,JsapiTicket.class);
            jsapiTicket.check();
            wxMpService.getWxMpConfig().updateJsapiTicket(jsapiTicket.getTicket(),jsapiTicket.getExpiresIn());
        }
        return wxMpService.getWxMpConfig().getJsapiTicket();
    }

    @Override
    public WxJsapiSignature createJsapiSignature(String url) {
        long timestamp = System.currentTimeMillis() / 1000;
        String randomStr = RandomUtils.getRandomStr();
        String jsapiTicket = getJsapiTicket();
        String signature = SHA1.genWithAmple("jsapi_ticket=" + jsapiTicket,
                "noncestr=" + randomStr, "timestamp=" + timestamp, "url=" + url);
        return WxJsapiSignature
                .builder()
                .appId(this.wxMpService.getWxMpConfig().getAppId())
                .timestamp(timestamp)
                .nonceStr(randomStr)
                .url(url)
                .signature(signature)
                .build();
    }

}
