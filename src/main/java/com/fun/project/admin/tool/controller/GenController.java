package com.fun.project.admin.tool.controller;

import com.fun.common.constant.Constants;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.gen.CodeGeneratorTool;
import com.fun.common.utils.gen.FreemarkerTool;
import com.fun.project.admin.tool.entity.GenTable;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DJun
 * @date 2019/10/6
 */
@Controller
@Slf4j
@RequestMapping("/tool/gen")
public class GenController {

    @Autowired
    private FreemarkerTool freemarkerTool;

    @GetMapping("")
    public String index() {
        return Constants.view("/tool/gen/index");
    }

    @PostMapping("/codeGenerate")
    @ResponseBody
    public CommonResult codeGenerate(String tableSql) {

        if (StringUtils.isBlank(tableSql)) {
            return CommonResult.failed("表结构信息不可为空");
        }

        try {
            // parse table
            GenTable genTable = CodeGeneratorTool.processTableIntoClassInfo(tableSql);

            // code genarete
            Map<String, Object> params = new HashMap<>();
            params.put("classInfo", genTable);

            // result
            Map<String, String> result = new HashMap<>();

            result.put("controller_code", freemarkerTool.processString(Constants.VIEW_PREFIX + "tool/gen/xxl-code-generator/controller.ftl", params));
            result.put("service_code", freemarkerTool.processString(Constants.VIEW_PREFIX + "tool/gen/xxl-code-generator/service.ftl", params));
            result.put("service_impl_code", freemarkerTool.processString(Constants.VIEW_PREFIX + "tool/gen/xxl-code-generator/service_impl.ftl", params));
            result.put("dao_code", freemarkerTool.processString(Constants.VIEW_PREFIX + "tool/gen/xxl-code-generator/mapper.ftl", params));
            result.put("mybatis_code", freemarkerTool.processString(Constants.VIEW_PREFIX + "tool/gen/xxl-code-generator/mybatis.ftl", params));
            result.put("model_code", freemarkerTool.processString(Constants.VIEW_PREFIX + "tool/gen/xxl-code-generator/entity.ftl", params));

            // 计算,生成代码行数
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
