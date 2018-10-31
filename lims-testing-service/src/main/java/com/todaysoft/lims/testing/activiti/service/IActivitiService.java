package com.todaysoft.lims.testing.activiti.service;

import com.todaysoft.lims.testing.base.entity.TestingSheet;

public interface IActivitiService
{
    void releaseTestingSheet(TestingSheet data);
    
    void submitTestingSheet(String id);
}
