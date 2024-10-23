package com.bbs.cloud.admin.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

@Component
public class JedisUtil {

    @Autowired
    private RedisPool redisPool;

    /**
     * redis string类型，获取单个对象
     *
     * @param key   键
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            String str = jedis.get(key);
            return str;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> T get(String key, Class<T> beanType) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            String str = jedis.get(key);
            if(StringUtils.isEmpty(str)) {
                return null;
            }
            return JsonUtils.jsonToPojo(str, beanType);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * redis string类型，存储对象
     *
     * @param key   键
     * @param value 键值
     * @return
     */
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.set(key, value);
        } finally {
            returnToPool(jedis);
        }

    }

    /**
     * 递增加1
     * @param key
     */
    public void incr(String key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.incr(key);
        } finally {
            returnToPool(jedis);
        }

    }

    /**
     * 递增加value
     * @param key
     * @param value
     */
    public void incrBy(String key, Long value) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.incrBy(key, value);
        } finally {
            returnToPool(jedis);
        }

    }

    /**
     * 递减1
     * @param key
     */
    public void decr(String key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.decr(key);
        } finally {
            returnToPool(jedis);
        }

    }

    /**
     * 递减value
     * @param key
     * @param value
     */
    public void decrBy(String key, Long value) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.decrBy(key, value);
        } finally {
            returnToPool(jedis);
        }

    }

    /**
     * 删除key
     *
     * @param key 键，一个或多个
     * @return
     */
    public boolean del(String... key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.del(key);
            if (jedis.exists(key) == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            returnToPool(jedis);
        }
    }


    /**
     * 为给定得key设置时间，以秒计
     * @param key 键
     * @param seconds 秒
     * @return
     */
    public boolean pexpire(String key,int seconds) {
        Jedis jedis=null;
        try {
            jedis = redisPool.getJedis();
            jedis.expire(key, seconds);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * redis list类型，存储单个对象
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public void lpush(String key, String[] value) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.lpush(key, value);
        } finally {
            returnToPool(jedis);
        }
    }

    public void lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.lpush(key, value);
        } finally {
            returnToPool(jedis);
        }
    }

    public String lpop(String key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            return jedis.lpop(key);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 根据下标范围获取列表元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            return jedis.lrange(key, start, end);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 获取列表长度
     * @param key
     * @return
     */
    public Long llen(String key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            return jedis.llen(key);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断是否存在
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            return jedis.exists(key);
        } finally {
            returnToPool(jedis);
        }
    }


    /**
     * 向redis集合中保存成员
     * @param key
     * @param value
     */
    public void sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.sadd(key, value);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 从redis集合中删除成员
     * @param key
     * @param value
     */
    public void srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            jedis.srem(key, value);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 获取集合成员数量
     * @param key
     * @return
     */
    public Long srem(String key) {
        Jedis jedis = null;
        try {
            jedis = redisPool.getJedis();
            return jedis.scard(key);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 关闭jedis
     *
     * @param jedis
     */
    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}
