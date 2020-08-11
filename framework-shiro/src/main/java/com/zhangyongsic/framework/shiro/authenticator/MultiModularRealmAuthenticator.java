package com.zhangyongsic.framework.shiro.authenticator;


import com.zhangyongsic.framework.shiro.encrpty.ShiroEncrptyTool;
import com.zhangyongsic.framework.shiro.realm.JwtAuthorizingRealm;
import com.zhangyongsic.framework.shiro.realm.UnencryptedRealm;
import com.zhangyongsic.framework.shiro.realm.UserNamePwdRealm;
import com.zhangyongsic.framework.shiro.token.JwtAuthenticationToken;
import com.zhangyongsic.framework.shiro.token.UnencryptedToken;
import com.zhangyongsic.framework.shiro.token.UserPrincipal;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author fanchao
 */
//@Component(value = "multiModularRealmAuthenticator")
public class MultiModularRealmAuthenticator extends ModularRealmAuthenticator {


    /**
     * 重写认证方法 针对不同的realm调用不同的认证处理
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        Map<String, Realm> realmMap = new HashMap<String, Realm>();
        // 获取所有的realm
        Collection<Realm> realms = getRealms();
        for (Realm realm : realms) {
            realmMap.put(realm.getName(), realm);
        }
        Realm activeRealm = null;
        if (authenticationToken instanceof JwtAuthenticationToken) {
            // JwtAuthenticationToken
            activeRealm = realmMap.get(JwtAuthorizingRealm.class.getSimpleName());
        } else if (authenticationToken instanceof UsernamePasswordToken) {
            // UsernamePasswordToken
            activeRealm = realmMap.get(UserNamePwdRealm.class.getSimpleName());
        }else if (authenticationToken instanceof UnencryptedToken){
            activeRealm = realmMap.get(UnencryptedRealm.class.getSimpleName());
        }
        if (activeRealm != null) {
            AuthenticationInfo authenticationInfo = doSingleRealmAuthentication(activeRealm, authenticationToken);
            if (!(authenticationToken instanceof JwtAuthenticationToken)) {
                UserPrincipal userPrincipal = (UserPrincipal) authenticationInfo.getPrincipals().getPrimaryPrincipal();
                principalAddSecret(userPrincipal);
            }
            return authenticationInfo;
        }
        return null;
    }


    /**
     * 添加 secret
     * JwtToken 是 包含accessToken和refreshToken
     * JwtPayload 是 accessToken 的负载
     * JwtAuthenticationToken 是 shiro 的 token
     * smdhPrincipal 是 principal 和 jwt 的结合
     *
     * @param userPrincipal
     * @return
     */
    private void principalAddSecret(UserPrincipal userPrincipal) {
        // 生成一个 secret，该 secret 用来加密 jwt
        String secret = ShiroEncrptyTool.getSalt(userPrincipal.getUserId(), 8);
        // userPrincipal
        userPrincipal.setJwtPrivateKey(secret);
    }

}
