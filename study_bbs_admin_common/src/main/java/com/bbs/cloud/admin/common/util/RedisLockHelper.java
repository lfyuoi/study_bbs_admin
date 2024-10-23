package com.bbs.cloud.admin.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLockHelper {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 尝试获取锁（立即返回）
     * @param key  锁的redis key
     * @param value 锁的value
     * @param expire 过期时间/秒
     * @return 是否获取成功
     */
    public boolean lock(String key, String value, long expire) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 释放锁
     * @param key
     */
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
