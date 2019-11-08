package com.fun.project.admin.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import com.fun.common.result.CommonResult;
import static com.fun.common.result.CommonResult.failed;
import static com.fun.common.result.CommonResult.success;
import com.fun.framework.web.controller.BaseController;
import com.fun.framework.annotation.Log;
import com.fun.project.admin.system.service.I${classInfo.className}Service;
import com.fun.project.admin.system.entity.${classInfo.className};
import com.fun.common.pagehelper.CommonPage;

import java.util.List;

/**
 * @author u-fun
 * @date ${.now?string('yyyy/MM/dd')}
 */
@Api("${classInfo.classComment}")
@Controller
@RequestMapping("/admin/system/${classInfo.className?uncap_first}")
public class ${classInfo.className}Controller extends BaseController{
    private String prefix = "system/${classInfo.className?uncap_first}";
    @Autowired
    private I${classInfo.className}Service ${classInfo.className?uncap_first}Service;

    /** ${classInfo.className}页面 */
    @RequiresPermissions("system:${classInfo.className?uncap_first}:view")
    @GetMapping()
    public String ${classInfo.className?uncap_first}(){
        return view(prefix + "/${classInfo.className?uncap_first}");
    }

    @RequiresPermissions("system:${classInfo.className?uncap_first}:list")
    @ApiOperation(value = "分页查询${classInfo.className}列表")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult select${classInfo.className}List(${classInfo.className} ${classInfo.className?uncap_first}){
        startPage();
        List<${classInfo.className}> ${classInfo.className?uncap_first}List = ${classInfo.className?uncap_first}Service.select${classInfo.className}List(${classInfo.className?uncap_first});
        return success(CommonPage.restPage(${classInfo.className?uncap_first}List));
    }

    /** 新增${classInfo.className}页面 */
    @GetMapping("/add")
    public String insert${classInfo.className}Page() {
        return view(prefix + "/add");
    }

    @RequiresPermissions("system:${classInfo.className?uncap_first}:add")
    @ApiOperation(value = "新增${classInfo.className}")
    // 如果是App端，请使用LoginType枚举表明app，默认为admin
    @Log("新增${classInfo.className}")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult insert${classInfo.className}(${classInfo.className} ${classInfo.className?uncap_first}){
        return success(${classInfo.className?uncap_first}Service.insert${classInfo.className}(${classInfo.className?uncap_first}));
    }

    /** 修改${classInfo.className}页面 */
    @GetMapping("/edit/{${classInfo.conversionPrimaryKey}}")
    public String edit${classInfo.className}Page(@PathVariable("${classInfo.conversionPrimaryKey}") Long ${classInfo.conversionPrimaryKey}, ModelMap mmap){
        mmap.put("${classInfo.className?uncap_first}",${classInfo.className?uncap_first}Service.select${classInfo.className}ById(${classInfo.conversionPrimaryKey}));
        return view(prefix + "/edit");
    }

    @RequiresPermissions("system:${classInfo.className?uncap_first}:edit")
    @ApiOperation(value = "修改${classInfo.className}信息")
    @Log("修改${classInfo.className}信息")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult update${classInfo.className}(${classInfo.className} ${classInfo.className?uncap_first}){
        return success(${classInfo.className?uncap_first}Service.update${classInfo.className}(${classInfo.className?uncap_first}));
    }

    @RequiresPermissions("system:${classInfo.className?uncap_first}:remove")
    @ApiOperation(value = "删除${classInfo.className}")
    @Log("删除${classInfo.className}")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult delete${classInfo.className}ByIds(String ids){
        return success(${classInfo.className?uncap_first}Service.delete${classInfo.className}ByIds(ids));
    }
}
