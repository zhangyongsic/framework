package com.zhangyongsic.framework.shiro.realm;

import com.zhangyongsic.framework.encrypt.sha1.Sha1Encrypt;
import com.zhangyongsic.framework.shiro.token.UnencryptedToken;
import com.zhangyongsic.framework.shiro.token.UserNamePwdToken;
import com.zhangyongsic.framework.shiro.token.UserPrincipal;
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
 * @create: 2020/07/29
 */
public class UnencryptedRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return UnencryptedRealm.class.getSimpleName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UnencryptedToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserPrincipal userPrincipal = (UserPrincipal) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info= new SimpleAuthorizationInfo();
        info.setStringPermissions(userPrincipal.getPermissions());
        info.setRoles(userPrincipal.getRoles());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UnencryptedToken unencryptedToken = (UnencryptedToken) authenticationToken;
        UserPrincipal userPrincipal = unencryptedToken.getUserPrincipal();
        return new SimpleAuthenticationInfo(userPrincipal, unencryptedToken.getUnencrypted(), userPrincipal.getUserName());
    }
}
