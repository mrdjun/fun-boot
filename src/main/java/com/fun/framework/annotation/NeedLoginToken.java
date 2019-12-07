package com.fun.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于App端登录后才能操作
 * 默认拦截 /app/** 的所有请求，所以在/app后的URI不用写该注解
 * 若需更改，参考 com.fun.framework.interceptor.InterceptorConfig.class
 *
 * @author DJun
 * @date 2019/11/11 7:30
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLoginToken {
    boolean required() default true;
}
