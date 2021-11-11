package com.zhangyongsic.framework.lib.sha1;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/06/30
 */
public class Sha1Encrypt {

    public static String encryptPassword(String plainPassword) {
        return encryptPassword(plainPassword, 8, 1024);
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String encryptPassword(String plainPassword, int SALT_SIZE, int HASH_INTERATIONS) {
        // 随机生成盐
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        // hashPassword = 明文 + 盐 》编码
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        // 盐 + hashPassword
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }


    public static boolean validatePassword(String plainPassword, String password) {
        return validatePassword(plainPassword, password, 1024);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password, int HASH_INTERATIONS) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }


    /**
     * 获取密码的盐
     *
     * @param password
     * @return
     */
    public static byte[] getPasswordSalt(String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        return salt;
    }

}
