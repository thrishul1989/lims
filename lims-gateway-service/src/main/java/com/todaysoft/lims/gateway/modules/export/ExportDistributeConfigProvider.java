package com.todaysoft.lims.gateway.modules.export;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExportDistributeConfigProvider implements DistributeConfigProvider
{
    private static final String EXPORT_SERVICE_NAME = "lims-export-service";
    
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getExperimentDistributeConfig());
        configs.add(getFinanceDistributeConfig());
        configs.add(getSystemDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getExperimentDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(EXPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/export/experiment/**/*"));
        return config;
    }
    
    private DistributeConfig getFinanceDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(EXPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/export/finance/**/*"));
        return config;
    }
    
    private DistributeConfig getSystemDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(EXPORT_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/export/system/**/*"));
        return config;
    }
    
}
