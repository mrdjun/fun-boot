package com.fun.project.admin.tool.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.text.Convert;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.tool.entity.GenTable;
import com.fun.project.admin.tool.entity.GenTableColumn;
import com.fun.project.admin.tool.service.IGenTableColumnService;
import com.fun.project.admin.tool.service.IGenTableService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.fun.common.result.CommonResult.success;

/**
 * 代码生成
 *
 * @author DJun
 */
@Controller
@RequestMapping("/admin/tool/gen")
public class GenController extends AdminBaseController {
    private String prefix = "tool/gen";

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;

    @RequiresPermissions("tool:gen:view")
    @GetMapping()
    public String gen() {
        return view(prefix + "/gen");
    }

    /**
     * 查询代码生成列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult genList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return success(CommonPage.restPage(list));
    }

    /**
     * 查询数据库列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/db/list")
    @ResponseBody
    public CommonResult dataList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return success(CommonPage.restPage(list));
    }

    /**
     * 查询数据表字段列表
     */
    @RequiresPermissions("tool:gen:list")
    @PostMapping("/column/list")
    @ResponseBody
    public CommonResult columnList(GenTableColumn genTableColumn) {
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(genTableColumn);
        return success(CommonPage.restPage(list));
    }

    /**
     * 导入表结构
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/importTable")
    public String importTable() {
        return view(prefix + "/importTable");
    }

    /**
     * 导入表结构（保存）
     */
    @RequiresPermissions("tool:gen:list")
    @Log("代码生成")
    @PostMapping("/importTable")
    @ResponseBody
    public CommonResult importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return success(Constants.SUCCESS);
    }

    /**
     * 修改代码生成业务
     */
    @GetMapping("/edit/{tableId}")
    public String edit(@PathVariable("tableId") Long tableId, ModelMap mmap) {
        GenTable table = genTableService.selectGenTableById(tableId);
        mmap.put("table", table);
        return view(prefix + "/edit");
    }

    /**
     * 修改保存代码生成业务
     */
    @RequiresPermissions("tool:gen:edit")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(@Validated GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return success(Constants.SUCCESS);
    }

    @RequiresPermissions("tool:gen:remove")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult remove(String ids) {
        genTableService.deleteGenTableByIds(ids);
        return success(Constants.SUCCESS);
    }

    /**
     * 预览代码
     */
    @RequiresPermissions("tool:gen:preview")
    @GetMapping("/preview/{tableId}")
    @ResponseBody
    public CommonResult preview(@PathVariable("tableId") Long tableId) throws IOException {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return success(dataMap);

    }

    /**
     * 生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @GetMapping("/genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.generatorCode(tableName);
        genCode(response, data);
    }

    /**
     * 批量生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @GetMapping("/batchGenCode")
    @ResponseBody
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.generatorCode(tableNames);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"fun-boot.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}