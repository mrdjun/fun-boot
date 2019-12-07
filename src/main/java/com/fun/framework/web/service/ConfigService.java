package com.fun.framework.web.service;

import com.fun.project.admin.system.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * thymeleaf 实现调用参数
 * 
 * @author DJun
 * @date 2019/10/30
 */
@Service("config")
public class ConfigService {
    @Autowired
    private IConfigService configService;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    public String getKey(String configKey) {
        return configService.selectConfigByKey(configKey);
    }
}
