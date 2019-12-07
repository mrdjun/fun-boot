package com.fun.project.app.user.controller;


import java.util.List;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.utils.poi.ExcelUtil;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.service.IAppUserRoleService;
import com.fun.project.app.user.service.IAppUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fun.framework.annotation.Log;
import com.fun.project.app.user.entity.AppRole;
import com.fun.project.app.user.service.IAppRoleService;
import com.fun.common.result.CommonResult;
import com.fun.framework.web.controller.AdminBaseController;
import io.swagger.annotations.Api;

import javax.validation.Valid;

import static com.fun.common.result.CommonResult.*;

/**
 * @author DJun
 * @date 2019-11-29
 */
@Api(tags = {"app角色管理"})
@Controller
@RequestMapping("/admin/app/role")
public class AppRoleController extends AdminBaseController {
    private String prefix = "app/role";

    @Autowired
    private IAppRoleService appRoleService;
    @Autowired
    private IAppUserRoleService appUserRoleService;

    @RequiresPermissions("app:role:view")
    @GetMapping()
    public String appRole() {
        return view(prefix + "/role");
    }

    @ApiOperation("查询角色列表")
    @RequiresPermissions("app:role:list")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult list(AppRole appRole) {
        startPage();
        List<AppRole> list = appRoleService.selectAppRoleList(appRole);
        return success(CommonPage.restPage(list));
    }

    @ApiOperation("导出角色列表")
    @RequiresPermissions("app:role:export")
    @PostMapping("/export")
    @ResponseBody
    public CommonResult export(AppRole appRole) {
        List<AppRole> list = appRoleService.selectAppRoleList(appRole);
        ExcelUtil<AppRole> util = new ExcelUtil<>(AppRole.class);
        return util.exportExcel(list, "角色列表");
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add() {
        return view(prefix + "/add");
    }

    @ApiOperation("新增保存角色")
    @RequiresPermissions("app:role:add")
    @Log("新增角色")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult addSave(@Valid AppRole appRole) {
        if (Constants.NOT_UNIQUE.equals(appRoleService.checkRoleKeyUnique(appRole))) {
            return failed("新增角色'" + appRole.getRoleKey() + "'失败，角色字符串已存在");
        }
        return success(appRoleService.insertAppRole(appRole));
    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        AppRole appRole = appRoleService.selectAppRoleById(roleId);
        mmap.put("appRole", appRole);
        return view(prefix + "/edit");
    }

    @ApiOperation("修改保存角色")
    @RequiresPermissions("app:role:edit")
    @Log("修改角色")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(@Validated AppRole appRole) {
        if (Constants.NOT_UNIQUE.equals(appRoleService.checkRoleKeyUnique(appRole))) {
            return failed("修改角色'" + appRole.getRoleKey() + "'失败，角色字符串已存在");
        }
        return success(appRoleService.updateAppRole(appRole));
    }

    @ApiOperation("删除角色")
    @RequiresPermissions("app:role:remove")
    @Log("删除角色")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult remove(String ids) {
        return success(appRoleService.deleteAppRoleByIds(ids));
    }

    /**
     * 分配用户
     */
    @RequiresPermissions("system:role:edit")
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", appRoleService.selectAppRoleById(roleId));
        return view(prefix + "/authUser");
    }

    @ApiOperation("查询已分配用户角色列表")
    @RequiresPermissions("app:role:edit")
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public CommonResult allocatedList(AppUser user) {
        startPage();
        List<AppUser> list = appUserRoleService.selectAllocatedList(user);
        return success(CommonPage.restPage(list));
    }

    @ApiOperation("查询未分配用户角色列表")
    @RequiresPermissions("app:role:edit")
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public CommonResult unallocatedList(AppUser user) {
        startPage();
        List<AppUser> list = appUserRoleService.selectUnallocatedList(user);
        return success(CommonPage.restPage(list));
    }

    @ApiOperation("批量选择用户授权")
    @Log("批量选择用户授权")
    @RequiresPermissions("app:role:edit")
    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public CommonResult selectAuthUserAll(Long roleId, String userIds) {
        return success(appUserRoleService.insertAuthUsers(roleId, userIds));
    }

    @ApiOperation("取消单个授权")
    @Log("取消单个授权")
    @RequiresPermissions("app:role:edit")
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public CommonResult cancelAuthUser(Long roleId, Long userId) {
        return success(appUserRoleService.deleteAuthUser(roleId, userId));
    }

    @ApiOperation("批量取消授权")
    @Log("批量取消授权")
    @RequiresPermissions("app:role:edit")
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public CommonResult cancelAuthUserAll(Long roleId, String userIds) {
        return success(appUserRoleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 选择用户
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", appRoleService.selectAppRoleById(roleId));
        return view(prefix + "/selectUser");
    }

    @ApiOperation("校验权限字符串唯一")
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(AppRole appRole){
        return appRoleService.checkRoleKeyUnique(appRole);
    }

}