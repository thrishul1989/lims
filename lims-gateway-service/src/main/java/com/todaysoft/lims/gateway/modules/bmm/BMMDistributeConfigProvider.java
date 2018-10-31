package com.todaysoft.lims.gateway.modules.bmm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class BMMDistributeConfigProvider implements DistributeConfigProvider
{
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getOrderModuleDistributeConfig());
        configs.add(getDataAreaModuleDistributeConfig());
        configs.add(getOrderApplyModuleDistributeConfig());
        configs.add(getPaymentModuleDistributeConfig());
        configs.add(getInvoiceApplyDistributeConfig());
        configs.add(getMeetingApplyDistributeConfig());
        configs.add(getSamplebackApplyDistributeConfig());
        configs.add(getResamplingCancelModuleDistributeConfig());
        configs.add(getAdvanceInvoiceDistributeConfig());
        configs.add(getDefaultInvoiceDistributeConfig());
        return configs;
    }
    
    private DistributeConfig getDataAreaModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/dataarea/**/*"));
        return config;
    }
    
    private DistributeConfig getOrderModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/order/**/*"));
        return config;
    }
    
    private DistributeConfig getOrderApplyModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/orderApply/**/*"));
        return config;
    }
    
    private DistributeConfig getPaymentModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/payment/**/*"));
        return config;
    }
    
    private DistributeConfig getInvoiceApplyDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/invoiceApply/**/*"));
        return config;
    }
    
    private DistributeConfig getMeetingApplyDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/meetingApply/**/*"));
        return config;
    }
    
    private DistributeConfig getSamplebackApplyDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/sampleBackApply/**/*"));
        return config;
    }
    
    private DistributeConfig getResamplingCancelModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/resampling/**/*"));
        return config;
    }
    
    private DistributeConfig getAdvanceInvoiceDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/advanceInvoice/**/*"));
        return config;
    }
    
    private DistributeConfig getDefaultInvoiceDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-sampleReceive-service");
        config.setPatterns(Collections.singleton("/bmm/defaultInvoice/**/*"));
        return config;
    }
}
