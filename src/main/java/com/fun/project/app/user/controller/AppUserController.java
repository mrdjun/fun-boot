package com.fun.project.app.user.controller;

import java.util.List;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.poi.ExcelUtil;
import com.fun.common.utils.text.Convert;
import com.fun.project.app.user.dto.UserDto;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.service.IAppRoleService;
import com.fun.project.app.user.service.IAppUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fun.framework.annotation.Log;
import com.fun.common.result.CommonResult;
import com.fun.framework.web.controller.AdminBaseController;
import io.swagger.annotations.Api;

import javax.validation.Valid;

import static com.fun.common.result.CommonResult.*;

/**
 * @author DJun
 * @date 2019-11-27
 */
@Api(tags = {"管理端app用户管理"})
@Controller
@RequestMapping("/admin/app/user")
public class AppUserController extends AdminBaseController {
    private String prefix = "app/user";

    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IAppRoleService appRoleService;

    @RequiresPermissions("app:user:view")
    @GetMapping()
    public String user() {
        return view(prefix + "/user");
    }

    @ApiOperation("查询app用户列表")
    @RequiresPermissions("app:user:list")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult list(AppUser appUser) {
        startPage();
        List<AppUser> list = appUserService.selectUserList(appUser);
        return success(CommonPage.restPage(list));
    }

    @ApiOperation("导出app用户列表")
    @RequiresPermissions("app:user:export")
    @PostMapping("/export")
    @ResponseBody
    public CommonResult export(AppUser appUser) {
        List<AppUser> list = appUserService.selectUserList(appUser);
        ExcelUtil<AppUser> util = new ExcelUtil<>(AppUser.class);
        return util.exportExcel(list, "user");
    }

    /**
     * 新增app用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("roles",appRoleService.selectRoleAll());
        return view(prefix + "/add");
    }

    @ApiOperation("新增保存app用户")
    @RequiresPermissions("app:user:add")
    @Log("新增app用户")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult addSave(AppUser user) {
        UserDto userDto = new UserDto();
        userDto.setLoginName(user.getLoginName());
        userDto.setEmail(user.getEmail());
        userDto.setTelephone(user.getTelephone());
        if (Constants.NOT_UNIQUE.equals(appUserService.checkLoginNameUnique(userDto))) {
            return warn("登录账号：'" + user.getLoginName() + "'，已存在");
        } else if (Constants.NOT_UNIQUE.equals(appUserService.checkEmailUnique(userDto))) {
            return warn("邮箱：'" + user.getEmail() + "'，已存在");
        } else if (Constants.NOT_UNIQUE.equals(appUserService.checkEmailUnique(userDto))) {
            return warn("手机号：'" + user.getTelephone() + "'，已存在");
        }
        return success(appUserService.insertUser(user));
    }

    /**
     * 修改app用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        AppUser appUser = appUserService.selectUserById(userId);
        mmap.put("roles", appRoleService.selectAppRolesByUserId(userId));
        mmap.put("appUser", appUser);
        return view(prefix + "/edit");
    }

    @ApiOperation("修改保存app用户")
    @RequiresPermissions("app:user:edit")
    @Log("修改app用户")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(AppUser user) {
        UserDto userDto = new UserDto();
        userDto.setLoginName(user.getLoginName());
        userDto.setEmail(user.getEmail());
        userDto.setTelephone(user.getTelephone());
        if (StringUtils.isNotNull(user.getUserId()) && user.getUserId() == 1L) {
            return warn("不允许修改超级管理员");
        }
        return success(appUserService.updateAppUser(user));
    }

    @ApiOperation("删除app用户")
    @RequiresPermissions("app:user:remove")
    @Log("删除app用户")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult remove(String ids) {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long id : userIds) {
            if (1L == id) {
                return warn("不允许删除超级管理员");
            }
        }
        return success(appUserService.deleteUserByIds(ids));
    }

    @ApiOperation("异步验证邮箱唯一")
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(@Valid UserDto user){
        return appUserService.checkEmailUnique(user);
    }

    @ApiOperation("异步验证用户名是否唯一")
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(@Valid UserDto user) {
        return appUserService.checkLoginNameUnique(user);
    }

    @ApiOperation("异步验证手机号码是否唯一")
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(@Valid UserDto user) {
        return appUserService.checkPhoneUnique(user);
    }

    @ApiOperation("异步验证用户名是否唯一")
    @PostMapping("/checkUAccountUnique")
    @ResponseBody
    public String checkUAccountUnique(@Valid UserDto user) {
        return appUserService.checkUAccountUnique(user);
    }
}