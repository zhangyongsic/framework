package com.zhangyongsic.framework.wx.mp.config;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
@Data
@ToString
@NoArgsConstructor
public class WxMpInMemoryConfig implements IWxMpConfig {
    private String appId;
    private String appSecret;
    private String accessToken;
    private long accessTokenExpired;

    private String jsapiTicket;
    private long jsapiTicketExpired;

    @Override
    public boolean isAccessTokenExpired() {
        return accessTokenExpired < System.currentTimeMillis();
    }

    @Override
    public void updateAccessToken(String token,Long expired) {
        setAccessToken(token);
        setAccessTokenExpired(System.currentTimeMillis() + (expired-200) * 1000);
    }

    @Override
    public boolean isJsapiTicketExpired() {
        return jsapiTicketExpired < System.currentTimeMillis();
    }


    @Override
    public void updateJsapiTicket(String ticket, Long expired) {
        setJsapiTicket(ticket);
        setJsapiTicketExpired(System.currentTimeMillis() + (expired-1200) * 1000);
    }
}
