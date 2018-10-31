package com.todaysoft.lims.technical.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfigManage
{
    
    @Autowired
    private Environment environment;
    
    public String getDownloadUrl()
    {
        return environment.getRequiredProperty("download.url");
        
    }
    
    public String getkey(String key)
    {
        return environment.getRequiredProperty(key);
    }
    
}
