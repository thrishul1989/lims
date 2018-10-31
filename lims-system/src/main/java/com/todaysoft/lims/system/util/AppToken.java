package com.todaysoft.lims.system.util;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
@EnableCaching
@Configuration
public class AppToken
{
    
    @Value("${spring.redis.host}")
    private String host;
    
    @Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.password}")
    private String password;
    
    @Autowired
    @Qualifier("redisTemplate")
    protected RedisTemplate<String, String> redisTemplate;
    
    private static final String researchOrderNoPattern = "MGNR{0}";
    
    public void clearAppToken(String userId)
    {
        
        Jedis jedis = new Jedis(host, port);
        //权限认证
        jedis.auth(password);
        
        jedis.del("access_token_" + userId); //删除某个键
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
    
    public synchronized String getOrderCode()
    {
        
        BoundValueOperations<String, String> operation = redisTemplate.boundValueOps(PersistRedisKey.RESEARCH_ORDER_NO.noneOf());
        
        //按当前日期，累加
        int currentNum;
        try
        {
            currentNum = operation.increment(1).intValue();
        }
        catch (InvalidDataAccessApiUsageException e)
        {
            redisTemplate.delete(PersistRedisKey.RESEARCH_ORDER_NO.noneOf());
            operation = redisTemplate.boundValueOps(PersistRedisKey.RESEARCH_ORDER_NO.noneOf());
            currentNum = operation.increment(1).intValue();
        }
        
        //最大9999999
        //String currentString = "" + currentNum;
        String current = String.format("%07d", currentNum);
        
        String researchOrderNo = MessageFormat.format(researchOrderNoPattern, current);
        
        return researchOrderNo;
    }
    
}
