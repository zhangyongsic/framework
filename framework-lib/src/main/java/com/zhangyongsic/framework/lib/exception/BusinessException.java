package com.zhangyongsic.framework.lib.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhang yong
 *
 *
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException  {
    private static final long serialVersionUID = 3583566093089790852L;
    private String code;
    private String message;

    public static final BusinessException SUCCESS = new BusinessException("0","成功");
    public static final BusinessException UNKNOWN_FAIL = new BusinessException("-1","未知异常");

    public static final BusinessException AUTH_ERROR = new BusinessException("104001","未授权");
    public static final BusinessException EXPIRE_ERROR = new BusinessException("104001","登录过期");

    public BusinessException(BusinessCode businessCode) {
        super(businessCode.getMessage());
        this.code =businessCode.getCode();
        this.message = businessCode.getMessage();
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
