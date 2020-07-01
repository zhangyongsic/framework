package com.zhangyongsic.framework.shiro.token;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
@Data
public class UserPrincipal implements Serializable {
    private String userId;
    private String cacheKey;
    private String userName;
    private String password;
    private String jwtPrivateKey;
}
