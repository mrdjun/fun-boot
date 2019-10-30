package com.fun.project.admin.system.controller;

import org.springframework.stereotype.Controller;
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
@RequestMapping("/admin/dictData")
public class DictDataController {

    @Autowired
    private IDictDataService dictDataService;


    @ApiOperation(value = "分页查询DictData列表")
    @PostMapping("/selectDictDataList")
    @ResponseBody
    public CommonResult selectDictDataList(DictData dictData,
                                           @RequestParam(value = "pageNum",defaultValue = "1",required = false) int pageNum,
                                           @RequestParam(value = "pageNum",defaultValue = "10",required = false)int pageSize){
        List<DictData> dictDataList = dictDataService.selectDictDataList(dictData,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(dictDataList));
    }


    @ApiOperation(value = "通过Id查询DictData")
    @Log("通过Id查询DictData")
    @GetMapping("/selectDictDataById/{dictCode}")
    @ResponseBody
    public CommonResult selectDictDataById(@PathVariable("dictCode") Long dictCode){
        return CommonResult.success(dictDataService.selectDictDataById(dictCode));
    }

    @ApiOperation(value = "新增DictData")
    @Log("新增DictData")
    @PostMapping("/insertDictData")
    @ResponseBody
    public CommonResult insertDictData(DictData dictData){
        return CommonResult.success(dictDataService.insertDictData(dictData));
    }


    @ApiOperation(value = "修改DictData信息")
    @Log("修改DictData信息")
    @PostMapping("/updateDictData")
    @ResponseBody
    public CommonResult updateDictData(DictData dictData){
        return CommonResult.success(dictDataService.updateDictData(dictData));
    }


    @ApiOperation(value = "通过id删除DictData")
    @Log("通过id删除DictData")
    @PostMapping("/deleteDictDataById/{dictCode}")
    @ResponseBody
    public CommonResult deleteDictDataById(@PathVariable("dictCode") Long dictCode){
        return CommonResult.success(dictDataService.deleteDictDataById(dictCode));
    }

    @ApiOperation(value = "通过id批量删除DictData")
    @Log("通过id批量删除DictData")
    @PostMapping("/deleteList")
    @ResponseBody
    public CommonResult deleteDictDataByIds(String dictCodes){
        return CommonResult.success(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
