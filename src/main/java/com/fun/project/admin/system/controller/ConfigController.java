package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.framework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.project.admin.system.service.IConfigService;
import com.fun.project.admin.system.entity.Config;
import com.fun.common.pagehelper.CommonPage;


import java.util.List;

import static com.fun.common.result.CommonResult.failed;
import static com.fun.common.result.CommonResult.success;

/**
 * @author u-fun
 * @date 2019/10/30
 */
@Api(description = "参数配置表")
@Controller
@RequestMapping("/admin/config")
public class ConfigController extends BaseController {

    @Autowired
    private IConfigService configService;


    @ApiOperation(value = "分页查询Config列表")
    @PostMapping("/selectConfigList")
    @ResponseBody
    public CommonResult selectConfigList(Config config) {
        startPage();
        List<Config> configs = configService.selectConfigList(config);
        return success(CommonPage.restPage(configs));
    }


    @ApiOperation(value = "通过Id查询Config")
    @Log("通过Id查询Config")
    @GetMapping("/selectConfigById/{configId}")
    @ResponseBody
    public CommonResult selectConfigById(@PathVariable("configId") Long configId) {
        return success(configService.selectConfigById(configId));
    }

    @ApiOperation(value = "新增Config")
    @Log("新增Config")
    @PostMapping("/insertConfig")
    @ResponseBody
    public CommonResult insertConfig(Config config) {
        if (Constants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return failed("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        return success(configService.insertConfig(config));
    }


    @ApiOperation(value = "修改Config信息")
    @Log("修改Config信息")
    @PostMapping("/updateConfig")
    @ResponseBody
    public CommonResult updateConfig(Config config) {
        return success(configService.updateConfig(config));
    }


    @ApiOperation(value = "通过id删除Config")
    @Log("通过id删除Config")
    @PostMapping("/deleteConfigById/{configId}")
    @ResponseBody
    public CommonResult deleteConfigById(@PathVariable("configId") Long configId) {
        return success(configService.deleteConfigById(configId));
    }

    @ApiOperation(value = "通过id批量删除Config")
    @Log("通过id批量删除Config")
    @PostMapping("/deleteList")
    @ResponseBody
    public CommonResult deleteConfigByIds(String configIds) {
        return success(configService.deleteConfigByIds(configIds));
    }

    /**
     * 校验参数键名
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public String checkConfigKeyUnique(Config config) {
        return configService.checkConfigKeyUnique(config);
    }
}
