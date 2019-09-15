package com.fun.framework.shiro;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * created by DJun on 2019/9/13 22:40
 * desc:
 */
@Data
@Component
@SpringBootConfiguration
@PropertySource(value = {"classpath:fun-boot.properties"})
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {
    private String loginUrl;
    private String unauthorizedUrl;
    private String indexUrl;
    private Boolean captchaEnabled;
    private String captchaType;
    private Long sessionTimeout;
    private Integer cookieTimeout;
    private String anonUrl;
    private String logoutUrl;
}
