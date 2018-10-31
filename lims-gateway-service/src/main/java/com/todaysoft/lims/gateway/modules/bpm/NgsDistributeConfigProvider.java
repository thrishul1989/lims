package com.todaysoft.lims.gateway.modules.bpm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.service.impl.DistributeConfig;
import com.todaysoft.lims.gateway.service.impl.DistributeConfigProvider;
@Service
public class NgsDistributeConfigProvider implements DistributeConfigProvider {

	@Override
	public Set<DistributeConfig> getDistributeConfigs() {
		Set<DistributeConfig> configs = new HashSet<DistributeConfig>();
		configs.add(getNgsModuleDistributeConfig());

		return configs;
	}

	private DistributeConfig getNgsModuleDistributeConfig() {
		DistributeConfig config = new DistributeConfig();
		config.setServiceName("lims-ngs-service");
		config.setPatterns(Collections.singleton("/bpm/ngs/**/*"));
		return config;
	}

}
