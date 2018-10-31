package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.product.entity.TestingNodeUserGroupConfig;
import com.todaysoft.lims.product.model.TestingNodeReagentKitConfig;
import com.todaysoft.lims.product.model.request.TestingNodeReagentKitConfigRequest;
import com.todaysoft.lims.product.model.request.TestingNodeUserGroupConfigRequest;

public interface ITestingNodeConfigService
{
    List<TestingNodeReagentKitConfig> search(TestingNodeReagentKitConfigRequest request);
    
    List<TestingNodeUserGroupConfig> getUserBySemantic(TestingNodeUserGroupConfigRequest request);
}
