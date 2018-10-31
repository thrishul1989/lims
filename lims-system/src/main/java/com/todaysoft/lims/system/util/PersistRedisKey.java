package com.todaysoft.lims.system.util;

/***
 *
 * 建议通过of生成KEY的话，前缀后面以.符号结束；
 *
 ***/
public enum PersistRedisKey implements RedisKey
{
    
    ACCESS_TOKEN("access_token"), //hash key: userId, value: accessToken
    
    LAUNCH_DAILY("launch_daily"), //set member: userId 每日登录记录
    LAUNCH_WEEKLY("launch_weekly"), //set member: userId 每周登录记录
    LAUNCH_MONTHLY("launch_monthly"), //set member: userId 每月登录记录
    
    REGISTER_APP_WEEKLY("register_app_weekly"), //set member: userId 每周通过APP注册的用户
    
    GLOBAL_CONFIG("global_config"), //hash key: (data, release), value: json
    
    SHOPPING_CART("shopping_cart"), //hash key: (itemId, count), value: json
    
    QUEUE_SMS("queue_sms"), //list value: sms json
    
    MOBILE_NUMBER_COUNT("mobile_number_count"), //hash key: mobile_number, value: date&count json
    
    VERIFY_TOKEN("verify_token"), //key: mobileNumber, value: verifyToken
    
    ORDER_NO("order_no"), //zset member: date string score: current no
    
    REPORT_NAME("report_name"),
    
    RESEARCH_ORDER_NO("research_no");
    
    private String prefix;
    
    PersistRedisKey(String prefix)
    {
        this.prefix = prefix;
    }
    
    @Override
    public String of(Object key)
    {
        if (key != null)
        {
            return this.prefix + '_' + key;
        }
        else
        {
            return this.prefix;
        }
    }
    
    public String getPrefix()
    {
        return prefix;
    }
}
