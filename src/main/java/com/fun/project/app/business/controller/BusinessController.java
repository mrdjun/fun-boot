package com.fun.project.app.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.fun.framework.annotation.PassToken;
import com.fun.project.app.business.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author DJun
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/app")
public class BusinessController {
    @Autowired
    private IBusinessService businessService;

    @PassToken
    @GetMapping("/weatherInfo/{city}")
    public JSONObject testGetWeatherInfo(@PathVariable String city) {
        return businessService.getWeatherInfo(city);
    }

}
