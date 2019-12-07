package com.fun.project.admin.system.controller;

import com.fun.common.utils.poi.ExcelUtil;
import com.fun.framework.web.controller.AdminBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.project.admin.system.service.IDictDataService;
import com.fun.project.admin.system.entity.dict.DictData;
import com.fun.common.pagehelper.CommonPage;

import java.util.List;

import static com.fun.common.result.CommonResult.success;

/**
 * @author DJun
 * @date 2019/10/30
 */
@Api(tags = {"admin字典数据"})
@Controller
@RequestMapping("/admin/system/dict/data" )
public class DictDataController extends AdminBaseController {
    private String prefix = "system/dict/data/" ;

    @Autowired
    private IDictDataService dictDataService;


    @ApiOperation(value = "分页查询DictData列表" )
    @PostMapping("/list" )
    @ResponseBody
    public CommonResult selectDictDataList(DictData dictData) {
        startPage();
        List<DictData> dictDataList = dictDataService.selectDictDataList(dictData);
        return success(CommonPage.restPage(dictDataList));
    }

    @Log("导出DictData列表" )
    @ApiOperation(value = "导出DictData列表" )
    @RequiresPermissions("system:dict:export" )
    @PostMapping("/export" )
    @ResponseBody
    public CommonResult export(DictData dictData) {
        List<DictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<DictData> util = new ExcelUtil<>(DictData.class);
        return util.exportExcel(list, "字典数据" );
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictCode}" )
    public String selectDictDataById(@PathVariable("dictCode" ) Long dictCode, ModelMap mmap) {
        mmap.put("dict" , dictDataService.selectDictDataById(dictCode));
        return view(prefix + "/edit" );
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add/{dictType}" )
    public String add(@PathVariable("dictType" ) String dictType, ModelMap mmap) {
        mmap.put("dictType" , dictType);
        return view(prefix + "/add" );
    }

    @ApiOperation(value = "新增DictData" )
    @RequiresPermissions("system:dict:add" )
    @Log("新增DictData" )
    @PostMapping("/add" )
    @ResponseBody
    public CommonResult insertDictData(@Validated DictData dictData) {
        return success(dictDataService.insertDictData(dictData));
    }


    @ApiOperation(value = "修改DictData信息" )
    @RequiresPermissions("system:dict:edit" )
    @Log("修改DictData信息" )
    @PostMapping("/edit" )
    @ResponseBody
    public CommonResult updateDictData(@Validated DictData dictData) {
        return success(dictDataService.updateDictData(dictData));
    }

    @ApiOperation(value = "通过id批量删除DictData" )
    @RequiresPermissions("system:dict:remove" )
    @Log("通过id批量删除DictData" )
    @PostMapping("/remove" )
    @ResponseBody
    public CommonResult deleteDictDataByIds(String ids) {
        return success(dictDataService.deleteDictDataByIds(ids));
    }
}
