package com.jinli.jinrui.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import javax.jws.soap.SOAPBinding;
import java.util.Set;

/*
* 使用Jedis来操作Redis
* */
public class JedisTest {

    @Test
    public void testRedis(){
        //获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //执行具体操作
        jedis.set("username", "xiaoming");
        String value = jedis.get("username");
        System.out.println(value);

        //删除
        jedis.del("username");
        //存储
        jedis.hset("myhash","addr","bj");
        String hvalue = jedis.hget("myhash", "addr");
        System.out.println(hvalue);

        Set<String> keys = jedis.keys("*");
        for (String key : keys){
            System.out.println(key);
        }

        //关闭连接
        jedis.close();
    }

}
