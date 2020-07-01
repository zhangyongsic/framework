package com.zhangyongsic.framework.shiro.realm;


import com.zhangyongsic.framework.encrypt.jwt.JwtPayload;
import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import com.zhangyongsic.framework.shiro.entrance.JwtTokenHelper;
import com.zhangyongsic.framework.shiro.entrance.PrincipalSupport;
import com.zhangyongsic.framework.shiro.token.JwtAuthenticationToken;
import com.zhangyongsic.framework.shiro.token.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author fanchao
 */
@Slf4j
public class JwtAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private PrincipalSupport principalSupport;

    @Override
    public String getName() {
        return JwtAuthorizingRealm.class.getSimpleName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtAuthenticationToken;
    }

    /**
     * 认证的时候调用根据token获取用户信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authenticationToken;
        String token = jwtAuthenticationToken.getToken();
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(BaseCode.NO_AUTH);
        }
        // userId
        JwtPayload payload = JwtTokenHelper.getJwtPayload(token);
        String userId = payload.getSub();
        String cacheKey = payload.getIss();
        // 根据userId获取principal
        UserPrincipal smdhPrincipal = principalSupport.getPrincipal(cacheKey,userId);
        if (smdhPrincipal == null) {
            throw new BusinessException(BaseCode.NO_AUTH);
        }

        return new SimpleAuthenticationInfo(smdhPrincipal, token, smdhPrincipal.getUserName());
    }

    /**
     * 根据认证信息获取权限数据
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //log.debug("Jwt Realm 权限校验检查........");
        return new SimpleAuthorizationInfo();
    }


}
