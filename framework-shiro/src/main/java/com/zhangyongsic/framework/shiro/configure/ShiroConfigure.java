package com.zhangyongsic.framework.shiro.configure;


import com.zhangyongsic.framework.lib.constant.SystemConstant;
import com.zhangyongsic.framework.shiro.authenticator.MultiModularRealmAuthenticator;
import com.zhangyongsic.framework.shiro.factory.StatelessSubjectFactory;
import com.zhangyongsic.framework.shiro.filter.JwtAccessControlFilter;
import com.zhangyongsic.framework.shiro.helper.ShiroPropertiesHelper;
import com.zhangyongsic.framework.shiro.matcher.RetryLimitHashCredentialMatcher;
import com.zhangyongsic.framework.shiro.realm.JwtAuthorizingRealm;
import com.zhangyongsic.framework.shiro.realm.UnencryptedRealm;
import com.zhangyongsic.framework.shiro.realm.UserNamePwdRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author zhang yong
 */
@Slf4j
@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
public class ShiroConfigure {

    /**
     * step-1 usernamePassword realm指定加密
     * HashedCredentialsMatcher，这个类是为了对密码进行编码的，
     * 防止密码在数据库里明码保存，当然在登陆认证的时候，
     * 这个类也负责对form里输入的密码进行编码。
     */
    @Bean
    public RetryLimitHashCredentialMatcher retryLimitHashCredentialMatcher() {
        RetryLimitHashCredentialMatcher credentialsMatcher = new RetryLimitHashCredentialMatcher();
        credentialsMatcher.setHashAlgorithmName("SHA-1");
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * step-2-1
     * 定义usernamePassword realm
     *
     * @return
     */
    @Bean(name = "userNamePwdRealm")
    public UserNamePwdRealm userNamePwdRealm() {
        UserNamePwdRealm usernamePasswordAuthorizingRealm = new UserNamePwdRealm();
        usernamePasswordAuthorizingRealm.setCredentialsMatcher(retryLimitHashCredentialMatcher());
        return usernamePasswordAuthorizingRealm;
    }

    @Bean(name = "unencryptedRealm")
    public UnencryptedRealm unencryptedRealm() {
        return new UnencryptedRealm();
    }

    /**
     * step-2-2
     * 定义jwt realm
     *
     * @return
     */
    @Bean(name = "jwtAuthorizingRealm")
    public JwtAuthorizingRealm jwtAuthorizingRealm() {
        return new JwtAuthorizingRealm();
    }

    @Bean(name = "multiModularRealmAuthenticator")
    public MultiModularRealmAuthenticator multiModularRealmAuthenticator() {
        MultiModularRealmAuthenticator modularRealmAuthenticator = new MultiModularRealmAuthenticator();
        /**
         * FirstSuccessfulStrategy:只要有一个Realm验证成功即可，只返回第一个Realm身份验证成功的认证信息，其他的忽略
         * AtLeastOneSuccessfulStrategy:只要有一个Realm验证成功即可，和FirstSuccessfulStrategy不同，
         *                              返回所有Realm身份验证成功的认证信息
         * AllSuccessfulStrategy:所有Realm验证成功才算成功，且返回所有Realm身份验证成功的认证信息，如果有一个失败就失败了
         */
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }


    /**
     * step-3
     * 定义shiro核心securityManager
     *
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(
            @Qualifier(value = "userNamePwdRealm") UserNamePwdRealm userNamePwdRealm,
            @Qualifier(value = "unencryptedRealm") UnencryptedRealm unencryptedRealm,
            @Qualifier(value = "jwtAuthorizingRealm") JwtAuthorizingRealm jwtAuthorizingRealm,
            @Qualifier(value = "multiModularRealmAuthenticator") MultiModularRealmAuthenticator multiModularRealmAuthenticator) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //1.定义认证器
        securityManager.setAuthenticator(multiModularRealmAuthenticator);

        //2.设置realms
        Set<Realm> realms = new HashSet<Realm>();
        realms.add(userNamePwdRealm);
        realms.add(unencryptedRealm);
        realms.add(jwtAuthorizingRealm);
        securityManager.setRealms(realms);

        //3.设置无状态相关subjectFactory,sessionManager等
        // DefaultWebSubjectFactory 禁止创建 session
        StatelessSubjectFactory statelessSubjectFactory = new StatelessSubjectFactory();
        securityManager.setSubjectFactory(statelessSubjectFactory);

        //3.DefaultSessionManager 禁用回话调度器
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        securityManager.setSessionManager(sessionManager);

        //3.禁用使用Sessions 作为存储策略的实现
        DefaultSubjectDAO defaultSubjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = (DefaultSessionStorageEvaluator) defaultSubjectDAO.getSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);


        // DefaultWebSessionManager
        // 会话超时时间，单位：毫秒
        //sessionManager.setGlobalSessionTimeout(sessionTimeout);
        // 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
        //sessionManager.setSessionValidationInterval(sessionTimeoutClean);
        //sessionManager.setSessionValidationSchedulerEnabled(true);
        // 指定sessionid
        //sessionManager.setSessionIdCookie(sessionIdCookie());
        //sessionManager.setSessionIdCookieEnabled(true);
        // 去掉shiro登录时url里的JSESSIONID
        //sessionManager.setSessionIdUrlRewritingEnabled(false);
        return securityManager;
    }

    /**
     * step-4
     * 定义shiro拦截器
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier(value = "securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加jwt拦截器
        JwtAccessControlFilter jwtAccessControlFilter = new JwtAccessControlFilter();
        shiroFilterFactoryBean.getFilters().put("jwt", jwtAccessControlFilter);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap());
        log.info("shiro拦截器工厂执行成功...");
        return shiroFilterFactoryBean;
    }


    /**
     * 解析配置文件获取拦截配置信息
     *
     * @return
     */
    private Map<String, String> filterChainMap() {
        String chainMap = ShiroPropertiesHelper.getInstance().getProperty("shiroFilterChainMap");
        log.info("读取shiro拦截链配置数据:" + chainMap);
        String[] chains = chainMap.split(";");
        //解析拦截器配置信息
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        for (int i = 0; i < chains.length; i++) {
            String chain = chains[i];
            String[] chainKeyValue = chain.split("=");
            // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
            // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
            filterChainDefinitionMap.put(chainKeyValue[0], chainKeyValue[1]);
        }
        return filterChainDefinitionMap;
    }

    /**
     * step-5
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier(value = "securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 自动创建代理
     *
     * @return
     */
    @Bean
    // @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
