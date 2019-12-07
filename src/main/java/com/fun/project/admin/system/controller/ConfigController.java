package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.utils.poi.ExcelUtil;
import com.fun.framework.web.controller.AdminBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
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
 * @author DJun
 * @date 2019/10/30
 */
@Api(tags = {"admin参数配置"})
@Controller
@RequestMapping("/admin/system/config")
public class ConfigController extends AdminBaseController {
    private String prefix = "system/config";

    @Autowired
    private IConfigService configService;

    @RequiresPermissions("system:config:view")
    @GetMapping()
    public String config()
    {
        return view(prefix + "/config");
    }

    @RequiresPermissions("system:config:list")
    @ApiOperation("分页查询Config列表")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult selectConfigList(Config config) {
        startPage();
        List<Config> configs = configService.selectConfigList(config);
        return success(CommonPage.restPage(configs));
    }

    @Log("导出用户列表" )
    @RequiresPermissions("system:user:export" )
    @PostMapping("/export" )
    @ResponseBody
    public CommonResult export(Config config) {
        List<Config> list = configService.selectConfigList(config);
        ExcelUtil<Config> util = new ExcelUtil<>(Config.class);
        return util.exportExcel(list, "系统配置参数" );
    }

    /**
     * 新增参数配置
     */
    @GetMapping("/add")
    public String insertConfigPage() {
        return view(prefix + "/add");
    }

    @RequiresPermissions("system:config:add")
    @ApiOperation("新增Config")
    @Log("新增Config")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult insertConfig(@Validated Config config) {
        if (Constants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return failed("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        return success(configService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @GetMapping("/edit/{configId}")
    public String updateConfigPage(@PathVariable("configId") Long configId, ModelMap mmap) {
        mmap.put("config", configService.selectConfigById(configId));
        return view(prefix + "/edit");
    }

    @RequiresPermissions("system:config:edit")
    @ApiOperation("修改Config信息")
    @Log("修改Config信息")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult updateConfig(@Validated Config config) {
        return success(configService.updateConfig(config));
    }

    @RequiresPermissions("system:config:remove")
    @ApiOperation("删除Config")
    @Log("删除Config")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult deleteConfigByIds(String ids) {
        return success(configService.deleteConfigByIds(ids));
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
