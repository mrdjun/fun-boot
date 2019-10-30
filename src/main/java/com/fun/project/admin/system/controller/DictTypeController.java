package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.framework.web.entity.Ztree;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.project.admin.system.service.IDictTypeService;
import com.fun.project.admin.system.entity.DictType;
import com.fun.common.pagehelper.CommonPage;

import java.util.List;

/**
 * @author u-fun
 * @date 2019/10/30
 */
@Api("字典类型表")
@Controller
@RequestMapping("/admin/system/dict")
public class DictTypeController {
    private String prefix = "system/dict/type/";

    @Autowired
    private IDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictType() {
        return Constants.view(prefix + "type");
    }

    @ApiOperation(value = "分页查询DictType列表")
    @PostMapping("/selectDictTypeList")
    @ResponseBody
    public CommonResult selectDictTypeList(DictType dictType,
                                           @RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum,
                                           @RequestParam(value = "pageNum", defaultValue = "10", required = false) int pageSize) {
        List<DictType> dictTypes = dictTypeService.selectDictTypeList(dictType, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(dictTypes));
    }

    @ApiOperation(value = "通过Id查询DictType")
    @Log("通过Id查询DictType")
    @GetMapping("/selectDictTypeById/{dictId}")
    @ResponseBody
    public CommonResult selectDictTypeById(@PathVariable("dictId") Long dictId) {
        return CommonResult.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add")
    public String add() {
        return Constants.view(prefix + "add");
    }

    @ApiOperation(value = "新增DictType")
    @Log("新增DictType")
    @PostMapping("/insertDictType")
    @ResponseBody
    public CommonResult insertDictType(DictType dictType) {
        if (Constants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dictType))) {
            return CommonResult.failed("新增字典'" + dictType.getDictName() + "'失败，字典类型已存在");
        }
        return CommonResult.success(dictTypeService.insertDictType(dictType));
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
        return Constants.view(prefix + "edit");
    }

    @RequiresPermissions("system:dict:edit")
    @ApiOperation(value = "修改DictType信息")
    @Log("修改DictType信息")
    @PostMapping("/updateDictType")
    @ResponseBody
    public CommonResult updateDictType(DictType dictType) {
        return CommonResult.success(dictTypeService.updateDictType(dictType));
    }

    @RequiresPermissions("system:dict:remove")
    @ApiOperation(value = "通过id删除DictType")
    @Log("通过id删除DictType")
    @PostMapping("/deleteDictTypeById/{dictId}")
    @ResponseBody
    public CommonResult deleteDictTypeById(@PathVariable("dictId") Long dictId) {
        return CommonResult.success(dictTypeService.deleteDictTypeById(dictId));
    }

    @ApiOperation(value = "通过id批量删除DictType")
    @Log("通过id批量删除DictType")
    @PostMapping("/deleteList")
    @ResponseBody
    public CommonResult deleteDictTypeByIds(String dictIds) {
        return CommonResult.success(dictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 选择字典树
     */
    @GetMapping("/selectDictTree/{columnId}/{dictType}")
    public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType,
                                 ModelMap mmap) {
        mmap.put("columnId", columnId);
        mmap.put("dict", dictTypeService.selectDictTypeByType(dictType));
        return prefix + "/tree";
    }

    /**
     * 校验字典类型
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public String checkDictTypeUnique(DictType dictType) {
        return dictTypeService.checkDictTypeUnique(dictType);
    }

    /**
     * 加载字典列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        return dictTypeService.selectDictTree(new DictType());
    }

}
