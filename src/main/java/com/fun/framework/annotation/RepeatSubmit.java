package com.fun.framework.annotation;

import java.lang.annotation.*;

/**
 * 防止表单重复提交
 * @author DJun
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {}
