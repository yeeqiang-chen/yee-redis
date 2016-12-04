package com.yee.redis;

import redis.clients.jedis.Jedis;

/**
 * Title:
 * Description: 主从复制
 * Create Time: 2016/12/4 0004 23:31
 *
 * @author: YEEQiang
 * @version: 1.0
 */
public class JedisMasterSlaveDemo {
    public static void main(String[] args) {
        Jedis jedis_M = new Jedis("127.0.0.1", 6379);
        Jedis jedis_S = new Jedis("127.0.0.1", 6380);

        jedis_S.slaveof("127.0.0.1", 6379);
        jedis_M.set("age","26");
        String result = jedis_S.get("age");
        System.out.println(result);
    }
}
