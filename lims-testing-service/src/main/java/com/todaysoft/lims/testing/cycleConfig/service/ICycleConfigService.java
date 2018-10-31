package com.todaysoft.lims.testing.cycleConfig.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.ScheduleTestingConfig;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.cycleConfig.dao.GlobalConfigSearcher;
import com.todaysoft.lims.testing.cycleConfig.dao.OtherReportFormSearcher;
import com.todaysoft.lims.testing.cycleConfig.dao.TestingConfigSearcher;
import com.todaysoft.lims.testing.cycleConfig.model.OtherReportFormModel;
import com.todaysoft.lims.testing.cycleConfig.model.WarningGlobalConfigModel;
import com.todaysoft.lims.testing.cycleConfig.model.WarningTestingConfigModel;
import com.todaysoft.lims.testing.cycleConfig.service.request.GlobalConfigRequest;
import com.todaysoft.lims.testing.cycleConfig.service.request.TaskConfigRequest;

public interface ICycleConfigService
{
    Pagination<WarningGlobalConfigModel> globalPagining(GlobalConfigSearcher searcher);
    
    Pagination<WarningTestingConfigModel> testingPagining(TestingConfigSearcher searcher);
    
    void modify(GlobalConfigRequest request);
    
    List<TestingMethod> getTestingMethodList();
    
    TaskConfig getTaskConfigBySemantic(String semantic);
    
    void create(TaskConfigRequest request);
    
    boolean validate(TestingConfigSearcher request);
    
    ScheduleTestingConfig getTestingConfigById(String id);
    
    Integer delete(String id);
    
    TestingMethod getTestingMethodListById(String id);
    
    List<WarningTestingConfigModel> getScheduleTestingConfigList(TestingConfigSearcher request);
    
    List<OtherReportFormModel> getCycleReportFormList(OtherReportFormSearcher rrquest);
    
    List<TestingMethod> getTestingMethodListIncludeVerity();
}
