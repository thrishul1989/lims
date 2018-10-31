package com.todaysoft.lims.gateway.modules.technical;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class TechnicalDistributeConfigProvider implements DistributeConfigProvider
{
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getTechnicalDistributeConfig());
        return configs;
    }

    private DistributeConfig getTechnicalDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-technical-service");
        config.setPatterns(Collections.singleton("/technicalanaly/**/*"));
        return config;
    }
    
}
