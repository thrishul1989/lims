package com.todaysoft.lims.gateway.service.impl;

import java.util.Set;

public class DistributeConfig
{
    private String serviceName;
    
    private Set<String> patterns;
    
    public String getServiceName()
    {
        return serviceName;
    }
    
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }
    
    public Set<String> getPatterns()
    {
        return patterns;
    }
    
    public void setPatterns(Set<String> patterns)
    {
        this.patterns = patterns;
    }
}
