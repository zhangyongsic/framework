package com.zhangyongsic.framework.shiro.entrance;

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
}
