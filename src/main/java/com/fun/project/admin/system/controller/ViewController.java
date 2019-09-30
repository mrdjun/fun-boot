package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.framework.config.FunBootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author U-Fun
 * @date 2019/9/29
 */
@Controller
public class ViewController {
//    @Autowired
//    private IMenuService menuService;
    @Autowired
    private FunBootConfig funBootConfig;

    /**
     * 定向到首页
     */
    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/admin/index";
    }

    /**
     * 定向到首页
     */
    @GetMapping("/index")
    public String indexRedirectIndex() {
        return "redirect:/admin/index";
    }

    /**
     * 定向到登录页
     */
    @GetMapping("/login")
    public String redirectLogin() {
        return "redirect:/admin/login";
    }

    /**
     * 系统首页
     * 在用户访问首页的时候，就把该用户的权限、角色等传给前端
     */
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
        return new ModelAndView( Constants.VIEW_PREFIX + "index");
    }

    /**
     * 系统介绍页
     */
    @GetMapping("/system/main")
    public ModelAndView main(ModelMap mmap) {
        mmap.put("version", funBootConfig.getVersion());
        mmap.put("name", funBootConfig.getName());
        mmap.put("copyrightYear", funBootConfig.getCopyrightYear());
        return new ModelAndView(Constants.VIEW_PREFIX + "main") ;
    }

    /**
     * 无权限
     */
    @GetMapping("/403")
    public ModelAndView unauthorized() {
        return new ModelAndView("/error/403");
    }

    /**
     * 无页面
     */
    @GetMapping("/404")
    public ModelAndView error404() {
        return new ModelAndView("/error/404");
    }

    /**
     * 服务端错误
     */
    @GetMapping("/500")
    public ModelAndView error500() {
        return new ModelAndView("/error/500");
    }

}
