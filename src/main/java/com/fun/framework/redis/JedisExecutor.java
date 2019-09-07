package com.fun.framework.redis;

import com.fun.framework.exception.RedisConnectException;

/**
 * created by DJun on 2019/9/7 17:09
 * desc:
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
