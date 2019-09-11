package com.fun.project.monitor.controller;

import com.fun.common.exception.RedisConnectException;
import com.fun.common.result.CommonResult;
import com.fun.framework.annotaion.Log;
import com.fun.framework.annotaion.NeedLoginToken;
import com.fun.framework.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/admin/redis")
public class RedisController {

    private static final String INTEGER_PREFIX = "(integer) ";

    @Autowired
    private IRedisService redisService;

    @NeedLoginToken
    @Log("执行 Redis keysSize 命令")
    @GetMapping("/keysSize")
    public Map<String, Object> getKeysSize() throws RedisConnectException {
        return redisService.getKeysSize();
    }

    @NeedLoginToken
    @Log("执行 Redis memoryInfo 命令")
    @GetMapping("/memoryInfo")
    public Map<String, Object> getMemoryInfo() throws RedisConnectException {
        return redisService.getMemoryInfo();
    }

    @NeedLoginToken
    @Log("执行 Redis keys 命令")
    @GetMapping("/keys")
    public CommonResult keys(String arg) throws RedisConnectException {
        Set<String> set = this.redisService.getKeys(arg);
        return CommonResult.success(set);
    }

    @NeedLoginToken
    @Log("执行Redis get命令")
    @GetMapping("/get")
    public CommonResult get(String arg) throws RedisConnectException {
        String result = this.redisService.get(arg);
        return CommonResult.success(result == null ? "" : result);
    }

    @NeedLoginToken
    @Log("执行Redis set命令")
    @GetMapping("/set")
    public CommonResult set(String arg) throws RedisConnectException {
        String[] args = arg.split(",");
        if (args.length == 1)
            return CommonResult.failed("(error) ERR wrong number of arguments for 'set' command");
        else if (args.length != 2)
            return CommonResult.failed("(error) ERR syntax error");
        String result = this.redisService.set(args[0], args[1]);
        return CommonResult.success(result);
    }

    @NeedLoginToken
    @Log("执行Redis del命令")
    @GetMapping("/del")
    public CommonResult del(String arg) throws RedisConnectException {
        String[] args = arg.split(",");
        Long result = this.redisService.del(args);
        return CommonResult.success(INTEGER_PREFIX + result);
    }

    @NeedLoginToken
    @Log("执行Redis exists命令")
    @GetMapping("/exists")
    public CommonResult exists(String arg) throws RedisConnectException {
        int count = 0;
        String[] arr = arg.split(",");
        for (String key : arr) {
            if (this.redisService.exists(key))
                count++;
        }
        return CommonResult.success(INTEGER_PREFIX + count);
    }

    @NeedLoginToken
    @Log("执行Redis pttl命令")
    @GetMapping("/pttl")
    public CommonResult pttl(String arg) throws RedisConnectException {
        return CommonResult.success(INTEGER_PREFIX + this.redisService.pttl(arg));
    }

    @NeedLoginToken
    @Log("执行Redis pexpire命令")
    @GetMapping("/pexpire")
    public CommonResult pexpire(String arg) throws RedisConnectException {
        String[] arr = arg.split(",");
        if (arr.length != 2 || !isValidLong(arr[1])) {
            return CommonResult.failed("(error) ERR wrong number of arguments for 'pexpire' command");
        }
        return CommonResult.success(INTEGER_PREFIX + this.redisService.pexpire(arr[0], Long.valueOf(arr[1])));
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
