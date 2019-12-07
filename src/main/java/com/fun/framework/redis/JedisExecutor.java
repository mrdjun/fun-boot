package com.fun.framework.redis;

import com.fun.common.exception.RedisConnectException;


/**
 * @author DJun
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {

    /**
     * Jedis 执行器
     */
    R excute(T t) throws RedisConnectException;
}
