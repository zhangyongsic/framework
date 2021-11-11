package com.zhangyongsic.framework.lib.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 *
 * @author zhangyong
 */
public class RsaDigestsHelper {

    /**
     * RSA密钥大小
     */
    private static int RSA_KEY_SIZE = 1024;
    /**
     * RSA最大加密明文大小
     */
    private static int RSA_MAX_ENCRYPT_BOLCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static int RSA_MAX_DECRYPT_BLOCK = 128;

    /**
     * 第三方算法提供程序 加解密能够通用
     */
    private static final Provider provider = new BouncyCastleProvider();

    /**
     * RSA 得到公钥和私钥字符串 base64编码后的
     *
     * @return
     * @throws Exception
     */
    public static KeyPair generateRsaKeyPair() throws Exception {
        SecureRandom random = new SecureRandom();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
        generator.initialize(RSA_KEY_SIZE, random);
        KeyPair keyPair = generator.generateKeyPair();
        return keyPair;
    }

    public static RsaKeyPair getStringKeyPair() throws Exception {
        KeyPair keyPair = generateRsaKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String base64RsaPublicKey = Base64.encodeBase64String(publicKey.getEncoded());
        String base64RsaPrivateKey = Base64.encodeBase64String(privateKey.getEncoded());
        RsaKeyPair rsaKeyPair = new RsaKeyPair();
        rsaKeyPair.setBase64PrivateKey(base64RsaPrivateKey);
        rsaKeyPair.setBase64PublicKey(base64RsaPublicKey);
        return rsaKeyPair;
    }

    public static void main(String[] args) throws Exception {
        RsaKeyPair rsaKeyPair = getStringKeyPair();
        System.out.println("公钥:" + rsaKeyPair.getBase64PublicKey());
        System.out.println("私钥:" + rsaKeyPair.getBase64PrivateKey());
        String publicKey = rsaKeyPair.getBase64PublicKey();
        String privateKey = rsaKeyPair.getBase64PrivateKey();
        byte[] encrypted = encryptByPublicKey(publicKey, "123456".getBytes());
        String decode = new String(decryptByPrivateKey(privateKey, encrypted));
        System.out.println(decode);
    }

    /**
     * 字符串转公钥
     *
     * @param base64PublicKey
     * @return
     * @throws Exception
     */
    public static RSAPublicKey getRsaPublicKey(String base64PublicKey) throws Exception {
        byte[] publicKey = Base64.decodeBase64(base64PublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", provider);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 字符串转私钥
     *
     * @param base64PrivateKey
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey getRsaPrivateKey(String base64PrivateKey) throws Exception {
        byte[] privateKey = Base64.decodeBase64(base64PrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", provider);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * RSA通过公钥加密
     *
     * @param base64PublicKey base64编码后的公钥
     * @param source          待加密的内容
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String base64PublicKey, byte[] source) throws Exception {
        if (StringUtils.isNotEmpty(base64PublicKey) && source.length > 0) {
            RSAPublicKey publicKey = getRsaPublicKey(base64PublicKey);
            return segment(publicKey, source, RSA_MAX_ENCRYPT_BOLCK, Cipher.ENCRYPT_MODE);
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param base64PrivateKey 私钥
     * @param encryptData      密文数据
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String base64PrivateKey, byte[] encryptData) throws Exception {
        if (StringUtils.isNotEmpty(base64PrivateKey) && encryptData.length > 0) {
            RSAPrivateKey privateKey = getRsaPrivateKey(base64PrivateKey);
            return segment(privateKey, encryptData, RSA_MAX_DECRYPT_BLOCK, Cipher.DECRYPT_MODE);
        }
        return null;
    }

    public static String decryptByPrivateKey(String base64PrivateKey, String base64EncryptData) {
        try {
            if (StringUtils.isNotEmpty(base64PrivateKey) && StringUtils.isNotBlank(base64EncryptData)) {
                RSAPrivateKey privateKey = getRsaPrivateKey(base64PrivateKey);
                byte[] encryptData = Base64.decodeBase64(base64EncryptData.getBytes());
                byte[] plain = segment(privateKey, encryptData, RSA_MAX_DECRYPT_BLOCK, Cipher.DECRYPT_MODE);
                return new String(plain);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 分段加密解密
     *
     * @param source 明文或者密文
     * @param key    公钥或者私钥
     * @param max    分段设置
     * @param model  加密还是解密
     * @return
     * @throws Exception
     */
    private static byte[] segment(Key key, byte[] source, int max, int model) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", provider);
        cipher.init(model, key);

        int inputLength = source.length;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int offset = 0;
        byte[] buffer;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > max) {
                buffer = cipher.doFinal(source, offset, max);
            } else {
                buffer = cipher.doFinal(source, offset, inputLength - offset);
            }
            outputStream.write(buffer, 0, buffer.length);
            i++;
            offset = i * max;
        }
        byte[] result = outputStream.toByteArray();
        outputStream.close();
        return result;
    }

}
