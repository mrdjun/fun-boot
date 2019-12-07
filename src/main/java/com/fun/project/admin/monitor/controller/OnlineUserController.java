package com.fun.project.admin.monitor.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.monitor.entity.OnlineUser;
import com.fun.project.admin.monitor.service.ISessionService;
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
 * @date 2019/11/4
 */
@Api(tags = {"在线用户"})
@Controller
@RequestMapping("/admin/monitor/online")
public class OnlineUserController extends AdminBaseController {
    private static String prefix = "monitor/online";

    @Autowired
    private ISessionService sessionService;

    @RequiresPermissions("monitor:online:view")
    @GetMapping()
    public String onlineUserPage() {
        return view(prefix + "/online");
    }

    @ApiOperation("获取在线用户信息")
    @PostMapping("/list")
    @RequiresPermissions("monitor:online:list")
    @ResponseBody
    public CommonResult selectOnlineUserList(String loginName) {
        startPage();
        List<OnlineUser> list = sessionService.list(loginName);
        return success(CommonPage.restPage(list));
    }

    @ApiOperation("踢出在线用户")
    @PostMapping("/remove")
    @RequiresPermissions("monitor:online:remove")
    @ResponseBody
    public CommonResult kickOutOnlineUser(String ids) {
        sessionService.forceLogout(ids);
        return success(Constants.SUCCESS);
    }
}
