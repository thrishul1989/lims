package com.todaysoft.lims.gateway.modules.maintain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class MaintainDistributeConfigProvider implements DistributeConfigProvider
{
    private static final String MAINTAIN_SERVICE_NAME = "lims-maintain-service";
    
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getMaintainModuleDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getMaintainModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(MAINTAIN_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/maintain/**/*"));
        return config;
    }
}
