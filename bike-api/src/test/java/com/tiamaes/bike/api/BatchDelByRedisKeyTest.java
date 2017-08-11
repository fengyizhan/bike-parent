package com.tiamaes.bike.api;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.tiamaes.bike.common.RedisKey;

import redis.clients.jedis.Jedis;

/**
 * 根据RedisKey批量删除缓存
 * @author kangty
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class BatchDelByRedisKeyTest {
	@Resource
	private RedisTemplate<String,String> redisTemplate;
	private Jedis jedis1;
    private Jedis jedis2;
    private Jedis jedis3;
    private String redisKey = "bike:projects";
    
    @Before
    public void setup() {
//        System.out.println("redis连接设置");
//        jedis1 = new Jedis("192.168.57.144", 7000);
//        jedis2 = new Jedis("192.168.57.144", 7001);
//        jedis3 = new Jedis("192.168.57.144", 7003);
    }
    @Test
    public void t1() throws Exception
    {
    	Object o=redisTemplate.opsForHash().get(RedisKey.PARKS,"10006");
    	System.out.println("o:"+o);
    }
    /** 
     * redis批量删除
     */  
    @Test
    public void testBatchDel() {  
        System.out.println("开始删除");  
        batchDel(jedis1);
        batchDel(jedis2);
        batchDel(jedis3);
    }
    
    private void batchDel(Jedis jedis){
        Set<String> set = jedis.keys(this.redisKey +"*");
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            String keyStr = it.next();
            System.out.println(keyStr);
            jedis.del(keyStr);
        }
    }

}
