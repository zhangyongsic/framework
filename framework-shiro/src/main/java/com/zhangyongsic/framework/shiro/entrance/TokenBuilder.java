package com.zhangyongsic.framework.shiro.entrance;

import com.zhangyongsic.framework.encrypt.jwt.JwtFactory;
import com.zhangyongsic.framework.encrypt.jwt.JwtPayload;
import com.zhangyongsic.framework.encrypt.jwt.JwtToken;
import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import com.zhangyongsic.framework.shiro.token.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
@Component
public class TokenBuilder {

    @Autowired
    private PrincipalSupport principalSupport;
    /**
     * 创建token
     *
     * @param userPrincipal
     * @return
     */
    public TokenVO createJwtToken(UserPrincipal userPrincipal) {
        // 生成 jwtToken
        JwtToken jwtToken = JwtFactory.createAccessToken(userPrincipal.getUserId(), userPrincipal.getCacheKey(),
                userPrincipal.getJwtPrivateKey(), 12, 12);
        // 保存 smdhPrincipal 到缓存
        principalSupport.putPrincipal(userPrincipal);
        return buildTokenVO(jwtToken);
    }

    /**
     * 换取token
     *
     * @param refreshToken
     * @return
     */
    public TokenVO refreshToken(String refreshToken) {
        JwtPayload payload = JwtTokenHelper.getJwtPayload(refreshToken);
        String userId = payload.getSub();
        String cacheKey = payload.getIss();
        UserPrincipal userPrincipal = principalSupport.getPrincipal(cacheKey,userId);
        if (userPrincipal == null) {
            throw new BusinessException(BaseCode.NO_AUTH);
        }
        // 认证refreshToken
        JwtTokenHelper.refreshTokenAuthc(userPrincipal.getJwtPrivateKey(), refreshToken);
        // 创建新的 jwtToken
        return createJwtToken(userPrincipal);

    }

    public TokenVO buildTokenVO(JwtToken jwtToken) {
        TokenVO token = new TokenVO();
        token.setToken(jwtToken.getAccessToken());
        token.setRefreshToken(jwtToken.getRefreshToken());
        token.setExpireTime(jwtToken.getExpireTime());
        return token;
    }

}
