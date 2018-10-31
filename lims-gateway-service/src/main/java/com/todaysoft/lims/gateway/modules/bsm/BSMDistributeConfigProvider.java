package com.todaysoft.lims.gateway.modules.bsm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class BSMDistributeConfigProvider implements DistributeConfigProvider
{
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getStorageDeviceModuleDistributeConfig());
        configs.add(getEquipmentModuleDistributeConfig());
        configs.add(getPrimerModuleDistributeConfig());
        configs.add(getPrimerDatumModuleDistributeConfig());
        configs.add(getReagentDistributeConfig());
        configs.add(getReagentKitModuleDistributeConfig());
        configs.add(getConnectDistributeConfig());
        configs.add(getProbeModuleDistributeConfig());
        
        configs.add(getCustomerModuleDistributeConfig());
        
        configs.add(getReagentModuleDistributeConfig());
        configs.add(getDepartmentModuleDistributeConfig());
        configs.add(getUserFeedbackModuleDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getStorageDeviceModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/storeContainer/**/*"));
        return config;
    }
    
    private DistributeConfig getEquipmentModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/equipment/**/*"));
        return config;
    }
    
    private DistributeConfig getPrimerModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/primer/**/*"));
        return config;
    }
    
    private DistributeConfig getPrimerDatumModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/primer_datum/**/*"));
        return config;
    }
    
    private DistributeConfig getReagentDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/reagent/**/*"));
        return config;
    }
    
    private DistributeConfig getReagentKitModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/reagentKit/**/*"));
        return config;
    }
    
    private DistributeConfig getConnectDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/connect/**/*"));
        return config;
    }
    
    private DistributeConfig getProbeModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/probe/**/*"));
        return config;
    }
    
    private DistributeConfig getCustomerModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/customer/**/*"));
        return config;
    }
    
    private DistributeConfig getReagentModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/company/**/*"));
        return config;
        
    }
    
    private DistributeConfig getDepartmentModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/department/**/*"));
        return config;
        
    }
    
    private DistributeConfig getUserFeedbackModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-metadata-service");
        config.setPatterns(Collections.singleton("/bsm/userFeedback/**/*"));
        return config;
        
    }
    
}
