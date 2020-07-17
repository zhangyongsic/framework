package com.zhangyongsic.framework.shiro.token;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/07/14
 */
@Data
public class LeaderPrincipal implements Serializable {
    private String leaderId;
    private String leaderName;
}
