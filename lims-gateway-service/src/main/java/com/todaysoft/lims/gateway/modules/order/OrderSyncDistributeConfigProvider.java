package com.todaysoft.lims.gateway.modules.order;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderSyncDistributeConfigProvider implements DistributeConfigProvider {
    @Override
    public Set<DistributeConfig> getDistributeConfigs() {
        Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
        configs.add(getOrderSyncModuleDistributeConfig());
        return configs;
    }

    private DistributeConfig getOrderSyncModuleDistributeConfig() {
        DistributeConfig config = new DistributeConfig();
        config.setServiceName("lims-order-sync-service");
        config.setPatterns(Collections.singleton("/order/sync/**/*"));
        return config;
    }

}
