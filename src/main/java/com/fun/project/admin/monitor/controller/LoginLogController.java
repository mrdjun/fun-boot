package com.fun.project.admin.monitor.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.monitor.entity.LoginLog;
import com.fun.project.admin.monitor.service.ILoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fun.common.result.CommonResult.success;

/**
 * @author DJun
 * @date 2019/9/13 12:22
 */
@Api(tags = {"登录日志"})
@Controller
@RequestMapping("/admin/monitor/loginlog")
public class LoginLogController extends AdminBaseController {
    @Autowired
    private ILoginLogService loginLogService;

    @RequiresPermissions("monitor:loginLog:view")
    @GetMapping()
    public String loginLog() {
        return view("monitor/log/loginLog");
    }

    @RequiresPermissions("monitor:loginLog:list")
    @ApiOperation(value = "分页查询LoginLog列表")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult selectLoginLogList(LoginLog loginLog) {
        startPage();
        List<LoginLog> loginLogList = loginLogService.selectLoginLogList(loginLog);
        return success(CommonPage.restPage(loginLogList));
    }


    @RequiresPermissions("monitor:loginLog:remove")
    @ApiOperation(value = "删除LoginLog")
    @Log("删除LoginLog")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult deleteLoginLogByIds(String ids) {
        return success(loginLogService.deleteLoginLogByIds(ids));
    }

    @RequiresPermissions("monitor:loginLog:remove")
    @Log("清空登陆日志")
    @PostMapping("/clean")
    @ResponseBody
    public CommonResult clean() {
        loginLogService.cleanLoginLog();
        return success(Constants.SUCCESS);
    }

}
