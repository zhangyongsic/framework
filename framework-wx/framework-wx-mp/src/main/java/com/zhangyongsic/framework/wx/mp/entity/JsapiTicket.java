package com.zhangyongsic.framework.wx.mp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/08/19
 */
@Data
public class JsapiTicket extends WxError implements Serializable {
    private String ticket;
    @JSONField(name = "expires_in")
    private Long expiresIn = -1L;
}
