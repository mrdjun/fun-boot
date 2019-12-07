package com.fun.project.admin.monitor.controller;

import com.fun.project.admin.monitor.entity.server.Server;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 服务器监控
 *
 * @author DJun
 * @date 2019/11/4
 */
@Controller
public class ServerController {

    @RequiresPermissions("monitor:server:view")
    @GetMapping("/admin/monitor/server")
    public String server(ModelMap mmap) throws Exception {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        return "fun/views/monitor/server/server";
    }

}
