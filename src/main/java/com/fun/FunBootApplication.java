package com.fun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author MrDJun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FunBootApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(FunBootApplication.class, args);
    }
}