package com.yee.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Title: redis 连接池工具类
 * Description:
 * Create Time: 2016/12/4 0004 22:47
 *
 * @author: YEEQiang
 * @version: 1.0
 */
public class JedisPoolUtil {
    private static volatile JedisPool ourInstance = null;

    private JedisPoolUtil() {
    }

    public static JedisPool getJedisPoolInstance() {
        if (null == ourInstance) {
            synchronized (JedisPoolUtil.class) {
                if (null == ourInstance) {
                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                    jedisPoolConfig.setMaxTotal(1000);
                    jedisPoolConfig.setMaxIdle(32);
                    jedisPoolConfig.setMaxWaitMillis(100*1000);
                    ourInstance = new JedisPool(jedisPoolConfig,"127.0.0.1",6379);
                }
            }
        }
        return ourInstance;
    }

    /**
     * 释放指定连接
     * @param jedisPool
     * @param jedis
     */
    public static void relase(JedisPool jedisPool, Jedis jedis) {
        if (null !=  jedis) {
            jedisPool.returnResourceObject(jedis);
        }
    }
}
