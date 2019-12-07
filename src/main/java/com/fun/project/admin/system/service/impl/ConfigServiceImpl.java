package com.fun.project.admin.system.service.impl;

import com.fun.common.constant.Constants;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.TimestampUtil;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.common.utils.text.Convert;
import com.fun.project.admin.system.mapper.ConfigMapper;
import com.fun.project.admin.system.service.IConfigService;
import com.fun.project.admin.system.entity.Config;

import java.util.List;

/**
 * 参数配置
 *
 * @author DJun
 * @date 2019/10/30
 */
@Service
public class ConfigServiceImpl implements IConfigService {

    @Autowired
    private ConfigMapper configMapper;

    /**
     * 分页查询Config列表
     */
    @Override
    public List<Config> selectConfigList(Config config) {
        return configMapper.selectConfigList(config);
    }

    /**
     * 通过Id查询 Config
     */
    @Override
    public Config selectConfigById(long configId) {
        return configMapper.selectConfigById(configId);
    }

    /**
     * 新增Config
     */
    @Override
    public int insertConfig(Config config) {
        config.setCreateTime(TimestampUtil.getCurrentTimestamp13());
        if (StringUtils.isNotEmpty(ShiroUtils.getLoginName())){
            config.setCreateBy(ShiroUtils.getLoginName());
        }
        return configMapper.insertConfig(config);
    }

    /**
     * 通过id删除Config
     */
    @Override
    public int deleteConfigById(long configId) {
        return configMapper.deleteConfigById(configId);
    }

    /**
     * 通过id批量删除Config
     */
    @Override
    public int deleteConfigByIds(String configIds) {
        return configMapper.deleteConfigByIds(Convert.toStrArray(configIds));
    }

    /**
     * 修改Config信息
     */
    @Override
    public int updateConfig(Config config) {
        config.setUpdateTime(TimestampUtil.getCurrentTimestamp13());
        if (StringUtils.isNotEmpty(ShiroUtils.getLoginName())){
            config.setUpdateBy(ShiroUtils.getLoginName());
        }
        return configMapper.updateConfig(config);
    }

    /**
     * 校验参数键名是否唯一
     */
    @Override
    public String checkConfigKeyUnique(Config config) {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        Config info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        Config config = new Config();
        config.setConfigKey(configKey);
        Config retConfig = configMapper.selectConfig(config);
        return StringUtils.isNotNull(retConfig) ? retConfig.getConfigValue() : "";
    }
}
