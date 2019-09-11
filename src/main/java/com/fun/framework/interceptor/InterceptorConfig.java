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
        // 默认登录拦截全部接口
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/api/**")
                .addPathPatterns("/admin/**");
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        // 登录拦截
        return new AuthenticationInterceptor();
    }

}