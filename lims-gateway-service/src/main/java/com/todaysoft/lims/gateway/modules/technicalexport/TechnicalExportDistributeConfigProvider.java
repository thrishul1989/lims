package com.todaysoft.lims.gateway.modules.technicalexport;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class TechnicalExportDistributeConfigProvider implements DistributeConfigProvider
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
        config.setServiceName("lims-technical-export-service");
        config.setPatterns(Collections.singleton("/technicalexport/**/*"));
        return config;
    }
    
}
