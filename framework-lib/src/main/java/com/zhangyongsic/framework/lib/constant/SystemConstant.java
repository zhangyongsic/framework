package com.zhangyongsic.framework.lib.constant;

/**
 * @author fanchao
 */
public interface SystemConstant {

    interface Token {
        String AUTHORIZATION = "Authorization";
        String BEARER = "Bearer";
        String TOKEN = "token";
        String TOKEN_EXPIRE = "tokenExpire";
        String REFRESH_TOKEN_EXPIRE = "refreshTokenExpire";
        String REMEMBER_ME_TOKEN_EXPIRE = "rememberMeTokenExpire";
    }

    interface Entrypt {
        String ALGORITHM_NAME = "algorithmName";
        String HASHITERATIONS = "hashIterations";
        String SALT_SIZE = "saltSize";
        String RSA_PWD = "rsaPwd";
        String RSA_PUBLIC_KEY = "rsaPublicKey";
        String RSA_PRIVATE_KEY = "rsaPrivateKey";

    }

    interface RealmAccessible {
        String SMDH_REALM_ACCESSIBLE = "smdhRealmAccessible";
    }


    interface Header{
        //终端
        String TERMINAL = "Terminal";

        String AUTHORIZATION = "Authorization";

        String MALL = "mall";

        String APPID = "appId";
    }

    interface MallTerminal{
        String APP = "APP";
        String H5 = "H5";
        /**
         * 微信小程序
         */
        String WX_MP = "WX_MP";
        String PC = "PC";
        /**
         * B端PC商城
         */
        String B_PC = "B_PC";
    }

}
