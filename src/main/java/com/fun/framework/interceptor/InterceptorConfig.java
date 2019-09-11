package com.fun.framework.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * created by DJun on 2019/9/8 14:45
 * desc:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截api下所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();// 自己写的拦截器
    }

    //省略其他重写方法

}