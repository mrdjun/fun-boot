package com.fun.project.admin.monitor.controller;

import com.fun.common.constant.Constants;
import com.fun.common.exception.TaskException;
import com.fun.common.pagehelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.framework.web.controller.AdminBaseController;
import com.fun.project.admin.monitor.entity.Job;
import com.fun.project.admin.monitor.service.IJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fun.common.result.CommonResult.failed;
import static com.fun.common.result.CommonResult.success;

/**
 * 任务调度
 *
 * @author DJun
 * @date 2019/11/5
 */
@Api(tags = {"admin任务调度信息操作处理"})
@Controller
@RequestMapping("/admin/monitor/job")
public class JobController extends AdminBaseController {
    private Logger logger = LoggerFactory.getLogger(JobController.class);
    private String prefix = "monitor/job";

    @Autowired
    private IJobService jobService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping()
    public String job() {
        return view(prefix + "/job");
    }

    @ApiOperation("获取任务列表")
    @RequiresPermissions("monitor:job:list")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage> selectJobList(Job job) {
        startPage();
        List<Job> list = jobService.selectJobList(job);
        return success(CommonPage.restPage(list));
    }

    @ApiOperation("删除定时任务")
    @Log("删除定时任务")
    @RequiresPermissions("monitor:job:remove")
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult deleteJobByIds(String ids) throws SchedulerException {
        jobService.deleteJobByIds(ids);
        return success(Constants.SUCCESS);
    }

    @RequiresPermissions("monitor:job:detail")
    @GetMapping("/detail/{jobId}")
    public String jobDetail(@PathVariable("jobId") Long jobId, ModelMap mmap) {
        mmap.put("name", "job");
        mmap.put("job", jobService.selectJobById(jobId));
        return view(prefix + "/detail");
    }

    @ApiOperation("修改任务状态")
    @Log("修改任务状态")
    @RequiresPermissions("monitor:job:changeStatus")
    @PostMapping("/changeStatus")
    @ResponseBody
    public CommonResult changeJobStatus(Job job) throws SchedulerException {
        Job newJob = jobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return success(jobService.changeStatus(newJob));
    }

    @ApiOperation("执行一次任务")
    @Log("任务立即执行一次")
    @RequiresPermissions("monitor:job:changeStatus")
    @PostMapping("/run")
    @ResponseBody
    public CommonResult run(Job job) throws SchedulerException {
        jobService.run(job);
        return success(Constants.SUCCESS);
    }

    /**
     * 新增任务
     */
    @GetMapping("/add")
    public String insertJob() {
        return view(prefix + "/add");
    }

    @ApiOperation("新增任务")
    @Log("新增任务")
    @RequiresPermissions("monitor:job:add")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult insertJobSave(@Validated Job job) {
        int res = 0;
        try {
            res = jobService.insertJob(job);
        } catch (SchedulerException | TaskException e) {
            logger.error(e.getMessage());
        }
        if (res == 0) {
            return failed();
        }
        return success(res);
    }

    /**
     * 修改任务调度
     */
    @GetMapping("/edit/{jobId}")
    public String editJob(@PathVariable("jobId") Long jobId, ModelMap mmap) {
        mmap.put("job", jobService.selectJobById(jobId));
        return view(prefix + "/edit");
    }

    @ApiOperation("修改任务")
    @Log("修改任务")
    @RequiresPermissions("monitor:job:edit")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult editSave(@Validated Job job) {
        int res = 0;
        try {
            res = jobService.updateJob(job);
        } catch (SchedulerException | TaskException e) {
            logger.error(e.getMessage());
        }
        if (res == 0) {
            return failed();
        }
        return success(res);
    }

    @ApiOperation("校验cron表达式是否有效")
    @PostMapping("/checkCronExpressionIsValid")
    @ResponseBody
    public boolean checkCronExpressionIsValid(Job job) {
        return jobService.checkCronExpressionIsValid(job.getCronExpression());
    }

}
