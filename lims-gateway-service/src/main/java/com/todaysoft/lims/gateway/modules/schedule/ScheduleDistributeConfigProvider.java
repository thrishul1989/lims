package com.todaysoft.lims.gateway.modules.schedule;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class ScheduleDistributeConfigProvider implements DistributeConfigProvider
{
    private static final String SCHEDULE_SERVICE_NAME = "lims-schedule";
    
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SCHEDULE_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/schedule/**/*"));
        
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(config);
        return configs;
    }
}
