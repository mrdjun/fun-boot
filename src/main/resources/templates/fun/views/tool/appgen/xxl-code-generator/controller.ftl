package com.fun.project.app.user.controller;

import com.fun.framework.annotation.JwtPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.enums.LoginType;
import com.fun.framework.web.controller.AppBaseController;
import com.fun.framework.annotation.Log;
import com.fun.project.app.business.service.I${classInfo.className}Service;
import com.fun.project.app.business.entity.${classInfo.className};
import com.fun.common.pagehelper.CommonPage;

import static com.fun.common.result.CommonResult.failed;
import static com.fun.common.result.CommonResult.success;

import java.util.List;

/**
 * @author DJun
 * @date ${.now?string('yyyy/MM/dd')}
 */
@Api(tags="${classInfo.classComment}")
@RestController
@RequestMapping("/app/${classInfo.className?uncap_first}")
public class ${classInfo.className}Controller extends AppBaseController{

    @Autowired
    private I${classInfo.className}Service ${classInfo.className?uncap_first}Service;

    @JwtPermission("${classInfo.className?uncap_first}:list")
    @ApiOperation(value = "分页查询${classInfo.classComment}列表")
    @PostMapping("/list")
    public CommonResult select${classInfo.className}List(${classInfo.className} ${classInfo.className?uncap_first}){
        startPage();
        List<${classInfo.className}> ${classInfo.className?uncap_first}List = ${classInfo.className?uncap_first}Service.select${classInfo.className}List(${classInfo.className?uncap_first});
        return success(CommonPage.restPage(${classInfo.className?uncap_first}List));
    }

    @JwtPermission("${classInfo.className?uncap_first}:list")
    @ApiOperation("通过Id查询${classInfo.classComment}")
    @GetMapping("/select${classInfo.className}ById/${r"{"}${classInfo.conversionPrimaryKey}${r"}"}")
    public CommonResult select${classInfo.className}ById(@PathVariable("${classInfo.conversionPrimaryKey}") Long ${classInfo.conversionPrimaryKey}){
        return success(${classInfo.className?uncap_first}Service.select${classInfo.className}ById(${classInfo.conversionPrimaryKey}));
    }

    @JwtPermission("${classInfo.className?uncap_first}:add")
    @ApiOperation(value = "新增${classInfo.classComment}")
    @Log(value="新增${classInfo.classComment}",type=LoginType.App)
    @PostMapping("/add")
    public CommonResult insert${classInfo.className}(${classInfo.className} ${classInfo.className?uncap_first}){
        return success(${classInfo.className?uncap_first}Service.insert${classInfo.className}(${classInfo.className?uncap_first}));
    }

    @JwtPermission("${classInfo.className?uncap_first}:edit")
    @ApiOperation("修改${classInfo.classComment}")
    @PutMapping("/update${classInfo.className}")
    @Log(value="修改${classInfo.classComment}",type=LoginType.App)
    public CommonResult update${classInfo.className}(${classInfo.className} ${classInfo.className?uncap_first}){
        return success(${classInfo.className?uncap_first}Service.update${classInfo.className}(${classInfo.className?uncap_first}));
    }

    @JwtPermission("${classInfo.className?uncap_first}:remove")
    @ApiOperation("通过id删除${classInfo.classComment}")
    @Log(value="删除${classInfo.classComment}",type=LoginType.App)
    @DeleteMapping("/delete${classInfo.className}ById/${r"{"}${classInfo.conversionPrimaryKey}${r"}"}")
    @ResponseBody
    public CommonResult delete${classInfo.className}ById(@PathVariable("${classInfo.conversionPrimaryKey}") Long ${classInfo.conversionPrimaryKey}){
        return success(${classInfo.className?uncap_first}Service.delete${classInfo.className}ById(${classInfo.conversionPrimaryKey}));
    }

    @JwtPermission("${classInfo.className?uncap_first}:remove")
    @ApiOperation(value = "批量删除${classInfo.classComment}",notes="id与id之间用英文逗号分隔，若只有一个参数不必加逗号")
    @Log(value="删除${classInfo.classComment}",type=LoginType.App)
    @DeleteMapping("/delete${classInfo.className}ById/${r"{"}${classInfo.conversionPrimaryKey}${r"}"}s")
    public CommonResult delete${classInfo.className}ByIds(@PathVariable String ${classInfo.conversionPrimaryKey}s){
        return success(${classInfo.className?uncap_first}Service.delete${classInfo.className}ByIds(${classInfo.conversionPrimaryKey}s));
    }

}
