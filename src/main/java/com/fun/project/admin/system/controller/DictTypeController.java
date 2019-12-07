package com.fun.project.admin.system.controller;

import com.fun.common.constant.Constants;
import com.fun.common.utils.poi.ExcelUtil;
import com.fun.framework.web.controller.AdminBaseController;
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
import com.fun.project.admin.system.entity.dict.DictType;
import com.fun.common.pagehelper.CommonPage;

import java.util.List;

import static com.fun.common.result.CommonResult.success;

/**
 * @author DJun
 * @date 2019/10/30
 */
@Api(tags = {"admin字典类型"})
@Controller
@RequestMapping("/admin/system/dict")
public class DictTypeController extends AdminBaseController {
    private String prefix = "system/dict/type/";

    @Autowired
    private IDictTypeService dictTypeService;

    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictType() {
        return view(prefix + "type");
    }

    @ApiOperation(value = "分页查询DictType列表")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult selectDictTypeList(DictType dictType) {
        startPage();
        List<DictType> dictTypes = dictTypeService.selectDictTypeList(dictType);
        return success(CommonPage.restPage(dictTypes));
    }

    @Log("导出DictType列表" )
    @ApiOperation(value = "导出DictType列表" )
    @RequiresPermissions("system:dict:export" )
    @PostMapping("/export" )
    @ResponseBody
    public CommonResult export(DictType dictType) {
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<DictType> util = new ExcelUtil<>(DictType.class);
        return util.exportExcel(list, "字典类型" );
    }

    /**
     * 新增字典类型
     */
    @GetMapping("/add")
    public String add() {
        return view(prefix + "/add");
    }

    @ApiOperation(value = "新增DictType")
    @Log("新增DictType")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult insertDictType(DictType dictType) {
        if (Constants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dictType))) {
            return CommonResult.failed("新增字典'" + dictType.getDictName() + "'失败，字典类型已存在");
        }
        return success(dictTypeService.insertDictType(dictType));
    }

    /**
     * 修改字典类型
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
        return view(prefix + "edit");
    }

    @RequiresPermissions("system:dict:edit")
    @ApiOperation(value = "修改DictType信息")
    @Log("修改DictType信息")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult updateDictType(DictType dictType) {
        return success(dictTypeService.updateDictType(dictType));
    }


    @ApiOperation(value = "通过id批量删除DictType")
    @Log("通过ids批量删除DictType")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult deleteDictTypeByIds(String ids) {
        return success(dictTypeService.deleteDictTypeByIds(ids));
    }

    /**
     * 查询字典详细
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap) {
        mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return view("system/dict/data/data");
    }

    /**
     * 选择字典树
     */
    @GetMapping("/selectDictTree/{columnId}/{dictType}")
    public String selectDeptTree(@PathVariable("columnId") Long columnId,
                                 @PathVariable("dictType") String dictType,
                                 ModelMap mmap) {
        mmap.put("columnId", columnId);
        mmap.put("dict", dictTypeService.selectDictTypeByType(dictType));
        return view(prefix + "tree");
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
