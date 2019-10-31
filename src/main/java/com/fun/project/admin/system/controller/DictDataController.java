package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.framework.web.controller.BaseController;
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
import com.fun.project.admin.system.entity.DictData;
import com.fun.common.pagehelper.CommonPage;

import java.util.List;

/**
 * @author u-fun
 * @date 2019/10/30
 */
@Api("字典数据表")
@Controller
@RequestMapping("/admin/system/dict/data")
public class DictDataController extends BaseController {
    private String prefix = "system/dict/data/";

    @Autowired
    private IDictDataService dictDataService;


    @ApiOperation(value = "分页查询DictData列表")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult selectDictDataList(DictData dictData) {
        startPage();
        List<DictData> dictDataList = dictDataService.selectDictDataList(dictData);
        return CommonResult.success(CommonPage.restPage(dictDataList));
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictCode}")
    public String selectDictDataById(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
        mmap.put("dict", dictDataService.selectDictDataById(dictCode));
        return view(prefix + "/edit");
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add/{dictType}")
    public String add(@PathVariable("dictType") String dictType, ModelMap mmap) {
        mmap.put("dictType", dictType);
        return view(prefix + "/add");
    }

    @ApiOperation(value = "新增DictData")
    @RequiresPermissions("system:dict:add")
    @Log("新增DictData")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult insertDictData(@Validated DictData dictData) {
        return CommonResult.success(dictDataService.insertDictData(dictData));
    }


    @ApiOperation(value = "修改DictData信息")
    @RequiresPermissions("system:dict:edit")
    @Log("修改DictData信息")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult updateDictData(@Validated DictData dictData) {
        return CommonResult.success(dictDataService.updateDictData(dictData));
    }

    @ApiOperation(value = "通过id批量删除DictData")
    @RequiresPermissions("system:dict:remove")
    @Log("通过id批量删除DictData")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult deleteDictDataByIds(String ids) {
        return CommonResult.success(dictDataService.deleteDictDataByIds(ids));
    }
}