package com.fun.project.app.tool.controller;

import com.fun.common.result.CommonResult;
import com.fun.common.utils.app.gen.CodeGeneratorTool;
import com.fun.common.utils.app.gen.FreemarkerTool;
import com.fun.project.app.tool.entity.AppGenTable;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.fun.framework.web.controller.AdminBaseController.view;

/**
 * @author DJun
 * @date 2019/10/6
 */
@Controller
@Slf4j
@RequestMapping("/admin/tool/appgen")
public class AppGenController {

    @Autowired
    private FreemarkerTool freemarkerTool;

    @RequiresPermissions("tool:gen:view")
    @GetMapping("")
    public String index() {
        return view("tool/appgen/index");
    }

    @PostMapping("/codeGenerate")
    @ResponseBody
    public CommonResult codeGenerate(String tableSql) {

        if (StringUtils.isBlank(tableSql)) {
            return CommonResult.failed("表结构信息不可为空");
        }

        try {
            // parse table
            AppGenTable appGenTable = CodeGeneratorTool.processTableIntoClassInfo(tableSql);

            // code genarete
            Map<String, Object> params = new HashMap<>();
            params.put("classInfo", appGenTable);

            // result
            Map<String, String> result = new HashMap<>();

            result.put("controller_code",   freemarkerTool.processString(view("tool/appgen/xxl-code-generator/controller.ftl"),    params));
            result.put("service_code",      freemarkerTool.processString(view("tool/appgen/xxl-code-generator/service.ftl"),       params));
            result.put("service_impl_code", freemarkerTool.processString(view("tool/appgen/xxl-code-generator/service_impl.ftl"),  params));
            result.put("dao_code",          freemarkerTool.processString(view("tool/appgen/xxl-code-generator/mapper.ftl"),        params));
            result.put("mybatis_code",      freemarkerTool.processString(view("tool/appgen/xxl-code-generator/mybatis.ftl"),       params));
            result.put("model_code",        freemarkerTool.processString(view("tool/appgen/xxl-code-generator/entity.ftl"),        params));

            // 计算生成代码行数
            int lineNum = 0;
            for (Map.Entry<String, String> item : result.entrySet()) {
                if (item.getValue() != null) {
                    lineNum += StringUtils.countMatches(item.getValue(), "\n");
                }
            }
            log.info("生成代码行数：{}", lineNum);
            return CommonResult.success(result);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage(), e);
            return CommonResult.failed("表结构解析失败");
        }
    }

}
