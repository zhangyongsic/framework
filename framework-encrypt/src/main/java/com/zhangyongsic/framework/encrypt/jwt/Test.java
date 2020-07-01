package com.zhangyongsic.framework.encrypt.jwt;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public class Test {
    public static void main(String[] args) {
        JwtToken jwtToken = JwtFactory.createAccessToken("122","222","dfasdfasdfsdf",20,200);

        JwtFactory.getSubFromTokenWithoutSign(jwtToken.getAccessToken());
    }
}
