package com.zhangyongsic.framework.shiro.principal;

import com.zhangyongsic.framework.shiro.token.CustomerPrincipal;

import java.util.Set;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/07/27
 */
public interface IPrincipal {
    /**
     * 获取用户ID
     * @return
     */
    String getUserId();

    /**
     * 客户类型
     * @return
     */
    String getUserType();
    /**
     * 获取用户名
     * @return
     */
    String getUserName();
    /**
     * 获取密码
     * @return
     */
    String getPassword();
    /**
     * 获取权限
     * @return
     */
    Set<String> getPermissions();
    /**
     * 获取角色
     * @return
     */
    Set<String> getRoles();

    /**
     * 获取客户信息
     * @return
     */
    CustomerPrincipal getCustomerPrincipal();

    /**
     * 获取客户ID
     * @return
     */
    String getCustomerId();

}
