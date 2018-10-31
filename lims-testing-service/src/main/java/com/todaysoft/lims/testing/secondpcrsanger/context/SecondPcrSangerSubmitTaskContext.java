package com.todaysoft.lims.testing.secondpcrsanger.context;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class SecondPcrSangerSubmitTaskContext
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
