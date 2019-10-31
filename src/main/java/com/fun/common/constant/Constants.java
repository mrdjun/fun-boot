package com.fun.common.constant;


/**
 * 通用常量信息
 *
 * @author DJun
 */
public class Constants {

    /**  通用成功、失败标识 */
    public static final String SUCCESS = "0";
    public static final String FAIL = "1";

    /** 登录状态 */
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGIN_FAIL = "登录失败";
    public static final String LOGOUT = "注销成功";

    /** 前端页面路径前缀 */
    public static final String VIEW_PREFIX = "fun/views/";
    public static String view(String view) {return VIEW_PREFIX + view;}

    /** 获取ip地区地址开关 */
    public static final boolean ADDRESS_ENABLED = true;

    /** 字典类型是否唯一的返回结果码 */
    public final static String UNIQUE = "0";
    public final static String NOT_UNIQUE = "1";

    /** 字典正常状态 */
    public static final String DICT_NORMAL = "0";

    /** 是否为系统默认（是） */
    public static final String YES = "Y";

}
