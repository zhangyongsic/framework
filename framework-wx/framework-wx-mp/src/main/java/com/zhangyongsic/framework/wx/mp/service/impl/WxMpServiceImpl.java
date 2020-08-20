package com.zhangyongsic.framework.wx.mp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhangyongsic.framework.lib.util.OKHttpHelper;
import com.zhangyongsic.framework.wx.mp.config.IWxMpConfig;
import com.zhangyongsic.framework.wx.mp.entity.WxAccessToken;
import com.zhangyongsic.framework.wx.mp.service.IWxMpService;
import com.zhangyongsic.framework.wx.mp.service.IWxMpTicketService;
import lombok.Data;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
@Data
public class WxMpServiceImpl implements IWxMpService {
    private IWxMpConfig wxMpConfig;
    private IWxMpTicketService ticketService = new WxMpTicketServiceImpl(this);

    @Override
    public String getAccessToken() {
        if (wxMpConfig.isAccessTokenExpired()){
            String url = String.format(GET_ACCESS_TOKEN_URL,wxMpConfig.getAppId(),wxMpConfig.getAppSecret());
            String json = OKHttpHelper.get(url);
            WxAccessToken accessToken = JSONObject.parseObject(json,WxAccessToken.class);
            accessToken.check();
            wxMpConfig.updateAccessToken(accessToken.getAccessToken(),accessToken.getExpiresIn());
        }
        return wxMpConfig.getAccessToken();
    }

    @Override
    public IWxMpTicketService getWxMpTicketService() {
        return ticketService;
    }

}
