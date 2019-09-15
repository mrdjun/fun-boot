package com.fun.framework.annotaion;


import com.fun.common.constant.LoginType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by DJun on 2019/9/9 19:47
 * desc: 操作日志记录
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /** 操作名称 */
    String value() default "";
    /** 默认为后端 */
    LoginType type() default LoginType.admin;
    /** 是否保存请求的参数 */
    boolean isSaveRequestData() default true;
}
