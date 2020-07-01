package com.zhangyongsic.framework.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public class UserNamePwdToken extends UsernamePasswordToken {
    private UserPrincipal userPrincipal;
    public UserNamePwdToken(String username, String password,UserPrincipal userPrincipal) {
        super(username, password);
        this.userPrincipal = userPrincipal;
    }

    public UserPrincipal getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }
}
