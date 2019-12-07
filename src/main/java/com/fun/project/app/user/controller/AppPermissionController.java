package com.fun.project.app.user.controller;


import java.util.List;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.framework.web.entity.Ztree;
import com.fun.project.app.user.entity.AppPermission;
import com.fun.project.app.user.service.IAppPermissionService;
import com.fun.project.app.user.service.IAppRoleService;
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

import static com.fun.common.result.CommonResult.*;

/**
 * @author DJun
 * @date 2019/12/2
 */
@Api(tags = {"App角色权限"})
@Controller
@RequestMapping("/admin/app/perm")
public class AppPermissionController extends AdminBaseController {
    private String prefix = "app/perm";
    @Autowired
    private IAppRoleService appRoleService;
    @Autowired
    private IAppPermissionService appPermissionService;

    @RequiresPermissions("app:perm:view")
    @GetMapping()
    public String perm() {
        return view(prefix + "/perm");
    }

    @ApiOperation("查询角色权限列表")
    @RequiresPermissions("app:perm:list")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult list(AppPermission perm) {
        startPage();
        List<AppPermission> list = appPermissionService.selectAppPermissionList(perm);
        return success(CommonPage.restPage(list));
    }

    /**
     * 新增角色权限
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("roles", appRoleService.selectRoleAll());
        return view(prefix + "/add");
    }

    @ApiOperation("新增保存权限")
    @RequiresPermissions("app:perm:add")
    @Log("新增角色权限")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult addSave(AppPermission appPermission) {
        if (Constants.NOT_UNIQUE.equals(appPermissionService.checkPermUnique(appPermission))) {
            return warn("权限字符：'" + appPermission.getPerm() + "'，已存在");
        }
        return success(appPermissionService.insertAppPermission(appPermission));
    }

    /**
     * 修改角色权限
     */
    @GetMapping("/edit/{permId}")
    public String edit(@PathVariable("permId") Long permId, ModelMap mmap) {
        AppPermission appPermission = appPermissionService.selectAppPermissionById(permId);
        mmap.put("appPerm", appPermission);
        return view(prefix + "/edit");
    }

    @ApiOperation("修改保存权限")
    @RequiresPermissions("app:perm:edit")
    @Log("修改角色权限")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(AppPermission perm) {
        return success(appPermissionService.updateAppPermission(perm));
    }


    @ApiOperation("删除权限")
    @RequiresPermissions("app:perm:remove")
    @Log("删除角色权限")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult remove(String ids) {
        return success(appPermissionService.deleteAppPermissionByIds(ids));
    }

    @ApiOperation("加载角色权限列表树")
    @RequiresPermissions("app:perm:list")
    @GetMapping("/rolePermTreeData")
    @ResponseBody
    public List<Ztree> rolePermTreeData(AppPermission perm) {
        return appPermissionService.rolePermTreeData(perm);
    }

    @ApiOperation("加载所有权限列表树")
    @RequiresPermissions("app:perm:list")
    @GetMapping("/permTreeData")
    @ResponseBody
    public List<Ztree> permTreeData() {
        return appPermissionService.permTreeData();
    }

    @GetMapping("/selectPermTree/{permId}")
    public String selectPermTree(@PathVariable("permId") Long permId, ModelMap mmap) {
        mmap.put("perm", appPermissionService.selectAppPermissionById(permId));
        return view(prefix + "/tree");
    }

    @ApiOperation("校验权限字符唯一")
    @PostMapping("/checkPermUnique")
    @ResponseBody
    public String checkPermUnique(AppPermission appPermission) {
        return appPermissionService.checkPermUnique(appPermission);
    }

}
