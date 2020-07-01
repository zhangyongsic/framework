package com.zhangyongsic.framework.lib.constant;

import com.zhangyongsic.framework.lib.exception.BusinessCode;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public enum BaseCode implements BusinessCode {
    OK("OK","成功"),
    UNKNOWN("UNKNOWN","未知异常"),
    NO_AUTH("NO_AUTH","未登录"),
    USERNAME_OR_PASSWORD_ERROR("USERNAME_OR_PASSWORD_ERROR","用户名密码错误"),
    ;
    private String code;
    private String message;
    BaseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
