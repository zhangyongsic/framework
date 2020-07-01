package com.zhangyongsic.framework.shiro.entrance;

import com.zhangyongsic.framework.encrypt.jwt.JwtFactory;
import com.zhangyongsic.framework.encrypt.jwt.JwtPayload;
import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
@Slf4j
public class JwtTokenHelper {

    public static JwtPayload getJwtPayload(String token) {
        try {
            return JwtFactory.getSubFromTokenWithoutSign(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BaseCode.NO_AUTH);
        }
    }

    public static void tokenAuthc(String jwtPrivateKey, String token) {
        try {
            JwtFactory.parserJwtTokenSubject(jwtPrivateKey, token);
        } catch (SignatureException e) {
            throw new BusinessException(BaseCode.NO_AUTH);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(BaseCode.NO_AUTH);
        } catch (Exception e) {
            throw new BusinessException(BaseCode.NO_AUTH);
        }
    }


    public static void refreshTokenAuthc(String jwtPrivateKey, String refreshToken) {
        try {
            JwtFactory.parserJwtTokenSubject(jwtPrivateKey, refreshToken);
        } catch (SignatureException e) {
            throw new BusinessException(BaseCode.NO_AUTH);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(BaseCode.NO_AUTH);
        } catch (Exception e) {
            throw new BusinessException(BaseCode.NO_AUTH);
        }
    }
}
