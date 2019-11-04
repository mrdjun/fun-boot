package com.fun.project.app.business.service;

import com.alibaba.fastjson.JSONObject;

/**
 * created by DJun on 2019/9/16 11:31
 * desc:
 * @author DJun
 */
public interface IBusinessService {

    JSONObject getWeatherInfo(String city);

}
