package com.todaysoft.lims.gateway.modules.invoice;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class InvoiceDistributeConfigProvider implements DistributeConfigProvider
{
    private static final String ES_SERVICE_NAME = "lims-invoice-service";
    
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getInvoiceDistributeConfig());
        configs.add(getInvoiceSendDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getInvoiceDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(ES_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/invoice/**/*"));
        return config;
    }
    
    private DistributeConfig getInvoiceSendDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(ES_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/invoiceSend/**/*"));
        return config;
    }
}
