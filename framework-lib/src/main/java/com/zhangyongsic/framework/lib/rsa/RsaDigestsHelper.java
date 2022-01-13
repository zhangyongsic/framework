package com.zhangyongsic.framework.lib.rsa;

import com.zhangyongsic.framework.lib.exception.BusinessException;
import com.zhangyongsic.framework.lib.exception.SystemException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
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

    private static String base64PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1JViPSBPI7ox+aUO66xfYYuioLdSMlDrBaVZf+utXB3okk3rfqR38AK7Zigd7kiWZkPNY4S0tfcD2DuGyifj1JE+NnYickg8ssOHn1C7Gi3GdvBn93oX5JDwcNCu1bYkrEkm+Ar9EvjSbHMC0w5ehj6JWNPSncKqD+zSO5ptz1QIDAQAB";
    private static String base64PrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALUlWI9IE8jujH5pQ7rrF9hi6Kgt1IyUOsFpVl/661cHeiSTet+pHfwArtmKB3uSJZmQ81jhLS19wPYO4bKJ+PUkT42diJySDyyw4efULsaLcZ28Gf3ehfkkPBw0K7VtiSsSSb4Cv0S+NJscwLTDl6GPolY09KdwqoP7NI7mm3PVAgMBAAECgYA6RcXL7fFjaY3rmnxN1JPqqcLTW07tXD/ceUDuhl8Ps5mQy5qy+YnqR+P3miYXE+ghkxYdaO6qHDKnVRk44Jaj5abt6QOezSzjdrBOEBD8/MVq6Oas1zrXOEfChNBcxu5tFgm2IfENvCrhswt75dRU3zfUDKXkOZzm8k1OzfqdzwJBAPjVy+zoWpNqFBFhXfZTE3WJoGikVmeVKg0r7QhmkW8skz7tXTiE1981n4cBSe+F1wHtn1ou6ITPxMJxf8uLyb8CQQC6XJneUBTQXdKO7hZruLpLlBMhbJxlyvkBZpg8RHmEhMX2aEgxYofcnVgmc29Bt0haIIbWFgd+Fp292xUKQB9rAkBg5ljIQ99pwohYEFOX5dgREGwf88cWBTdf87gVamO+KFyax7JinRC3gllCKJVTKgqFXLSOWPABMCuOEMbUS/ZfAkEAmQGUNwlKvXR6fm0NrW2iTpEJT+TrV0vZOwQvszOJDXsxSUh/FUTPRRtOF2upCaxgU9bmvXiiuCv7YLgxwxO4dwJBAKyWq3bGhNSjhO5aOxycnprrLJ6+89cA0FGZJo/8fir0MUL/+B7XzFX7gMO4HYx0Lped+GwjI/2RBtK2kRmVblk=";

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
//        RsaKeyPair rsaKeyPair = getStringKeyPair();
//        System.out.println("公钥:" + rsaKeyPair.getBase64PublicKey());
//        System.out.println("私钥:" + rsaKeyPair.getBase64PrivateKey());

        String eee = encryptByPublicKey("张雨生是");
        System.out.println("加密信息："+eee);
        System.out.println("加密信息："+decryptByPrivateKey(eee));
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
        throw SystemException.ENCRYPT_EXCEPTION;
    }


    public static String encryptByPublicKey(String base64PublicKey, String source) throws Exception {
        if (StringUtils.isNotBlank(source)){
            return Base64.encodeBase64String(encryptByPublicKey(base64PublicKey,source.getBytes()));
        }
        throw SystemException.ENCRYPT_EXCEPTION;
    }

    public static String encryptByPublicKey(String source) throws Exception {
        return encryptByPublicKey(base64PublicKey,source);
    }
    /**
     * 私钥解密
     *
     * @param base64PrivateKey 私钥
     * @param encryptData      密文数据
     */
    public static byte[] decryptByPrivateKey(String base64PrivateKey, byte[] encryptData) throws Exception{
        if (StringUtils.isNotEmpty(base64PrivateKey) && encryptData.length > 0) {
            RSAPrivateKey privateKey = getRsaPrivateKey(base64PrivateKey);
            return segment(privateKey, encryptData, RSA_MAX_DECRYPT_BLOCK, Cipher.DECRYPT_MODE);
        }
        throw SystemException.DECRYPT_EXCEPTION;
    }

    public static String decryptByPrivateKey(String base64PrivateKey, String base64EncryptData) throws Exception{
        if (StringUtils.isNotBlank(base64EncryptData)){
            byte[] encryptData = Base64.decodeBase64(base64EncryptData.getBytes());
            return new String(decryptByPrivateKey(base64PrivateKey,encryptData));
        }
        throw SystemException.DECRYPT_EXCEPTION;
    }


    public static String decryptByPrivateKey(String base64EncryptData) throws Exception{
       return decryptByPrivateKey(base64PrivateKey,base64EncryptData);
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
