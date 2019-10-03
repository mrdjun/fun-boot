package com.fun.project.admin.system.controller;

import com.fun.common.result.CommonResult;
import com.fun.project.admin.system.entity.Role;
import com.fun.project.admin.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author DJun
 * @date 2019/10/3
 */
@Controller
@RequestMapping("/admin/system/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    @ResponseBody
    public CommonResult list(Role role){
        return CommonResult.success(roleService.selectRoleList(role));
    }
}
