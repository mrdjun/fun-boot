package com.fun.project.admin.system.menu.controller;

import com.fun.common.result.CommonResult;
import com.fun.framework.annotaion.Log;
import com.fun.project.admin.system.menu.entity.Menu;
import com.fun.project.admin.system.menu.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 菜单
 * TODO：
 *  1、获取全部菜单
 *  2、菜单维护：新增、删除、修改
 *  3、校验菜单名称是否唯一
 *  4、加载角色下的菜单列表树
 *  5、加载所有菜单列表树
 *
 * @author DJun
 * @date 2019/9/23
 */
@Controller
@RequestMapping("/admin/system/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @PostMapping("/list")
    @ResponseBody
    public List<Menu> list(Menu menu) {
        return menuService.selectMenuList(menu);
    }

    @Log("新增菜单")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult add(@Validated Menu menu) {
        if (menuService.checkMenuNameUnique(menu) > 0)
            return CommonResult.failed("该菜单已存在");
        return CommonResult.success(menuService.insertMenu(menu));
    }

    @Log("删除菜单")
    @PostMapping("/remove/{menuId}")
    @ResponseBody
    public CommonResult remove(@PathVariable("menuId") Long menuId){

        if (menuService.selectCountMenuByParentId(menuId)>0)
            return CommonResult.failed("存在子菜单，不允许删除");
        if (menuService.selectCountRoleMenuByMenuId(menuId)>0)
            return CommonResult.failed("该菜单已分配，不允许删除");

        return CommonResult.success(menuService.deleteMenuById(menuId));
    }

}
