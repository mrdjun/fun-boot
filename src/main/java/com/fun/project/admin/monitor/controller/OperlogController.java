package com.fun.project.admin.monitor.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.DataSource;
import com.fun.framework.annotation.Log;
import com.fun.framework.annotation.enums.DataSourceType;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.monitor.entity.OperLog;
import com.fun.project.admin.monitor.service.IOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fun.common.result.CommonResult.success;


/**
 * @author DJun
 * @date 2019/11/03 21:42
 */
@Api(tags = "操作日志")
@Controller
@RequestMapping("/admin/monitor/operlog")
public class OperlogController extends AdminBaseController {

    @Autowired
    private IOperLogService operLogService;

    @RequiresPermissions("monitor:operlog:view")
    @GetMapping()
    public String operlog() {
        return view("monitor/log/operlog");
    }

    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<OperLog>> operList(OperLog operLog) {
        startPage();
        List<OperLog> list = operLogService.selectOperLogList(operLog);
        return success(CommonPage.restPage(list));
    }

    @RequiresPermissions("monitor:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") Long operId, ModelMap mmap) {
        mmap.put("operLog", operLogService.selectOperLogById(operId));
        return view("monitor/log/detail");
    }

    @ApiOperation("删除操作日志")
    @Log("删除操作日志")
    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/remove")
    public CommonResult remove(String ids) {
        return success(operLogService.deleteOperLogByIds(ids));
    }

    @RequiresPermissions("monitor:operlog:remove")
    @ApiOperation("清空操作日志")
    @PostMapping("/clean")
    public CommonResult clean() {
        operLogService.cleanOperLog();
        return success(Constants.SUCCESS);
    }
}
