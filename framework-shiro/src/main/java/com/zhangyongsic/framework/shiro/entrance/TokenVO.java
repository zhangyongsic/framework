package com.zhangyongsic.framework.shiro.entrance;

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
public class TokenVO implements Serializable {
    private static final long serialVersionUID = -3599460632836659650L;

    /**
     * TokenVo
     * 访问token
     */
    private String token;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * token过期时间
     */
    private Date expireTime;
}
