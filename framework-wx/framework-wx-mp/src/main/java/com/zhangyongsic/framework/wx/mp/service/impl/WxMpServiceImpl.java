package com.zhangyongsic.framework.wx.mp.service.impl;

import com.zhangyongsic.framework.wx.mp.config.IWxMpConfig;
import com.zhangyongsic.framework.wx.mp.entity.WxAccessToken;
import com.zhangyongsic.framework.wx.mp.entity.WxMaJscode2Session;
import com.zhangyongsic.framework.wx.mp.http.IWxHttpRequest;
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
    private IWxHttpRequest wxHttpRequest;
    private IWxMpTicketService wxMpTicketService = new WxMpTicketServiceImpl(this);

    @Override
    public String getAccessToken() {
        if (wxMpConfig.isAccessTokenExpired()){
            String url = String.format(GET_ACCESS_TOKEN_URL,wxMpConfig.getAppId(),wxMpConfig.getAppSecret());
            WxAccessToken accessToken = wxHttpRequest.get(url,WxAccessToken.class);
            wxMpConfig.updateAccessToken(accessToken.getAccessToken(),accessToken.getExpiresIn());
        }
        return wxMpConfig.getAccessToken();
    }

    @Override
    public WxMaJscode2Session getWxMaJscode2Session(String code) {
        String url = String.format(JSCODE_TO_SESSION_URL,wxMpConfig.getAppId(),wxMpConfig.getAppSecret(),code);
        return wxHttpRequest.get(url,WxMaJscode2Session.class);
    }
}
