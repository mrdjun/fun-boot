package com.fun.project.admin.monitor.controller;

import com.fun.common.constant.Constants;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.monitor.entity.JobLog;
import com.fun.project.admin.monitor.service.IJobLogService;
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
 * 任务调度日志
 *
 * @author DJun
 * @date 2019/11/5
 */
@Api(tags = {"admin任务调度日志"})
@Controller
@RequestMapping("/admin/monitor/jobLog")
public class JobLogController extends AdminBaseController {
    private String prefix = "monitor/job";

    @Autowired
    private IJobLogService jobLogService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping()
    public String jobLog() {
        return view(prefix + "/jobLog");
    }

    @ApiOperation("获取任务列表")
    @RequiresPermissions("monitor:job:list")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage> selectJobList(JobLog job) {
        startPage();
        List<JobLog> list = jobLogService.selectJobLogList(job);
        return success(CommonPage.restPage(list));
    }

    @ApiOperation("删除任务日志")
    @Log("删除任务日志")
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult remove(String ids) {
        return success(jobLogService.deleteJobLogByIds(ids));
    }

    @RequiresPermissions("monitor:job:detail")
    @GetMapping("/detail/{jobLogId}")
    public String detail(@PathVariable("jobLogId") Long jobLogId, ModelMap mmap) {
        mmap.put("name", "jobLog");
        mmap.put("jobLog", jobLogService.selectJobLogById(jobLogId));
        return view(prefix + "/detail");
    }

    @ApiOperation("清空任务日志")
    @Log("清空任务日志")
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/clean")
    @ResponseBody
    public CommonResult clean() {
        jobLogService.cleanJobLog();
        return success(Constants.SUCCESS);
    }

}
