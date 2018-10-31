package com.todaysoft.lims.system.modules.bcm.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bcm.model.TestingNodeReagentKitConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingNodeReagentKitConfigSearcher;
import com.todaysoft.lims.system.modules.bcm.model.TestingNodeUserGroupConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingNodeUserGroupConfigSearcher;

public interface ITestingNodeConfigService
{
    List<TestingNodeReagentKitConfig> search(TestingNodeReagentKitConfigSearcher searcher);
    
    List<TestingNodeUserGroupConfig> List(TestingNodeUserGroupConfigSearcher searcher);
}
