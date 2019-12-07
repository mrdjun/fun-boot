package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.system.entity.role.Role;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.entity.user.UserRole;
import com.fun.project.admin.system.service.IAdminUserService;
import com.fun.project.admin.system.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fun.common.result.CommonResult.failed;
import static com.fun.common.result.CommonResult.success;

/**
 * @author DJun
 * @date 2019/10/3
 */
@Api(tags = {"管理员角色信息"})
@Controller
@RequestMapping("/admin/system/role")
public class RoleController extends AdminBaseController {

    private String prefix = "system/role/";

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAdminUserService userService;

    @RequiresPermissions("system:role:view")
    @GetMapping()
    public String role() {
        return view(prefix + "role");
    }

    @ApiOperation("分页获取角色")
    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult list(Role role) {
        startPage();
        List<Role> list = roleService.selectRoleList(role);
        return success(CommonPage.restPage(list));
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add() {
        return view(prefix + "add");
    }

    @ApiOperation("新增保存角色")
    @RequiresPermissions("system:role:add")
    @Log("新增角色")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult addSave(@Validated Role role) {
        if (Constants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return failed("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (Constants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return failed("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return success(roleService.insertRole(role));
    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return view(prefix + "edit");
    }

    @ApiOperation("修改保存角色")
    @RequiresPermissions("system:role:edit")
    @Log("修改角色")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(@Validated Role role) {
        if (Constants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return failed("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (Constants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return failed("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return success(roleService.updateRole(role));
    }

    /**
     * 角色分配数据权限
     */
    @GetMapping("/authDataScope/{roleId}")
    public String authDataScope(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return view(prefix + "dataScope");
    }

    @ApiOperation("保存角色分配数据权限")
    @RequiresPermissions("system:role:edit")
    @Log("保存角色分配数据权限")
    @PostMapping("/authDataScope")
    @ResponseBody
    public CommonResult authDataScopeSave(Role role) {
        if (roleService.authDataScope(role) > 0) {
            setSysUser(userService.selectAdminUserById(getSysUser().getUserId()));
            return success(Constants.SUCCESS);
        }
        return failed();
    }

    @ApiOperation("批量删除角色")
    @RequiresPermissions("system:role:remove")
    @Log("批量删除角色")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult remove(String ids) {
        try {
            return success(roleService.deleteRoleByIds(ids));
        } catch (Exception e) {
            return failed(e.getMessage());
        }
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(Role role) {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(Role role) {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree() {
        return view(prefix + "tree");
    }

    @ApiOperation("角色状态修改")
    @Log("角色状态修改")
    @RequiresPermissions("system:role:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public CommonResult changeStatus(Role role) {
        return success(roleService.changeStatus(role));
    }

    /**
     * 分配用户
     */
    @RequiresPermissions("system:role:edit")
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return view(prefix + "authUser");
    }

    @ApiOperation("查询已分配用户角色列表")
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public CommonResult allocatedList(AdminUser user) {
        startPage();
        List<AdminUser> list = userService.selectAllocatedList(user);
        return success(CommonPage.restPage(list));
    }


    @ApiOperation("单个取消授权")
    @Log("单个取消授权")
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public CommonResult cancelAuthUser(UserRole userRole) {
        return success(roleService.deleteAuthUser(userRole));
    }

    @ApiOperation("批量取消授权")
    @Log("批量取消授权")
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public CommonResult cancelAuthUserAll(Long roleId, String userIds) {
        return success(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 选择用户
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
        mmap.put("role", roleService.selectRoleById(roleId));
        return view(prefix + "selectUser");
    }

    @ApiOperation("查询未分配用户角色列表")
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public CommonResult unallocatedList(AdminUser user) {
        startPage();
        List<AdminUser> list = userService.selectUnallocatedList(user);
        return success(CommonPage.restPage(list));
    }

    @ApiOperation("批量选择用户授权")
    @Log("批量选择用户授权")
    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public CommonResult selectAuthUserAll(Long roleId, String userIds) {
        return success(roleService.insertAuthUsers(roleId, userIds));
    }

}
