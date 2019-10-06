package com.fun.project.admin.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.project.admin.service.${classInfo.className}Service;

import javax.annotation.Resource;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author u-fun
 * @date ${.now?string('yyyy/MM/dd')}
 */
@Api("${classInfo.classComment}")
@Controller
@RequestMapping("/app/${classInfo.className?uncap_first}")
public class ${classInfo.className}Controller {

    @Autowired
    private ${classInfo.className}Service ${classInfo.className?uncap_first}Service;


    @ApiOperation(" 查询${classInfo.className}列表")
    @PostMapping("/select${classInfo.className}List")
    @ResponseBody
    public CommonResult select${classInfo.className}List(${classInfo.className} ${classInfo.className?uncap_first},
                                        @RequestParam(value = "pageNum",defaultValue = "1",required = false) int pageNum,
                                        @RequestParam(value = "pageNum",defaultValue = "10",required = false)int pageSize){
        return CommonResult.success(CommonPage.restPage(${classInfo.className?uncap_first}Service.select${classInfo.className}List(${classInfo.className?uncap_first},pageNum,pageSize)));
    }


    @ApiOperation("通过Id查询${classInfo.className}")
    @Log("通过Id查询${classInfo.className}")
    @GetMapping("/select${classInfo.className}ById/${r"{"}${classInfo.conversionPrimaryKey}${r"}"}")
    @ResponseBody
    public CommonResult select${classInfo.className}ById(@PathVariable("${classInfo.conversionPrimaryKey}") Long ${classInfo.conversionPrimaryKey}){
        return CommonResult.success(${classInfo.className?uncap_first}Service.select${classInfo.className}ById(${classInfo.conversionPrimaryKey}));
    }

    @ApiOperation("新增${classInfo.className}")
    @Log("新增${classInfo.className}")
    @PostMapping("/insert${classInfo.className}")
    @ResponseBody
    public CommonResult insert${classInfo.className}(${classInfo.className} ${classInfo.className?uncap_first}){
        return CommonResult.success(${classInfo.className?uncap_first}Service.insert${classInfo.className}(${classInfo.className?uncap_first}));
    }


    @ApiOperation("修改${classInfo.className}信息")
    @Log("修改${classInfo.className}信息")
    @PostMapping("/update${classInfo.className}")
    @ResponseBody
    public CommonResult update${classInfo.className}(${classInfo.className} ${classInfo.className?uncap_first}){
        return CommonResult.success(${classInfo.className?uncap_first}Service.update${classInfo.className}(${classInfo.className?uncap_first}));
    }


    @ApiOperation("通过id删除${classInfo.className}")
    @Log("通过id删除${classInfo.className}")
    @PostMapping("/delete${classInfo.className}ById/${r"{"}${classInfo.conversionPrimaryKey}${r"}"}")
    @ResponseBody
    public CommonResult delete${classInfo.className}ById(@PathVariable("${classInfo.conversionPrimaryKey}") Long ${classInfo.conversionPrimaryKey}){
        return CommonResult.success(${classInfo.className?uncap_first}Service.delete${classInfo.className}ById(${classInfo.conversionPrimaryKey}));
    }

    @ApiOperation("通过id批量删除${classInfo.className}")
    @Log("通过id批量删除${classInfo.className}")
    @PostMapping("/deleteList")
    @ResponseBody
    public CommonResult delete${classInfo.className}ByIds(String ${classInfo.conversionPrimaryKey}s){
        return CommonResult.success(${classInfo.className?uncap_first}Service.delete${classInfo.className}ByIds(${classInfo.conversionPrimaryKey}s));
    }
}
