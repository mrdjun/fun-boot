package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.framework.config.FunBootConfig;
import com.fun.project.admin.system.entity.Menu;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.fun.framework.shiro.helper.ShiroUtils.getSysUser;

/**
 *
 * @author MrDJun
 * @date 2019/9/29
 */
@Controller
public class ViewController {
    @Autowired
    private IMenuService menuService;
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
     * 首页 layout 页面
     * 在用户访问首页的时候，就把该用户的权限、角色等传给前端
     * 涉及敏感数据时，如密码可将密码属性用@JSONField(serial=false)反序列化。
     * 此外，不暴露出来也行，管理端页面直接使用集合即可。
     */
    @GetMapping("/admin/index")
    public ModelAndView index(ModelMap mmap) {

        // 取身份信息
        AdminUser user = getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", funBootConfig.getCopyrightYear());
        mmap.put("demoEnabled", funBootConfig.isDemoEnabled());

        return new ModelAndView(Constants.view("layout"));
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
     */
    @GetMapping("admin/system/main")
    public ModelAndView main(ModelMap mmap) {
        mmap.put("version", funBootConfig.getVersion());
        mmap.put("name", funBootConfig.getName());
        return new ModelAndView(Constants.view("index")) ;
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

    @GetMapping("/test")
    public ModelAndView testLayout(){
        return new ModelAndView(Constants.view("index"));
    }
}
