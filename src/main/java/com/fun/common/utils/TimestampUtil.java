package com.fun.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳工具类
 * @author DJun
 */
public class TimestampUtil {

    /**
     * 时间戳 转 日期 (10位秒级)
     */
    public static Date TimestampToDate10(String timestamp) {
        return new Date(Long.parseLong(timestamp) * 1000);
    }

    /**
     * 时间戳 转 日期 (13位毫秒级)
     */
    public static String TimestampToDate13(String timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    /**
     * 日期 转 时间戳 (10位秒级)
     */
    public static long DateToTimestamp10(Date date) {
        return date.getTime() / 1000;
    }

    /**
     * 日期 转 时间戳 (13位毫秒级)
     */
    public static long DateToTimestamp13(Date date) {
        return date.getTime();
    }

    /**
     * 获取当前时间的时间戳（10位秒级）
     */
    public static long getCurrentTimestamp10() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间的时间戳（13位毫秒级）
     */
    public static long getCurrentTimestamp13() {
        return System.currentTimeMillis();
    }

}
