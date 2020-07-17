package com.zhangyongsic.framework.shiro.entrance;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.zhangyongsic.framework.encrypt.jwt.JwtPayload;
import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import com.zhangyongsic.framework.shiro.token.UserPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/3
 */
@Component
public class PrincipalSupport {

    @Autowired
    @Qualifier("jacksonRedisTemplate")
    private RedisTemplate redisTemplate;

    public UserPrincipal getPrincipal(){
        try {
            Subject subject = SecurityUtils.getSubject();
            return (UserPrincipal) subject.getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public UserPrincipal getPrincipalByToken(String token){
        JwtPayload payload = JwtTokenHelper.getJwtPayload(token);
        String userId = payload.getSub();
        String cacheKey = payload.getIss();
        UserPrincipal userPrincipal = getPrincipal(cacheKey,userId);
        if (userPrincipal == null) {
            throw new BusinessException(BaseCode.NO_AUTH);
        }
        // 认证refreshToken
        JwtTokenHelper.tokenAuth(userPrincipal.getJwtPrivateKey(), token);
        return userPrincipal;
    }

    public UserPrincipal getPrincipal(String userKey, String userId){
        Object object = redisTemplate.opsForHash().get(userKey,userId);
        if (object != null){
            return (UserPrincipal) object;
        }
        return null;
    }

    public void putPrincipal(UserPrincipal userPrincipal){
        redisTemplate.opsForHash().put(userPrincipal.getCacheKey(),userPrincipal.getUserId(),userPrincipal);
    }

    public String getUserId(){
        if (getPrincipal()!=null){
            return getPrincipal().getUserId();
        }return null;
    }

    public String getLeaderId(){
        if (getPrincipal() !=null && getPrincipal().getLeaderPrincipal()!= null){
            return getPrincipal().getLeaderPrincipal().getLeaderId();
        }
        return null;
    }
}
