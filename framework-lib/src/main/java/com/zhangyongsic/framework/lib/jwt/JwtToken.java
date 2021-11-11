package com.zhangyongsic.framework.lib.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
@Data
@ToString
@NoArgsConstructor
public class JwtToken implements Serializable {


    private static final long serialVersionUID = 9196513930848114262L;
    /**
     * 访问token
     */
    private String accessToken;
    /**
     * 刷新token(过期时间为accessToken的两倍)
     */
    private String refreshToken;
    /**
     * token过期时间
     */
    private Date expireTime;

    public JwtToken(String accessToken, String refreshToken, Date expireTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }
}
