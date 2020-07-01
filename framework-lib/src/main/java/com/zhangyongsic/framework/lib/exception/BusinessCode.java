package com.zhangyongsic.framework.lib.exception;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public interface BusinessCode {

    /**
     * 错误码
     * @return
     */
    String getCode();

    /**
     * 错误信息
     * @return
     */
    String getMessage();
}
