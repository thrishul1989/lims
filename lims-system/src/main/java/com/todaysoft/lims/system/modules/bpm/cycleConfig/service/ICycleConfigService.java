package com.todaysoft.lims.system.modules.bpm.cycleConfig.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.GlobalConfigSearcher;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.TestingConfigSearcher;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.WarningGlobalConfigModel;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.WarningTestingConfigModel;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.request.GlobalConfigRequest;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.request.TestingConfigRequest;
import com.todaysoft.lims.system.modules.report.model.ScheduleTestingConfig;

public interface ICycleConfigService
{
    Pagination<WarningGlobalConfigModel> globalPagining(GlobalConfigSearcher searcher);
    Pagination<WarningTestingConfigModel> testingPagining(TestingConfigSearcher searcher);
    void modify(GlobalConfigRequest request);
    List<TestingMethod> getTestingMethodList();
    List<TaskConfig> getTaskConfigBySemantic(TestingConfigRequest request);
    void create(TestingConfigRequest request);
    public boolean validate(TestingConfigSearcher request);
    ScheduleTestingConfig getTestingConfigById(String id);
    WarningTestingConfigModel getById(String id);
    Integer delete(String id);
    List<WarningTestingConfigModel> getScheduleTestingConfigList(TestingConfigRequest request);
    
}
