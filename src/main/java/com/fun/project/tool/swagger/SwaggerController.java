package com.fun.project.tool.swagger;

import com.fun.common.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * swagger 接口
 */
@Controller
@RequestMapping("/tool/swagger")
public class SwaggerController {
    @GetMapping()
    public String index() {
        return StringUtils.format("redirect:{}", "swagger-ui.html");
    }
}
