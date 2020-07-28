package com.zhangyongsic.framework.shiro.entrance;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.zhangyongsic.framework.encrypt.jwt.JwtPayload;
import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.constant.SystemConstant;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import com.zhangyongsic.framework.shiro.principal.IPrincipal;
import com.zhangyongsic.framework.shiro.token.CustomerPrincipal;
import com.zhangyongsic.framework.shiro.token.UserPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/3
 */
@Component
public class PrincipalSupport implements IPrincipal {

    @Autowired
    @Qualifier("jacksonRedisTemplate")
    private RedisTemplate redisTemplate;

    public UserPrincipal getPrincipal(){
        UserPrincipal userPrincipal = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            userPrincipal =  (UserPrincipal) subject.getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userPrincipal == null) {
            throw new BusinessException(BaseCode.NO_AUTH);
        }
        return userPrincipal;
    }

    public UserPrincipal getPrincipal(String token){
        JwtPayload payload = JwtTokenHelper.getJwtPayload(token);
        String userId = payload.getSub();
        String cacheKey = payload.getIss();
        UserPrincipal userPrincipal = getPrincipal(cacheKey,userId);
        if (userPrincipal == null){
            throw new BusinessException(BaseCode.NO_AUTH);
        }
        // 认证refreshToken
        JwtTokenHelper.tokenAuth(userPrincipal.getJwtPrivateKey(), token);
        return userPrincipal;
    }

    public UserPrincipal getPrincipal(String userKey, String userId){
        Object object = redisTemplate.opsForHash().get(userKey,userId);
        if (object == null){
            return null;
        }
        return (UserPrincipal) object;
    }

    public void putPrincipal(UserPrincipal userPrincipal){
        redisTemplate.opsForHash().put(userPrincipal.getCacheKey(),userPrincipal.getUserId(),userPrincipal);
    }


    public void checkAudit(){
        if (getCustomerPrincipal()!=null){
            if (!SystemConstant.SUCCESS.equals(getCustomerPrincipal().getCustomerAudit())){
                throw new BusinessException(BaseCode.ACCOUNT_AUDIT);
            }
        }
    }

    @Override
    public String getUserId() {
        return getPrincipal().getUserId();
    }

    @Override
    public String getUserType() {
        return getPrincipal().getUserType();
    }

    @Override
    public String getUserName() {
        return getPrincipal().getUserName();
    }

    @Override
    public String getPassword() {
        return getPrincipal().getPassword();
    }

    @Override
    public Set<String> getPermissions() {
        return getPrincipal().getPermissions();
    }

    @Override
    public Set<String> getRoles() {
        return getPrincipal().getRoles();
    }

    @Override
    public CustomerPrincipal getCustomerPrincipal() {
        return getPrincipal().getCustomerPrincipal();
    }

    @Override
    public String getCustomerId() {
        if (getCustomerPrincipal()!=null){
            return getCustomerPrincipal().getCustomerId();
        }
        return null;
    }
}
