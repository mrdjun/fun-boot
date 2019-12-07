//package com.fun.common.utils.encrypt;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.lang3.StringUtils;
//import sun.misc.BASE64Decoder;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.spec.SecretKeySpec;
//import java.math.BigInteger;
//import java.nio.charset.StandardCharsets;
//
///**
// * AES加密工具类
// *
// * @author DJun
// * @date 2019/12/4
// */
//public class AesUtils {
//    private static final String KEY = "1575424443184000";
//    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";
//    private static final String AES_STR = "AES";
//    /**
//     * AES加密数据块分组长度必须为 128bit，密钥长度为 128bit、192bit、256bit 中的任意一个
//     */
//    private static final int AES_LENGTH = 128;
//    /**
//     * 将byte[]转为各种进制的字符串
//     *
//     * @param bytes byte[]
//     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
//     * @return 转换后的字符串
//     */
//    public static String binary(byte[] bytes, int radix) {
//        return new BigInteger(1, bytes).toString(radix);
//    }
//
//    /**
//     * base 64 encode
//     *
//     * @param bytes 待编码的byte[]
//     * @return 编码后的base 64 code
//     */
//    private static String base64Encode(byte[] bytes) {
//        return Base64.encodeBase64String(bytes);
//    }
//
//    /**
//     * base 64 decode
//     *
//     * @param base64Code 待解码的base 64 code
//     * @return 解码后的byte[]
//     * @throws Exception
//     */
//    private static byte[] base64Decode(String base64Code) throws Exception {
//        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
//    }
//
//
//    /**
//     * AES加密
//     *
//     * @param content    待加密的内容
//     * @param encryptKey 加密密钥
//     * @return 加密后的byte[]
//     * @throws Exception
//     */
//    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
//        KeyGenerator kg = KeyGenerator.getInstance(AES_STR);
//        kg.init(AES_LENGTH);
//        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
//        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), AES_STR));
//        return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
//    }
//
//
//    /**
//     * AES加密为base 64 code
//     *
//     * @param content    待加密的内容
//     * @param encryptKey 加密密钥
//     * @return 加密后的base 64 code
//     * @throws Exception
//     */
//    public static String aesEncrypt(String content, String encryptKey) throws Exception {
//        return base64Encode(aesEncryptToBytes(content, encryptKey));
//    }
//
//    /**
//     * AES解密
//     *
//     * @param encryptBytes 待解密的byte[]
//     * @param decryptKey   解密密钥
//     * @return 解密后的String
//     * @throws Exception
//     */
//    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
//        KeyGenerator kg = KeyGenerator.getInstance(AES_STR);
//        kg.init(AES_LENGTH);
//        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
//        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), AES_STR));
//        byte[] decryptBytes = cipher.doFinal(encryptBytes);
//        return new String(decryptBytes);
//    }
//
//
//    /**
//     * 将 base 64 code AES解密
//     *
//     * @param encryptStr 待解密的base 64 code
//     * @param key 解密密钥
//     * @return 解密后的string
//     * @throws Exception
//     */
//    public static String aesDecrypt(String encryptStr, String key) throws Exception {
//        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), key);
//    }
//
//    /**
//     * aes解密
//     *
//     * @param encrypt 内容
//     * @return aesDecrypt
//     */
//    public static String toDecrypt(String encrypt) throws Exception {
//        return aesDecrypt(encrypt, KEY);
//    }
//
//    /**
//     * aes加密
//     *
//     * @param data data
//     */
//    public static String toEncrypt(String data) throws Exception {
//        return aesEncrypt(data, KEY);
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        String content = "admin";
//        String encrypt = toEncrypt(content);
//        System.out.println("加密：\n" + encrypt);
//        String decrypt = toDecrypt(encrypt);
//        System.out.println("解密：\n" + decrypt);
//    }
//}
