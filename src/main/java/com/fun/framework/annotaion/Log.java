package com.fun.framework.annotaion;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by DJun on 2019/9/9 19:47
 * desc: 操作日志记录
 * TODO: 目前是通过捕获app端token，来获取当前用户名
 *       现在需要admin端也能使用这个注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 操作名称
     */
    String value() default "";

}
