package com.zhangyongsic.framework.encrypt.jwt;


import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;


/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public class JwtFactory {

    /**
     * * 根据用户ID和私钥生成唯一token,同时返回刷新token
     * iss: jwt签发者
     * sub: jwt所面向的用户
     * aud: 接收jwt的一方
     * exp: jwt的过期时间，这个过期时间必须要大于签发时间
     * nbf: 定义在什么时间之前，该jwt都是不可用的.
     * iat: jwt的签发时间
     * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     *
     * @param userId 用户ID
     * @param secret 加密因子
     * @return
     */
    public static JwtToken createAccessToken(String userId, String tenantId, String secret, int tokenExpire, int refreshTokenExpire) {
        Date date = new Date();
        // token过期时间
        Date tokenExpireAt = DateUtils.addSeconds(date, tokenExpire);
        // token
        String token = createToken(userId, tenantId, tokenExpireAt, secret);
        // 刷新token过期时间为7*24小时
        Date refreshTokenExpireAt = DateUtils.addSeconds(date, refreshTokenExpire);
        // 刷新token
        String refreshToken = createToken(userId, tenantId, refreshTokenExpireAt, secret);

        JwtToken jwtToken = new JwtToken(token, refreshToken, tokenExpireAt);
        return jwtToken;
    }


    /**
     * 创建token
     *
     * @param userId   用户ID
     * @param expireAt 过期时间
     * @param secret   加密因子
     * @return ISSUER 发行人
     * SUBJECT 主题
     * AUDIENCE 观众
     * EXPIRATION 截止 到期
     * ISSUED_AT 签发日期
     */
    private static String createToken(String userId, String tenantId, Date expireAt, String secret) {
        Claims claims = Jwts.claims().setSubject(userId).setIssuer(tenantId);
        String token = Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return token;
    }

    /**
     * 解析token返回subject数据(生成token的时候存放的数据)
     *
     * @param secret
     * @param token
     * @return
     */
    public static String parserJwtTokenSubject(String secret, String token) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String subject = body.getSubject();
        return subject;
    }

    /**
     * 不传递秘钥解析出保存的用户信息(payload)
     *
     * @param token
     * @return
     */
    public static JwtPayload getSubFromTokenWithoutSign(String token) {
        String[] parts = token.split("\\.");
        String body = new String(Base64.decodeBase64(parts[1]));
        JwtPayload jwtPayload = JSONObject.parseObject(body, JwtPayload.class);
        return jwtPayload;
    }
}
