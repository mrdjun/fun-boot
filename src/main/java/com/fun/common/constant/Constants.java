package com.fun.common.constant;


/**
 * 通用常量信息
 * 用户状态：0-禁用 1-正常
 *
 * @author DJun
 */
public class Constants {

    /**
     * 通用成功、失败标识
     */
    public static final String SUCCESS = "0";
    public static final String FAIL = "1";

    /**
     * 登录状态
     */
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGIN_FAIL = "登录失败";
    public static final String LOGOUT = "注销成功";

    /**
     * 前端页面路径前缀
     */
    public static final String VIEW_PREFIX = "fun/views/";

    /**
     * 获取ip地区地址开关
     */
    public static final boolean ADDRESS_ENABLED = true;

    /**
     * 是否唯一的返回结果码
     */
    public final static String UNIQUE = "0";
    public final static String NOT_UNIQUE = "1";

    /**
     * 字典正常状态
     */
    public static final String DICT_NORMAL = "1";

    /**
     * 部门正常状态
     */
    public static final String DEPT_NORMAL = "1";

    /**
     * 是否为系统默认（是）
     */
    public static final String YES = "Y";

    /**
     * 排序的方向 "desc" 或者 "asc"
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 权限字符串
     */
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";
}
