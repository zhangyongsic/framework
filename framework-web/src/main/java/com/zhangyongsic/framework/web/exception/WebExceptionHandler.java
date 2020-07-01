package com.zhangyongsic.framework.web.exception;

import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import com.zhangyongsic.framework.web.RtnHttp;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */

@ResponseBody
@ControllerAdvice
public class WebExceptionHandler {

    /**
     * 业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public RtnHttp handleBusinessException(BusinessException ex) {
        return RtnHttp.error(ex.getCode(),ex.getMessage());
    }

    /**
     * 未知异常
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RtnHttp handleBusinessException(Exception ex) {
        ex.printStackTrace();
        return RtnHttp.error(BaseCode.UNKNOWN);
    }
}
