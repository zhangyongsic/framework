package com.zhangyongsic.framework.shiro.matcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 散列加密限制用户输入密码错误次数
 * @author zhang yong
 */
public class RetryLimitHashCredentialMatcher extends HashedCredentialsMatcher {

	private static Logger logger = LoggerFactory.getLogger(RetryLimitHashCredentialMatcher.class);

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		logger.info("-----------------进入密码错误次数限制匹配器----------------");
		return super.doCredentialsMatch(token, info);
	}
}
