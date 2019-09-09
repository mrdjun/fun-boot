package com.fun.framework.annotaion;

import com.fun.framework.annotaion.enums.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by DJun on 2019/9/7 17:57
 * desc: 接口限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /**
     * 资源名称，用于描述接口功能
     */
    String name() default "";

    /**  资源 key */
    String key() default "";

    /**  key prefix */
    String prefix() default "";

    /**  时间范围，单位秒 */
    int period();

    /**  限制访问次数 */
    int count();

    /**
     * 限制类型
     */
    LimitType limitType() default LimitType.CUSTOMER;
}
