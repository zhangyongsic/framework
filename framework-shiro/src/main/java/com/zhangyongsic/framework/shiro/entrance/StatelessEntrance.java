package com.zhangyongsic.framework.shiro.entrance;


import com.zhangyongsic.framework.lib.constant.BaseCode;
import com.zhangyongsic.framework.lib.exception.BusinessException;
import com.zhangyongsic.framework.shiro.token.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
@Slf4j
@Service("statelessEntrance")
public class StatelessEntrance {

    @Autowired
    private TokenBuilder tokenBuilder;

    /**
     * 登陆
     *
     * @param authenticationToken
     * @return
     */
    public TokenVO login(AuthenticationToken authenticationToken) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(authenticationToken);
        } catch (AuthenticationException e){
            e.fillInStackTrace();
            throw new BusinessException(BaseCode.USERNAME_OR_PASSWORD_ERROR);
        }
        UserPrincipal userPrincipal = (UserPrincipal) subject.getPrincipal();
        return tokenBuilder.createJwtToken(userPrincipal);
    }

    public void logout(){

    }


    /**
     * 换取 refreshToken
     *
     * @param refreshToken
     * @return
     */
    public TokenVO refreshToken(String refreshToken) {
        try {
            return tokenBuilder.refreshToken(refreshToken);
        }  catch (AuthenticationException e) {
            if (e.getCause() instanceof BusinessException) {
                throw (BusinessException) e.getCause();
            } else {
                e.printStackTrace();
                throw new BusinessException(BaseCode.USERNAME_OR_PASSWORD_ERROR);
            }
        }
    }



}
