package com.fun.project.admin.system.user.controller;

import com.fun.framework.config.FunBootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author U-Fun
 * @date 2019/9/29
 */
@Controller
public class IndexController {
    String prefix = "/fun/views";
//    @Autowired
//    private IMenuService menuService;
    @Autowired
    private FunBootConfig funBootConfig;


    // 系统首页
    @GetMapping("/admin/index")
    public ModelAndView index(ModelMap mmap) {
//        // 取身份信息
//        AdminUser user = getSysUser();
//        // 根据用户id取出菜单
//        List<Menu> menus = menuService.selectMenusByUser(user);
//        mmap.put("menus", menus);
//        mmap.put("user", user);
//        mmap.put("copyrightYear", funBootConfig.getCopyrightYear());
//        mmap.put("demoEnabled", funBootConfig.isDemoEnabled());
        return new ModelAndView( prefix + "/index");
    }



    // 系统介绍
    @GetMapping("/system/main")
    public ModelAndView main(ModelMap mmap) {
        mmap.put("version", funBootConfig.getVersion());
        mmap.put("name", funBootConfig.getName());
        mmap.put("copyrightYear", funBootConfig.getCopyrightYear());
        return new ModelAndView(prefix + "/main") ;
    }

}
