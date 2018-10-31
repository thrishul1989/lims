package com.todaysoft.lims.gateway.modules.biology;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class BiologyDistributeConfigProvider implements DistributeConfigProvider
{
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getBiologyDivisionlModuleDistributeConfig());
        return configs;
    }

    private DistributeConfig getBiologyDivisionlModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-biology-service");
        config.setPatterns(Collections.singleton("/biology/**/*"));
        return config;
    }
    
}
