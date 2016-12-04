package com.yee.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Title:
 * Description:
 * Create Time: 2016/12/4 0004 23:03
 *
 * @author: YEEQiang
 * @version: 1.0
 */
public class TestPool {
    public static void main(String[] args) {
        JedisPool jedisPool1 = JedisPoolUtil.getJedisPoolInstance();
        JedisPool jedisPool2 = JedisPoolUtil.getJedisPoolInstance();
        System.out.println(jedisPool1 == jedisPool2);

        Jedis jedis = null;
        try {
            jedis = jedisPool1.getResource();
            jedis.set("yee", "qiang");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisPoolUtil.relase(jedisPool1, jedis);
        }

    }
}
