package com.todaysoft.lims.gateway.modules.es;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class ESDistributeConfigProvider implements DistributeConfigProvider
{
    private static final String ES_SERVICE_NAME = "lims-kbm-es-service";
    
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getESModuleDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getESModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(ES_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/es/**/*"));
        return config;
    }
}
