package com.fun.project.admin.monitor.controller;

import com.fun.common.exception.RedisConnectException;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotation.Log;
import com.fun.framework.redis.IRedisService;
import com.fun.framework.redis.RedisInfo;
import com.fun.framework.web.controller.AdminBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.fun.common.result.CommonResult.failed;
import static com.fun.common.result.CommonResult.success;


/**
 * Redis
 *
 * @author DJun
 */
@Api(tags = {"Redis接口"})
@Controller
@RequestMapping("/admin/monitor/redis")
@Slf4j
public class RedisController extends AdminBaseController {

    private static final String INTEGER_PREFIX = "(integer) ";

    @Autowired
    private IRedisService redisService;

    @RequiresPermissions("monitor:redis:view")
    @GetMapping()
    public String redisPage(ModelMap mmap) {
        List<RedisInfo> infoList = null;
        try {
            infoList = this.redisService.getRedisInfo();
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }
        mmap.put("infoList", infoList);
        return view("monitor/redis/redis");
    }

    @ApiOperation("执行 Redis keysSize 命令")
    @RequiresPermissions("monitor:redis:edit")
    @Log("执行 Redis keysSize 命令")
    @GetMapping("/keysSize")
    @ResponseBody
    public Map<String, Object> getKeysSize() {
        try {
            return redisService.getKeysSize();
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }
        return null;
    }

    @ApiOperation("执行 Redis memoryInfo 命令")
    @RequiresPermissions("monitor:redis:edit")
    @Log("执行 Redis memoryInfo 命令")
    @GetMapping("/memoryInfo")
    @ResponseBody
    public Map<String, Object> getMemoryInfo() {
        try {
            return redisService.getMemoryInfo();
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }
        return null;
    }

    @ApiOperation("执行 Redis keys 命令")
    @RequiresPermissions("monitor:redis:edit")
    @Log("执行 Redis keys 命令")
    @GetMapping("/keys")
    @ResponseBody
    public CommonResult keys(String arg) {

        Set<String> set = null;
        try {
            set = this.redisService.getKeys(arg);
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }

        return success(set);
    }

    @ApiOperation("执行Redis get命令")
    @RequiresPermissions("monitor:redis:edit")
    @Log("执行Redis get命令")
    @GetMapping("/get")
    @ResponseBody
    public CommonResult get(String arg) {

        String result = null;
        try {
            result = this.redisService.get(arg);
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }
        return success(result == null ? "" : result);
    }

    @ApiOperation("执行Redis set命令")
    @RequiresPermissions("monitor:redis:edit")
    @Log("执行Redis set命令")
    @GetMapping("/set")
    @ResponseBody
    public CommonResult set(String arg) {
        String[] args = arg.split(",");
        if (args.length == 1) {
            return failed("(error) ERR wrong number of arguments for 'set' command");
        } else if (args.length != 2) {
            return failed("(error) ERR syntax error");
        }
        String result = null;
        try {
            result = this.redisService.set(args[0], args[1]);
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }
        return success(result);
    }

    @ApiOperation("执行Redis del命令")
    @RequiresPermissions("monitor:redis:remove")
    @Log("执行Redis del命令")
    @GetMapping("/del")
    @ResponseBody
    public CommonResult del(String arg) {
        String[] args = arg.split(",");
        Long result = null;
        try {
            result = this.redisService.del(args);
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }
        return success(INTEGER_PREFIX + result);
    }

    @ApiOperation("执行Redis exists命令")
    @RequiresPermissions("monitor:redis:edit")
    @Log("执行Redis exists命令")
    @GetMapping("/exists")
    @ResponseBody
    public CommonResult exists(String arg) {
        int count = 0;
        String[] arr = arg.split(",");
        for (String key : arr) {
            try {
                if (this.redisService.exists(key)) {
                    count++;
                }
            } catch (RedisConnectException e) {
                log.error("Redis 连接异常");
            }
        }
        return success(INTEGER_PREFIX + count);
    }

    @ApiOperation("执行Redis pttl命令")
    @RequiresPermissions("monitor:redis:edit")
    @Log("执行Redis pttl命令")
    @GetMapping("/pttl")
    @ResponseBody
    public CommonResult pttl(String arg) {
        try {
            return success(INTEGER_PREFIX + this.redisService.pttl(arg));
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }
        return failed();
    }

    @ApiOperation("执行Redis pexpire命令")
    @RequiresPermissions("monitor:redis:edit")
    @Log("执行Redis pexpire命令")
    @GetMapping("/pexpire")
    @ResponseBody
    public CommonResult pexpire(String arg) {
        String[] arr = arg.split(",");
        if (arr.length != 2 || !isValidLong(arr[1])) {
            return failed("(error) ERR wrong number of arguments for 'pexpire' command");
        }
        try {
            return success(INTEGER_PREFIX + this.redisService.pexpire(arr[0], Long.valueOf(arr[1])));
        } catch (RedisConnectException e) {
            log.error("Redis 连接异常");
        }
        return failed();
    }

    private static boolean isValidLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
