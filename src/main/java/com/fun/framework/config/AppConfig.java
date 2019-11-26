package com.fun.framework.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * App端的properties
 *
 * @author DJun
 * @date 2019/11/19
 */
@Data
@Component
@PropertySource(value = {"classpath:fun-boot.properties"})
@SpringBootConfiguration
public class AppConfig {

    @Value("${rsa.publicKey}")
    private String publicKey;

    @Value("${rsa.privateKey}")
    private String privateKey;

    @Value("${rsa.signature}")
    private String signature;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.expirationRemember}")
    private Long expirationRemember;

    @Value("${jwt.userPrefix}")
    private String userPrefix;


}
