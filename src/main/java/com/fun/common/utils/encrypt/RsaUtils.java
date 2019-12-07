package com.fun.common.utils.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * RSA 工具类
 *
 * @author DJun
 * @date 2019/11/14
 */
public class RsaUtils {
    private static final String RSA_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    private final static String MD5_WITH_RSA = "MD5withRSA";

    /**
     * RSA keys must be at least 512 bits long
     * e、g：512、1024....
     */
    private static final int KEY_SIZE = 512;

    /**
     * 生成密匙对
     *
     * @return Map<String, String>
     */
    public static Map<String, String> createKeys() {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }
        kpg.initialize(KEY_SIZE);
        KeyPair keyPair = kpg.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<>(3);
        keyPairMap.put(PUBLIC_KEY, publicKeyStr);
        keyPairMap.put(PRIVATE_KEY, privateKeyStr);
        RSAPublicKey rsp = (RSAPublicKey) keyPair.getPublic();
        BigInteger bit = rsp.getModulus();
        byte[] b = bit.toByteArray();
        String retValue = new String(Base64.encodeBase64(b));
        keyPairMap.put("model", retValue);
        return keyPairMap;
    }

    /**
     * 从公钥密文中获取公钥
     *
     * @param publicKey 公钥密文
     * @return RSAPublicKey对象
     * @throws NoSuchAlgorithmException ex
     * @throws InvalidKeySpecException  ex
     */
    public static RSAPublicKey getPublicKey(String publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * 从私钥密文中获取私钥
     *
     * @param privateKey 私钥密文
     * @return RSAPrivateKey对象
     * @throws NoSuchAlgorithmException ex
     * @throws InvalidKeySpecException  ex
     */
    public static RSAPrivateKey getPrivateKey(String privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密
     *
     * @param data      加密的数据
     * @param publicKey 公钥
     * @return String
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE,
                    data.getBytes(StandardCharsets.UTF_8), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data       加密的数据
     * @param privateKey 私钥
     * @return String
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    privateKey.getModulus().bitLength()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 加密解密方法
     *
     * @param cipher  加密算法
     * @param opmode  模式：加密：Cipher.ENCRYPT_MODE ，解密：Cipher.DECRYPT_MODE
     * @param datas   加密的数据
     * @param keySize 大小
     * @return byte[]
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] res = out.toByteArray();
        IOUtils.closeQuietly(out);
        return res;
    }

    /**
     * 执行签名
     *
     * @param privateKey 私钥
     * @param signStr    参数内容
     * @return 签名后的内容，base64后的字符串
     */
    public static String rsaSignature(String privateKey, String signStr)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // base64解码私钥
        byte[] decodePrivateKey = Base64.decodeBase64(privateKey.replace("\r\n", ""));

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodePrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey pKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance(MD5_WITH_RSA);
        signature.initSign(pKey);
        signature.update(signStr.getBytes());
        byte[] result = signature.sign();
        return java.util.Base64.getEncoder().encodeToString(result);
    }

    /**
     * 验证签名
     *
     * @param publicKey 公钥
     * @param sign      私钥签名字符串
     * @param src       签名
     * @return 验证结果
     */
    public static boolean verifyRsaSignature(String publicKey, String sign, String src)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // base64解码公钥
        byte[] decodePublicKey = Base64.decodeBase64(publicKey.replace("\r\n", ""));

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodePublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance(MD5_WITH_RSA);
        signature.initVerify(pubKey);
        signature.update(src.getBytes());
        // base64解码签名为字节数组
        byte[] decodeSign = java.util.Base64.getDecoder().decode(sign);

        // 验证签名
        return signature.verify(decodeSign);
    }

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Map<String, String> keyPairMap = createKeys();
        String publicKey = keyPairMap.get(PUBLIC_KEY);
        String privateKey = keyPairMap.get(PRIVATE_KEY);

        System.out.println("-----公钥----");
        System.out.println(publicKey);
        System.out.println("-----私钥----");
        System.out.println(privateKey);

        String data = "[\"data\":{\"userId\":\"1\"}]";

        System.out.println("-----公钥加密结果----");
        String encode = publicEncrypt(data, getPublicKey(publicKey));
        System.out.println(encode);

        String decodeResult = privateDecrypt(encode, getPrivateKey(privateKey));
        System.out.println("-----解密结果----");
        System.out.println(decodeResult);

        System.out.println("-----签名-------");
        String sign = rsaSignature(privateKey, "fun-boot");
        System.out.println(sign);

        System.out.println("-----验签结果-------");
        System.out.println(verifyRsaSignature(publicKey, sign, "fun-boot"));

        System.out.println("-----前后交互加密解密-------");
        String privateKey2 = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAgxB0LkFy5BorQY7NaFPFo2SSxcybq9a2kFD-cWVTXA8gZ5TSz1fiZa-pAcW_IAJcgloIJZnFO-xed81bXaNObwIDAQABAkAjFl-UFo90g5D6_wj8mhi6Em28qHcwfM3pOtWzc-XqKfqgnh9bZ18tBiwiIkJRw3P-i2FrM4KiKe0ZfAzbDpSpAiEA3jGRgUrSyPhPPLc98ztVgK77fBAEjAjEdtEl-IlE3jMCIQCXAWa6RhL9JN5e5F9uVcnTUbF0P6ZiioEcWQ5zyGXa1QIgDGwZBnF4d2Pqiip0fDTFAvzFcpoypuGWmk33IX4LK6ECIQCMJ8TNR5UAWGP890KMChwVg1GNcDZiZ-OGCDKdzHadMQIgRJCG5NpeX4myojOeFgCFGEp4ace2NW8vnLBkElz8ego";
        String publicKey2 = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIMQdC5BcuQaK0GOzWhTxaNkksXMm6vWtpBQ_nFlU1wPIGeU0s9X4mWvqQHFvyACXIJaCCWZxTvsXnfNW12jTm8CAwEAAQ";
        String encode2 = publicEncrypt(data, getPublicKey(publicKey2));
        String decodeResult2 = privateDecrypt(encode2, getPrivateKey(privateKey2));
        System.out.println(decodeResult2);
    }

}
