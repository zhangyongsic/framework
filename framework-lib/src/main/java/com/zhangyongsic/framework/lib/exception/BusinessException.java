package com.zhangyongsic.framework.lib.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhang yong
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 3583566093089790852L;
    private String code;
    private String message;

    public static final BusinessException AUTH_ERROR = new BusinessException("401","未授权");
    public static final BusinessException EXPIRE_ERROR = new BusinessException("401","登录过期");


    public BusinessException(BusinessCode businessCode) {
        this.code = businessCode.getCode();
        this.message = businessCode.getMessage();
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
