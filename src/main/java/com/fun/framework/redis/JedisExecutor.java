package com.fun.framework.redis;

import com.fun.common.exception.RedisConnectException;


/**
 * @author DJun
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
