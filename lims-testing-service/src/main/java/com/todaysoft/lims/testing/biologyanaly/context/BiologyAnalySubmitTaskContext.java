package com.todaysoft.lims.testing.biologyanaly.context;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class BiologyAnalySubmitTaskContext
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
