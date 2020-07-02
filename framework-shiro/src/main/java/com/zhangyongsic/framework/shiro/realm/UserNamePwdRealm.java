package com.zhangyongsic.framework.shiro.realm;

import com.zhangyongsic.framework.encrypt.sha1.Sha1Encrypt;
import com.zhangyongsic.framework.shiro.token.JwtAuthenticationToken;
import com.zhangyongsic.framework.shiro.token.UserPrincipal;
import com.zhangyongsic.framework.shiro.token.UserNamePwdToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public class UserNamePwdRealm extends AuthorizingRealm {

    @Override
    public String getName() {
        return UserNamePwdRealm.class.getSimpleName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserNamePwdToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserPrincipal userPrincipal = (UserPrincipal) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info= new SimpleAuthorizationInfo();
        info.setStringPermissions(userPrincipal.getPermissions());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserNamePwdToken userNamePwdToken = (UserNamePwdToken) authenticationToken;
        UserPrincipal userPrincipal = userNamePwdToken.getUserPrincipal();
        System.out.println(userPrincipal);
        byte[] salt = Sha1Encrypt.getPasswordSalt(userPrincipal.getPassword());
        return new SimpleAuthenticationInfo(userPrincipal, userPrincipal.getPassword().substring(16),
                ByteSource.Util.bytes(salt), userPrincipal.getUserName());
    }
}
