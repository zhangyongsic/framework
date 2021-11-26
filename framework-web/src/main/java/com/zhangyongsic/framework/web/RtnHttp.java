package com.zhangyongsic.framework.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.exception.BusinessCode;
import com.zhangyongsic.framework.mybatisplus.util.PageUtil;
import lombok.Data;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
@Data
public class RtnHttp<T>{
    private String code;
    private T data;
    private String message;

    public RtnHttp(){}

    public RtnHttp(BusinessCode businessCode){
        this.code = businessCode.getCode();
        this.message = businessCode.getMessage();
    }

    public RtnHttp(String code, String message){
        this.code = code;
        this.message = message;
    }
    public static RtnHttp ok(){
        return new RtnHttp(BaseCode.OK);
    }

    public static <T> RtnHttp ok(T data){
        RtnHttp rtnHttp =  new RtnHttp(BaseCode.OK);
        rtnHttp.setData(data);
        return rtnHttp;
    }

    public static RtnHttp ok(IPage iPage){
        RtnHttp rtnHttp =  new RtnHttp(BaseCode.OK);
        rtnHttp.setData(PageUtil.out(iPage));
        return rtnHttp;
    }

    public static RtnHttp error(BusinessCode businessCode){
        return new RtnHttp(businessCode);
    }

    public static RtnHttp error(String code, String message){
        return new RtnHttp(code, message);
    }
}
