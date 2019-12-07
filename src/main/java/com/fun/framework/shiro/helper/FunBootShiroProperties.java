package com.fun.framework.shiro.helper;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * FUN-BOOT Shiro相关配置参数
 *
 * @author DJun
 */
@Data
@Component
@SpringBootConfiguration
@PropertySource(value = {"classpath:fun-boot.properties"})
@ConfigurationProperties(prefix = "shiro")
public class FunBootShiroProperties {
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
