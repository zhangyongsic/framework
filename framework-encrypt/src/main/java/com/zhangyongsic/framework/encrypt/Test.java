package com.zhangyongsic.framework.encrypt;

import com.zhangyongsic.framework.encrypt.jwt.JwtFactory;
import com.zhangyongsic.framework.encrypt.jwt.JwtPayload;
import com.zhangyongsic.framework.encrypt.jwt.JwtToken;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public class Test {
    public static void main(String[] args) {
        JwtToken token = JwtFactory.createAccessToken("1","2","zy22",20,30);
        System.out.println(token);
        JwtPayload jwtPayload = JwtFactory.getSubFromTokenWithoutSign(token.getAccessToken());
        System.out.println(jwtPayload);
    }


}
