package com.todaysoft.lims.gateway.modules.bcm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class BCMDistributeConfigProvider implements DistributeConfigProvider
{
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getTestingMethodModuleDistributeConfig());
        configs.add(getProductPagingDistributeConfig());
        configs.add(getSampleDistributeConfig());
        configs.add(getTestingTypeDistributeConfig());
        configs.add(geContractDistributeConfig());
        configs.add(geSampleReceivingDistributeConfig());
        configs.add(getTaskConfigModuleDistributeConfig());
        configs.add(getDiseaseConfigModuleDistributeConfig());
        configs.add(getPhenotypeConfigModuleDistributeConfig());
        configs.add(getTestingNodeConfigModuleDistributeConfig());
        configs.add(getDocumentConfigModuleDistributeConfig());
        configs.add(getConfigModuleDistributeConfig());
        configs.add(getMaterialsApplyDistributeConfig());
        configs.add(getGeneDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getTestingMethodModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/testing/methods/**/*"));
        return config;
    }
    
    private DistributeConfig getTestingNodeConfigModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/testing/nodes/**/*"));
        return config;
    }
    
    private DistributeConfig getProductPagingDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/product/**/*"));
        return config;
    }
    
    private DistributeConfig getSampleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/sample/**/*"));
        return config;
    }
    
    private DistributeConfig getTestingTypeDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/testingType/**/*"));
        return config;
    }
    
    private DistributeConfig geContractDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bcm/contract/**/*"));
        return config;
    }
    
    private DistributeConfig geSampleReceivingDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bcm/sampleReceiving/**/*"));
        return config;
    }
    
    private DistributeConfig getTaskConfigModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/task/**/*"));
        return config;
    }
    
    private DistributeConfig getDiseaseConfigModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/disease/**/*"));
        return config;
    }
    
    private DistributeConfig getPhenotypeConfigModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/phenotype/**/*"));
        return config;
    }
    
    private DistributeConfig getDocumentConfigModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/document/**/*"));
        return config;
    }
    
    private DistributeConfig getConfigModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/configs/**/*"));
        return config;
    }
    
    private DistributeConfig getMaterialsApplyDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bcm/materialsApply/**/*"));
        return config;
    }
    
    private DistributeConfig getGeneDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-product-service");
        config.setPatterns(Collections.singleton("/bcm/gene/**/*"));
        return config;
    }
}
