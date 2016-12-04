package com.yee.redis;

import org.apache.log4j.pattern.LineSeparatorPatternConverter;
import redis.clients.jedis.Jedis;

import javax.sound.midi.Soundbank;
import javax.swing.border.EmptyBorder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisDemo {

    public static void main(String[] args) {
        // 构造jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 测试连通性
        System.out.println(jedis.ping());
        // 向redis中添加数据
        jedis.set("foo", "123");
        // 从redis中读取数据
        String value = jedis.get("foo");
        System.out.println(value);

        // key
        Set<String> keys = jedis.keys("*");
        for (Iterator iterator = keys.iterator();iterator.hasNext();) {
            String key = (String) iterator.next();
            System.out.println(key);
        }
        System.out.println("jedis.exists===>" + jedis.exists("yee"));
        System.out.println(jedis.ttl("balance"));

        // String
//        jedis.append("k1","myredis");
        System.out.println(jedis.get("k1"));
        jedis.set("k4","ke_redis");
        System.out.println("-------------------------------------");
        jedis.mget("str1","v1","str2","v2","str3","v3");
        System.out.println(jedis.mget("str1","str2","str3"));
        System.out.println("-------------------------------------");

        // list
        jedis.lpush("mylist","v1","v2","v3","v4","v5");
        List<String> list = jedis.lrange("mylist",0,-1);
        for (String element : list) {
            System.out.println(element);
        }

        // set
        jedis.sadd("orders","jd001");
        jedis.sadd("orders","jd002");
        jedis.sadd("orders","jd003");
        Set<String> set1 = jedis.smembers("orders");
        for (Iterator iterator = set1.iterator();iterator.hasNext();) {
            String string = (String) iterator.next();
            System.out.println(string);
        }
        jedis.srem("orders","jd002");
        System.out.println(jedis.smembers("orders").size());

        // hash
        jedis.hset("hash1","userName","lisi");
        System.out.println(jedis.hget("hash1","userName"));
        Map<String,String> map = new HashMap<String, String>();
        map.put("telphone","15828050670");
        map.put("address","chongqing");
        map.put("email","yee@126.com");
        jedis.hmset("hash2",map);
        List<String > result = jedis.hmget("hash2","telphone","email");
        for (String element : result) {
            System.out.println(element);
        }

        // zset
        jedis.zadd("zset01",60d,"v1");
        jedis.zadd("zset01",70d,"v2");
        jedis.zadd("zset01",80d,"v3");
        jedis.zadd("zset01",90d,"v4");
        Set<String > s1 = jedis.zrange("zset01",0,-1);
        for (Iterator iterator = s1.iterator();iterator.hasNext();) {
            String string = (String) iterator.next();
            System.out.println(string);
        }
        // 关闭连接
        jedis.close();

    }

}
