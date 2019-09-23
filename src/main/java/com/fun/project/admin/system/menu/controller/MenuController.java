package com.fun.project.admin.system.menu.controller;

import com.fun.project.admin.system.menu.entity.Menu;
import com.fun.project.admin.system.menu.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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




}
