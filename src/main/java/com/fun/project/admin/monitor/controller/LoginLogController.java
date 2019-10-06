package com.fun.project.admin.monitor.controller;

import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotaion.Log;
import com.fun.project.admin.monitor.entity.LoginLog;
import com.fun.project.admin.monitor.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by DJun on 2019/9/13 12:22
 * desc: 登录日志
 */
@RestController
@RequestMapping("/admin/monitor/loginLog")
public class LoginLogController {

    @Autowired
    private ILoginLogService loginLogService;

    @Log("获取登录日志列表")
    @PostMapping("/list")
    public CommonResult<CommonPage<LoginLog>> loginLogList(LoginLog loginLog,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        List<LoginLog> logs = loginLogService.selectLoginLogList(loginLog,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(logs));
    }

    @Log("批量删除登录日志")
    @PostMapping("/remove")
    public CommonResult removeLoginLog(String ids){
        return CommonResult.success(loginLogService.deleteLoginLogByIds(ids));
    }

    @Log("清空登录日志")
    @PostMapping("/clean")
    public CommonResult cleanAll(){
        loginLogService.cleanLoginLog();
        return CommonResult.success("清除成功");
    }
}
