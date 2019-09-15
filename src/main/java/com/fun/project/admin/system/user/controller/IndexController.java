package com.fun.project.admin.system.user.controller;

import com.fun.framework.config.FunBootConfig;
import com.fun.project.admin.system.menu.entity.Menu;
import com.fun.project.admin.system.menu.service.IMenuService;
import com.fun.project.admin.system.user.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.fun.framework.shiro.ShiroUtils.getSysUser;

/**
 * created by DJun on 2019/9/14 13:07
 * desc:
 */
@Controller
public class IndexController {
    String prefix = "/fun/views";
    @Autowired
    private IMenuService menuService;
    @Autowired
    private FunBootConfig funBootConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        AdminUser user = getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", funBootConfig.getCopyrightYear());
        mmap.put("demoEnabled", funBootConfig.isDemoEnabled());
        return prefix + "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", funBootConfig.getVersion());
        return prefix + "main";
    }

}
