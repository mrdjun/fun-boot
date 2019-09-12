package com.fun.framework.annotaion;

import java.lang.annotation.*;

/**
 * created by DJun on 2019/9/12 21:14
 * desc: 防止表单重复提交
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

}
