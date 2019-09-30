package com.fun.common.utils;

import com.fun.project.admin.system.entity.user.AdminUser;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Objects;

/**
 * Md5加密方法
 */
public class Md5Utils {
    private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);

    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes(StandardCharsets.UTF_8));
            return algorithm.digest();
        } catch (Exception e) {
            log.error("MD5 Error...", e);
        }
        return null;
    }

    private static String toHex(byte[] hash) {
        if (hash == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                sb.append("0");
            }
            sb.append(Long.toString(hash[i] & 0xff, 16));
        }
        return sb.toString();
    }

    public static String hash(String s) {
        try {
            return new String(Objects.requireNonNull(toHex(md5(s))).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("not supported charset...{}", e);
            return s;
        }
    }


    public static String encryptPassword(String loginName, String password, String salt) {
        return new Md5Hash(loginName + password + salt).toHex();
    }

    /**
     * 验证密码
     * @param adminUser 管理员对象
     * @param pwd       需验证的密码
     * @return true or false
     */
    public static boolean validatePwd(AdminUser adminUser,String pwd){
        return adminUser.getPassword().equals(encryptPassword(adminUser.getLoginName(),pwd,adminUser.getSalt()));
    }
//    public static void main(String[] args) {
//        AdminUser adminUser = new AdminUser();
//        adminUser.setPassword("b35b89afc0e805106fdc6aeb0bc0f00f");
//        adminUser.setLoginName("admin");
//        adminUser.setSalt("888888");
//        System.out.println(validatePwd(adminUser,"admin"));
//    }
}
