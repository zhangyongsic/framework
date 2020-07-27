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
public class CustomerPrincipal implements Serializable {
    private String customerId;
    private String customerName;
    private String customerAudit;
    private String customerForbidden;
}
