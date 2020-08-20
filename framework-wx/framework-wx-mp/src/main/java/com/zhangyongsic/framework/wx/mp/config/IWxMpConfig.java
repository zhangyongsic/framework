package com.zhangyongsic.framework.wx.mp.config;

/**
 * @program: framework
 * @description: s
 * @author: zhang yong
 * @create: 2020/08/19
 */
public interface IWxMpConfig {


    /**
     * appId
     * @return
     */
    String getAppId();
    /**
     *  secret
     * @return
     */
    String getAppSecret();
    /**
     * token 是否过期
     * @return
     */
    boolean isAccessTokenExpired();
    /**
     * 获取 token
     * @return
     */
    String getAccessToken();

    /**
     * 修改token
     * @param token
     */
    void updateAccessToken(String token,Long expired);

    /**
     * JsapiTicket
     * @return
     */
    boolean isJsapiTicketExpired();
    void updateJsapiTicket(String ticket,Long expired);
    String getJsapiTicket();

}
