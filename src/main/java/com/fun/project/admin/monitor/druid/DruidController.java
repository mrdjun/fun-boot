package com.fun.project.admin.monitor.druid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by DJun on 2019/9/12 17:19
 * desc:
 */
@Controller
@RequestMapping("/admin/monitor/druid")
public class DruidController {
    @GetMapping()
    public String index()
    {
        return "druid";
    }

}
