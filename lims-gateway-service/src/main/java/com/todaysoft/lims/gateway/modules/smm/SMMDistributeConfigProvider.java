package com.todaysoft.lims.gateway.modules.smm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;

@Service
public class SMMDistributeConfigProvider implements DistributeConfigProvider
{
    private static final String SMM_SERVICE_NAME = "lims-user-service";
    
    @Override
    public Set<DistributeConfig> getDistributeConfigs()
    {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getUserModuleDistributeConfig());
        configs.add(getRoleModuleDistributeConfig());
        configs.add(getResourceModuleDistributeConfig());
        configs.add(getDictModuleDistributeConfig());
        configs.add(getAuthorizeModuleDistributeConfig());
        configs.add(getAppsalemanModuleDistributeConfig());
        configs.add(getDataAuthorityModuleDistributeConfig());
        configs.add(getCouponModuleDistributeConfig());
        configs.add(getUserGroupDistributeConfig());
        configs.add(getInvoiceUserDistributeConfig());
        configs.add(getOperationLogConfig());
        configs.add(getMaterialLogConfig());
        return configs;
    }
    
    private DistributeConfig getUserModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/user/**/*"));
        return config;
    }
    
    private DistributeConfig getRoleModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/role/**/*"));
        return config;
    }
    
    private DistributeConfig getResourceModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/resource/**/*"));
        return config;
    }
    
    private DistributeConfig getDictModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/dict/**/*"));
        return config;
    }
    
    private DistributeConfig getAuthorizeModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/authorize/**/*"));
        return config;
    }
    
    private DistributeConfig getAppsalemanModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/appsaleman/**/*"));
        return config;
    }
    
    private DistributeConfig getDataAuthorityModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/dataAuthority/**/*"));
        return config;
    }
    
    private DistributeConfig getCouponModuleDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/coupon/**/*"));
        return config;
    }
    
    private DistributeConfig getUserGroupDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/userGroup/**/*"));
        return config;
    }
    
    private DistributeConfig getInvoiceUserDistributeConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/invoiceUser/**/*"));
        return config;
    }
    private DistributeConfig getOperationLogConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/opertaionLog/**/*"));
        return config;
    }
    private DistributeConfig getMaterialLogConfig()
    {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName(SMM_SERVICE_NAME);
        config.setPatterns(Collections.singleton("/smm/material/**/*"));
        return config;
    }
}
