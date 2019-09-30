package com.fun.framework.config;


import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 系统信息
 * @author DJun
 */
@Data
@Component
@SpringBootConfiguration
@PropertySource(value = {"classpath:fun-boot.properties"})
@ConfigurationProperties(prefix = "fun")
public class FunBootConfig {
    /** 项目名称 */
    private String name;

    /**  版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /**  上传路径 */
    private static String profile;

    /**  获取地址开关 */
    private static boolean addressEnabled;

    /** 日志开关 */
    private boolean openLog;


    public static String getProfile() {
        return profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }
}
