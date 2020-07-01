package com.zhangyongsic.framework.shiro.encrpty;


import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author fanchao
 */
public class ShiroEncrptyTool {

    /**
     * 根据用户名以及随机数生成加密因子
     *
     * @param userName
     * @return
     */
    public static String getSalt(String userName, int saltSize) {
        String salt = userName + new SecureRandomNumberGenerator().nextBytes(saltSize).toHex();
        return salt;
    }

    /**
     * 根据加密因子以及明文加密密码
     *
     * @param salt     加密因子
     * @param plainPwd 用户传入的明文
     * @return
     */
    public static String encryptPwd(String algorithmName, String salt, String plainPwd, int hashIterations) {
        SimpleHash simpleHash = new SimpleHash((algorithmName), plainPwd, salt, hashIterations);
        return simpleHash.toHex();
    }
}
