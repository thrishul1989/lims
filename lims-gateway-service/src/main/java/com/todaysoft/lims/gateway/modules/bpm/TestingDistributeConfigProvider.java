package com.todaysoft.lims.gateway.modules.bpm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class TestingDistributeConfigProvider implements DistributeConfigProvider
{
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getTestingModuleDistributeConfig());
        configs.add(getReportEmailModuleDistributeConfig());
        configs.add(getCycleConfigModuleDistributeConfig());
        configs.add(getReportCancewlModuleDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getTestingModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-testing-service");
        config.setPatterns(Collections.singleton("/bpm/**/*"));
        return config;
    }
    
    private DistributeConfig getReportEmailModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-testing-service");
        config.setPatterns(Collections.singleton("/report/reportEmail/**/*"));
        return config;
    }
    
    private DistributeConfig getCycleConfigModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-testing-service");
        config.setPatterns(Collections.singleton("/bpm/cycleConfig/**/*"));
        return config;
    }
    
    private DistributeConfig getReportCancewlModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-testing-service");
        config.setPatterns(Collections.singleton("/bpm/report/reportCancel/*/*"));
        return config;
    }
    
}
