package com.fun.project.monitor.controller;

import com.fun.common.pageHelper.CommonPage;
import com.fun.common.result.CommonResult;
import com.fun.project.monitor.entity.OperLog;
import com.fun.project.monitor.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * created by DJun on 2019/9/10 15:39
 * desc:
 */

@RestController
@RequestMapping("/admin/operlog")
public class OperlogController {

    @Autowired
    private IOperLogService operLogService;

    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<OperLog>> operList(OperLog operLog,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<OperLog> list = operLogService.selectOperLogList(operLog,pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @GetMapping("/detail/{operId}")
    public CommonResult operDetail(@PathVariable("operId") Long operId) {
       return CommonResult.success(operLogService.selectOperLogById(operId));
    }

    /**
     * 批量删除，也可单删
     * @param ids operId
     * @return 删除记录数
     */
    @PostMapping("/remove")
    @ResponseBody
    public CommonResult remove(String ids){
        return CommonResult.success(operLogService.deleteOperLogByIds(ids));
    }

    @PostMapping("/clean")
    @ResponseBody
    public CommonResult clean() {
        operLogService.cleanOperLog();
        return CommonResult.success(null);
    }
}
