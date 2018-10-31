package com.todaysoft.lims.testing.technicalanaly.context;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class TechnicalAnalySubmitTaskContext
{
    private TestingTask testingTaskEntity;
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
}
