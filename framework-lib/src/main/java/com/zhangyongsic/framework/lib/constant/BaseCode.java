package com.zhangyongsic.framework.lib.constant;

import com.zhangyongsic.framework.lib.exception.BusinessCode;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public enum BaseCode implements BusinessCode {
    OK("0","成功"),
    UNKNOWN("UNKNOWN","未知异常"),
    BUSY("BUSY","业务忙"),
    NO_AUTH("NO_AUTH","未登录"),
    ACCOUNT_AUDIT("ACCOUNT_AUDIT","账号正在审核！"),
    ACCOUNT_FORBIDDEN("ACCOUNT_FORBIDDEN","账号已禁用！"),
    USERNAME_OR_PASSWORD_ERROR("USERNAME_OR_PASSWORD_ERROR","用户名密码错误"),
    ;
    private String code;
    private String message;
    BaseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
