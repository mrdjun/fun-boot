package com.fun.project.app.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.utils.HttpUtils;
import com.fun.common.utils.StringUtils;
import com.fun.project.app.business.service.IBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * created by DJun on 2019/9/16 10:26
 * desc:
 *
 * @author DJun
 */
@Service
public class BusinessServiceImpl implements IBusinessService {
    private static final Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);
    private static final String WEATHER_URL = "https://www.tianqiapi.com/api/";

    @Override
    public JSONObject getWeatherInfo(String city) {
        String rspStr;
        String weatherInfo = "XX XX";
        rspStr = HttpUtils.sendGet(WEATHER_URL, "version=v1&appid=22331626&appsecret=GsQdwp34&city=" + city);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取 WEATHER_URL 为{}天气异常 ", WEATHER_URL);
            return null;
        }

        return JSONObject.parseObject(rspStr);
    }
}
