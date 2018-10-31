package com.todaysoft.lims.testing.resampling.service;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingResamplingRecord;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.resampling.model.ResamplingCancelRecordHandleRequest;
import com.todaysoft.lims.testing.resampling.model.ResamplingSchedule;

public interface IResamplingService
{
    void restart(TestingResamplingRecord record);
    
    List<ResamplingSchedule> getSchedules(String id);
    
    void handleCancelRecord(ResamplingCancelRecordHandleRequest request, String token, VariableModel model);
    
    List<TaskConfig> getRiskTestingNode(String id);
    
    TestingMethod findFamilyVerityAutoStartMethod();
}
