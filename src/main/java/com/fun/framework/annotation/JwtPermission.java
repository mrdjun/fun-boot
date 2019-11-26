package com.fun.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义 jwt 权限校验注解
 * 使用该注解后可省略使用 @NeedLoginToken
 *
 * @author DJun
 * @date 2019/11/25
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtPermission {
    String value() default "";
}
