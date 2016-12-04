package com.yee.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.lang.management.BufferPoolMXBean;

/**
 * Title: redis 事务--加锁
 * Description:
 * Create Time: 2016/12/4 0004 23:09
 *
 * @author: YEEQiang
 * @version: 1.0
 */
public class JedisTxDemo {

    public static void main(String[] args) throws InterruptedException {
        JedisTxDemo jedisTxDemo = new JedisTxDemo();
        boolean resultValue = jedisTxDemo.transMethod();
        System.out.println("final resultValue ===> " + resultValue);
    }

    /**
     * 通俗点讲，watch命令就是标记一个键，如果标记了一个键，
     * 在提交事务前如果该键被别人修改过，那事务就会失败，这种情况通常可以在程序中重新再尝试一次。
     * 首先标记了键balance，然后检查余额是否足够，不足就取消标记，并不做扣减；
     * 足够的话，就启动事务进行更新操作，
     * 如果在此期间键balance被其它人修改， 那在提交事务（执行exec）时就会报错，
     * 程序中通常可以捕获这类错误再重新执行一次，直到成功。
     * @return
     * @throws InterruptedException
     */
    public boolean transMethod () throws InterruptedException {
        /** 可用余额 */
        int balance;
        /** 欠额 */
        int debt;
        /** 实刷额度 */
        int amtToSubtract = 10;
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        jedis.watch("balance");
//        jedis.set("balance","7"); // 模拟其他程序已经修改了该条目
        Thread.sleep(10000);
        balance = Integer.parseInt(jedis.get("balance"));
        if (balance < amtToSubtract) {
            jedis.unwatch();
            System.out.println("This data has been modified, Please refresh first!");
            return false;
        } else {
            System.out.println("******transaction******");
            Transaction transaction = jedis.multi();
            transaction.decrBy("balance", amtToSubtract);
            transaction.incrBy("debt", amtToSubtract);
            transaction.exec();
            balance = Integer.parseInt(jedis.get("balance"));
            debt = Integer.parseInt(jedis.get("debt"));
            System.out.println("balance ====> " + balance);
            System.out.println("debt ===> " + debt);
            return true;
        }
    }
}
