package com.zhangyongsic.framework.shiro.token;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/07/29
 */
@Data
public class UnencryptedToken implements AuthenticationToken {
    private String unencrypted;
    private UserPrincipal userPrincipal;

    public UnencryptedToken(String unencrypted,UserPrincipal userPrincipal){
        this.unencrypted = unencrypted;
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Object getPrincipal() {
        return unencrypted;
    }

    @Override
    public Object getCredentials() {
        return unencrypted;
    }
}
