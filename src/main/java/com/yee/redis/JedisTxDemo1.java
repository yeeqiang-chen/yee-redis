package com.yee.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * Title: 事务回滚
 * Description:
 * Create Time: 2016/12/5 0005 1:28
 *
 * @author: YEEQiang
 * @version: 1.0
 */
public class JedisTxDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        // 监控key,如果改动了事务就放弃
       /* jedis.watch("serialNum");
        jedis.set("serialNum","s***********");
        jedis.unwatch();*/

        Transaction transaction = jedis.multi();
        Response<String> responce = transaction.get("serialNum");
        transaction.set("serialNum","s003");
        responce = transaction.get("serialNum");
        transaction.lpush("list3","a");
        transaction.lpush("list3","b");
        transaction.lpush("list3","c");

//        transaction.exec();
        transaction.discard();
        System.out.println("serialMum ===> " + responce.get());
    }
}
