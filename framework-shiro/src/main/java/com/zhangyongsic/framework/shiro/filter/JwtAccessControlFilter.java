package com.zhangyongsic.framework.shiro.filter;


import com.alibaba.fastjson.JSONObject;
import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.constant.SystemConstant;
import com.zhangyongsic.framework.shiro.token.JwtAuthenticationToken;
import com.zhangyongsic.framework.web.RtnHttp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author fanchao
 */
@Slf4j
public class JwtAccessControlFilter extends AccessControlFilter {


    /**
     * 先执行：isAccessAllowed 再执行onAccessDenied
     * <p>
     * isAccessAllowed：表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分， 如果允许访问返回true，否则false；
     * <p>
     * 如果返回true的话，就直接返回交给下一个filter进行处理。 如果返回false的话，回往下执行onAccessDenied
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        return false;
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        try {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String token = servletRequest.getHeader(SystemConstant.Token.AUTHORIZATION);
            //header里面为空则从request里面获取
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter(SystemConstant.Token.AUTHORIZATION);
            }
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
            // 调用realm认证
            getSubject(request, response).login(jwtAuthenticationToken);
            return true;
        } catch (AuthenticationException e) {
            log.info(e.getMessage());
            onTokenAuthFail(response, e);
        }
        return false;
    }

    /**
     * token认证失败
     *
     * @param response
     * @param e
     */
    private void onTokenAuthFail(ServletResponse response, Exception e) {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        servletResponse.setStatus(HttpServletResponse.SC_OK);
        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("UTF-8");
        try {
            servletResponse.getWriter().write(JSONObject.toJSONString(RtnHttp.error(BaseCode.NO_AUTH)));
        } catch (IOException e1) {
            log.error(e1.getMessage(), e1);
        }
    }


}
