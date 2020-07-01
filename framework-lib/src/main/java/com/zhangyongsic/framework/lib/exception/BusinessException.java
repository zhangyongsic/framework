package com.zhangyongsic.framework.lib.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fanchao
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 3583566093089790852L;
    private String code;
    private String message;

    public BusinessException(BusinessCode businessCode) {
        this.code = businessCode.getCode();
        this.message = businessCode.getMessage();
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
