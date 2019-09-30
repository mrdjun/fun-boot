package com.fun.test;

import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.service.IMenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DJun
 * @date 2019/9/30
 */


public class TestController {
    @Autowired
    private IMenuService menuService;

    @Test
    public void TestMenuList() {
        AdminUser adminUser = new AdminUser();
        adminUser.setUserId(1L);
        System.out.println(menuService.selectMenusByUser(adminUser));
    }

}
