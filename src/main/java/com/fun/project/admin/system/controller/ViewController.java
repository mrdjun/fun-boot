package com.fun.project.admin.system.controller;

import com.fun.framework.config.FunBootConfig;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.system.entity.Menu;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.service.IMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * 公共页面跳转
 *
 * @author DJun
 * @date 2019/9/29
 */
@Controller
public class ViewController extends AdminBaseController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private FunBootConfig funBootConfig;

    /**
     * 定向到首页
     */
    @GetMapping("/")
    public String redirectIndex() {
        return redirect("/admin/index");
    }

    /**
     * 定向到首页
     */
    @GetMapping("/index")
    public String indexRedirectIndex() {
        return redirect("/admin/index");
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

        return new ModelAndView(view("layout"));
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
        return new ModelAndView(view("index"));
    }

    /**
     * ERROR:无权限
     */
    @GetMapping("/403")
    public String unauthorized() {
        return "/error/403";
    }

    /**
     * ERROR:无页面
     */
    @GetMapping("/404")
    public String error404() {
        return "/error/404";
    }

    /**
     * ERROR:服务端错误
     */
    @GetMapping("/500")
    public String error500() {
        return "/error/500";
    }

    /**
     * ERROR:业务错误
     */
    @GetMapping("/business")
    public String errorBusiness() {
        return "/error/business";
    }

    /**
     * 表单生成工具
     */
    @GetMapping("/tool/table")
    public ModelAndView testLayout() {
        return new ModelAndView(view("tool/table/table"));
    }

    /**
     * 丝袜哥
     */
    @RequiresPermissions("tool:swagger:view")
    @GetMapping("/tool/swagger")
    public String swaggerPage() {
        return redirect("/swagger-ui.html");
    }

    /**
     * druid 监控
     */
    @RequiresPermissions("monitor:data:view")
    @GetMapping("/admin/monitor/data")
    public String druidPage() {
        return redirect("/druid/index");
    }
}
