package com.todaysoft.lims.reagent.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;

@Component
@EnableCaching
@Configuration
public class LpushRedisLocation
{
    
    @Value("${spring.redis.host}")
    private String host;
    
    @Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.password}")
    private String password;
    
    public void addLocationRedis(String deviceId, String sampleId, String type, String code)
    {
        
        String key = deviceId + "_" + sampleId + "_" + type;
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        
        //jedis.del(key);
        
        jedis.lpush(key, code);
        //jedis.lpush(key, "B2-001-001-002");
        // jedis.lpush(key, "B2-001-001-003");
        
        //System.out.println(jedis.lpop(key)); // 栈顶
        
        //System.out.println(jedis.lrange(key, 0, -1));
        
        jedis.close();
    }
    
    public void clearLocationRedis(String deviceId, String sampleId, String type, String code)
    {
        
        String key = deviceId + "_" + sampleId + "_" + type;
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        if (StringUtils.isEmpty(code))
        {
            jedis.del(key); // 清空所有
        }
        else
        //分之暂不用
        {
            // jedis.lrem(key, 0, code); //全部删除 --code
            //在原有位置再加上
            jedis.linsert(key, LIST_POSITION.AFTER, "上一个code", code);
        }
        
        jedis.close();
    }
    
    /**
     * 先进后出
     * @param sampleId
     * @param type
     * @return
     */
    public String lpopLocationRedis(String deviceId, String sampleId, String type)
    {
        
        String key = deviceId + "_" + sampleId + "_" + type;
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        
        String location = jedis.rpop(key); // 栈顶
        
        jedis.close();
        
        return location;
    }
    
    /* public static void main(String[] args)
     {
         LpushRedisLocation aa = new LpushRedisLocation();
         aa.lpopLocationRedis("101068dbf2cc489dbe9d8999a6b20188", "2");
     }*/
    
}
