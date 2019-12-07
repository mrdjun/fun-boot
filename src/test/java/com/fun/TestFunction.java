//package com.fun;
//
//import com.fun.common.utils.StringUtils;
//import com.fun.common.utils.text.Convert;
//import org.jasypt.util.text.BasicTextEncryptor;
//import org.junit.Test;
//
///**
// * @author DJun
// * @date 2019/11/26
// */
//public class TestFunction {
//
//    @Test
//    public void strSubString() {
//        String str = "[\"user:view\",\"user:edit\",\"user:delete\",\"square:view\"]";
//
//        String[] arr = arrayStrToArr(str);
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(i + "=" + arr[i]);
//        }
//
//    }
//
//    public String[] arrayStrToArr(String arrStr) {
//        if (StringUtils.isEmpty(arrStr) && arrStr.length() == 0) {
//            throw new IllegalArgumentException();
//        }
//
//        String s = arrStr.trim().replace("\"", "")
//                .replaceFirst("\\[", "");
//        int len = s.length();
//        return Convert.toStrArray(s.substring(0, len - 1));
//    }
//
//    @Test
//    public void testEncryptDB() {
//        BasicTextEncryptor encryptor = new BasicTextEncryptor();
//        encryptor.setPassword("fun-boot");
//        String encryptedDatasourceUsername = encryptor.encrypt("root");
//        String encryptedDatasourcePassword = encryptor.encrypt("root");
//        System.out.println("用户名加密结果：" + encryptedDatasourceUsername);
//        System.out.println("密码加密结果：" + encryptedDatasourcePassword);
//
//    }
//}
