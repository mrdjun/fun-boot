package com.fun.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳工具类
 *
 * @author DJun
 */
public class TimestampUtil {

    /**
     * 时间戳 转 日期 (10位秒级)
     */
    public static Date timestampToDate10(String timestamp) {
        return new Date(Long.parseLong(timestamp) * 1000);
    }

    /**
     * 时间戳 转 日期 (13位毫秒级)
     */
    public static String timestampToDate13(Long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    /**
     * 日期 转 时间戳 (10位秒级)
     */
    public static long dateToTimestamp10(Date date) {
        return date.getTime() / 1000;
    }

    /**
     * 日期 转 时间戳 (13位毫秒级)
     */
    public static long dateToTimestamp13(Date date) {
        return date.getTime();
    }

    /**
     * 获取当前时间的时间戳（13位毫秒级）
     */
    public static long getCurrentTimestamp13() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        System.out.println(timestampToDate13(1574429467637L));
    }
}
