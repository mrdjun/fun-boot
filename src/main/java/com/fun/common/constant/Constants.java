package com.fun.common.constant;


/**
 * 通用常量信息
 * 用户状态：0-禁用 1-正常
 *
 * @author DJun
 */
public interface Constants {

    /**
     * 通用成功、失败标识
     */
    String SUCCESS = "1";
    String FAIL = "0";

    /**
     * 通用status
     */
    String NORMAL = "1";
    String FORBIDDEN = "0";

    /**
     * 登录状态
     */
    String LOGIN_SUCCESS = "登录成功";
    String LOGIN_FAIL = "登录失败，账号或密码错误";
    String LOGOUT = "注销成功";

    /**
     * 前端页面路径前缀
     */
    String VIEW_PREFIX = "fun/views/";

    /**
     * 获取ip地区地址开关
     */
    boolean ADDRESS_ENABLED = true;

    /**
     * 是否唯一的返回结果码
     */
    String UNIQUE = "0";
    String NOT_UNIQUE = "1";

    /**
     * 字典正常状态
     */
    String DICT_NORMAL = "1";

    /**
     * 部门正常状态
     */
    String DEPT_NORMAL = "1";

    /**
     * 是否为系统默认（是）
     */
    String YES = "Y";

    /**
     * 排序的方向 "desc" 或者 "asc"
     */
    String IS_ASC = "isAsc";

    /**
     * 排序列
     */
    String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 当前记录起始索引
     */
    String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    String PAGE_SIZE = "pageSize";

    /**
     * 权限字符串
     */
    String PREMISSION_STRING = "perms[\"{0}\"]";

    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * Header头 token参数
     */
    String TOKEN = "Authorization";

    /**
     * 为空
     */
    String EMPTY = "empty";

    /**
     * 管理员字段
     */
    String ADMIN = "admin";

}
