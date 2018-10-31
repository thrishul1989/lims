package com.todaysoft.lims.sample.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
@EnableCaching
@Configuration
public class LpopRedisLocation
{
    
    @Value("${spring.redis.host}")
    private String host;
    
    @Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.password}")
    private String password;
    
    public void addLocationRedis(String sampleId, String type)
    {
        
        String key = sampleId + "_" + type;
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        
        jedis.del(key);
        
        jedis.lpush(key, "B2-001-001-001");
        jedis.lpush(key, "B2-001-001-002");
        jedis.lpush(key, "B2-001-001-003");
        
        System.out.println(jedis.lpop(key)); // 栈顶
        
        System.out.println(jedis.lrange(key, 0, -1));
        
        jedis.close();
    }
    
    /**
     * 先进先出
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
    /*   
       public static void main(String[] args)
       {
           LpopRedisLocation aa = new LpopRedisLocation();
           aa.addLocationRedis("1000c3317cd0486890167d94e60820ad", "2");
       }
       */
}
