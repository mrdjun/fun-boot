package com.fun;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author DJun
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// 异步
@EnableAsync
// 事务管理
@EnableTransactionManagement
// 允许AOP代理对象，使得AopContext能够访问
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@MapperScan({"com.fun.project.admin.*.mapper","com.fun.project.app.*.mapper"})
public class FunBootApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FunBootApplication.class).run(args);
    }
}
