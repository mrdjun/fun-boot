package com.fun;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author DJun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
@EnableTransactionManagement
@MapperScan({"com.fun.project.admin.*.*.mapper","com.fun.project.app.*.mapper"})
public class FunBootApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FunBootApplication.class).run(args);
    }
}
