package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.framework.web.entity.Ztree;
import com.fun.project.admin.system.entity.Menu;
import com.fun.project.admin.system.entity.role.Role;
import com.fun.project.admin.system.service.IMenuService;
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
 * @date 2019/9/23
 */
@Api(tags = {"管理员后台菜单"})
@Controller
@RequestMapping("/admin/system/menu")
public class MenuController extends AdminBaseController {
    private String prefix = "system/menu";

    @Autowired
    private IMenuService menuService;

    @RequiresPermissions("system:menu:view")
    @GetMapping()
    public String menu() {
        return view(prefix + "/menu");
    }

    @ApiOperation("获取全部菜单")
    @RequiresPermissions("system:menu:list")
    @PostMapping("/list")
    @ResponseBody
    public List<Menu> selectMenuList(Menu menu) {
        return menuService.selectMenuList(menu);
    }


    @GetMapping("/add/{parentId}")
    public String addMenu(@PathVariable("parentId") Long parentId, ModelMap mmap) {
        Menu menu = null;
        if (0L != parentId) {
            menu = menuService.selectMenuById(parentId);
        } else {
            menu = new Menu();
            menu.setMenuId(0L);
            menu.setMenuName("主目录");
        }
        mmap.put("menu", menu);
        return view(prefix + "/add");
    }

    @ApiOperation("新增菜单")
    @Log("新增菜单")
    @PostMapping("/add")
    @ResponseBody
    @RequiresPermissions("system:menu:add")
    public CommonResult insertMenu(@Validated Menu menu) {
        if (Constants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return failed("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        return success(menuService.insertMenu(menu));
    }

    @ApiOperation("通过菜单id删除菜单")
    @Log("删除菜单")
    @GetMapping("/remove/{menuId}")
    @ResponseBody
    @RequiresPermissions("system:menu:remove")
    public CommonResult deleteMenuById(@PathVariable("menuId") Long menuId) {
        if (menuService.selectCountMenuByParentId(menuId) > 0) {
            return failed("存在子菜单，不允许删除");
        }
        if (menuService.selectCountRoleMenuByMenuId(menuId) > 0) {
            return failed("该菜单已分配，不允许删除");
        }
        return success(menuService.deleteMenuById(menuId));
    }

    /**
     * 修改菜单
     */
    @GetMapping("/edit/{menuId}")
    public String selectMenuById(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("menu", menuService.selectMenuById(menuId));
        return view(prefix + "/edit");
    }

    @ApiOperation("修改菜单")
    @Log("修改菜单")
    @RequiresPermissions("system:menu:edit")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult updateMenu(@Validated Menu menu) {
        if (Constants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return failed("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        return success(menuService.updateMenu(menu));
    }

    /**
     * 选择菜单图标
     */
    @GetMapping("/icon")
    public String icon() {
        return view(prefix + "/icon");
    }

    @ApiOperation("校验菜单名称是否唯一")
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public String checkMenuNameUnique(Menu menu) {
        return menuService.checkMenuNameUnique(menu);
    }
    
    @ApiOperation("加载角色菜单列表树")
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Ztree> roleMenuTreeData(Role role) {
        return menuService.roleMenuTreeData(role);
    }

    @ApiOperation("加载所有菜单列表树")
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Ztree> menuTreeData() {
        return menuService.menuTreeData();
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree/{menuId}")
    public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap) {
        mmap.put("menu", menuService.selectMenuById(menuId));
        return view(prefix + "/tree");
    }


}
