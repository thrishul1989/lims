package com.todaysoft.lims.gateway.modules.order;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class OrderDistributeConfigProvider implements DistributeConfigProvider
{
    private static final String ORDER_SERVICE_NAME = "lims-order";
    
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(ORDER_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/orders/**/*"));
        
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(config);
        configs.add(getReconciliationModuleDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getReconciliationModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(ORDER_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/reconciliation/**/*"));
        return config;
    }
    
}
