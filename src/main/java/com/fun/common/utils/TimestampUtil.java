package com.fun.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by DJun on 2019/9/7 18:26
 * desc: 时间戳工具类
 */
public class TimestampUtil {

    /**  时间戳 转 日期 (10位秒级) */
    public static Date TimestampToDate10(String timestamp) {
        return new Date(Long.parseLong(timestamp) * 1000);
    }

    /**  时间戳 转 日期 (13位毫秒级) */
    public static String TimestampToDate13(String timestamp){
        // 13位的秒级别的时间戳
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    /**  日期 转 时间戳 */
    public static String DateToTimestamp(Date date) {
        return String.valueOf(date.getTime() / 1000);
    }

    /** 获取当前时间的时间戳 */
    public static String getCurrentTimestamp() {
        return String.valueOf(new Date().getTime() / 1000);
    }
}