package com.fun.project.admin.system.user.controller;

import com.fun.common.pageHelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.project.admin.system.user.entity.AdminUser;
import com.fun.project.admin.system.user.service.IAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by DJun on 2019/9/14 9:42
 * desc:
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    private String prefix = "/fun/views/system/user";
    @Autowired
    private IAdminUserService adminUserService;

    @GetMapping()
    public String user() {
        return prefix + "/user";
    }


    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<AdminUser>> getAdminUserList(AdminUser adminUser,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<AdminUser> list = adminUserService.selectUserList(adminUser, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }



}
