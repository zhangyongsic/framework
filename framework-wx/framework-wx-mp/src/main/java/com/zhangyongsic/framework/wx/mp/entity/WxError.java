package com.zhangyongsic.framework.wx.mp.entity;

import com.zhangyongsic.framework.lib.exception.BusinessException;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
@Data
public class WxError implements Serializable {
    private Integer errcode = 0;
    private String errmsg;

    public void check(){
        if (getErrcode() != 0){
            throw new BusinessException(getErrcode().toString(),getErrmsg());
        }
    }
}
