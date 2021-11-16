package com.zhangyongsic.framework.wx.mp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
public class WxMaJscode2Session extends WxError implements Serializable {
  private static final long serialVersionUID = -1060216618475607933L;

  @JSONField(name = "session_key")
  private String sessionKey;

  private String openid;

  private String unionid;

}
