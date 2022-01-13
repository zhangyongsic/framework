package com.zhangyongsic.framework.lib.exception;

import lombok.Data;

@Data
public class SystemException extends Exception {
    private static final long serialVersionUID = 3583566093089790851L;
    private String message;

    public static final SystemException DECRYPT_EXCEPTION = new SystemException("解密失败");
    public static final SystemException ENCRYPT_EXCEPTION = new SystemException("加密失败");

    public SystemException(String message) {
        super(message);
        this.message = message;
    }
}
