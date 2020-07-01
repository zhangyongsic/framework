package com.zhangyongsic.framework.encrypt.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class JwtPayload implements Serializable{


    private static final long serialVersionUID = 1979792585894373009L;
    /**
     * jwt签发者
     */
    private String iss;
    /**
     * jwt的签发时间
     */
    private String iat;
    /**
     * jwt所面向的用户
     */
    private String sub;
    /**
     * jwt的过期时间，这个过期时间必须要大于签发时间
     */
    private String exp;
}
