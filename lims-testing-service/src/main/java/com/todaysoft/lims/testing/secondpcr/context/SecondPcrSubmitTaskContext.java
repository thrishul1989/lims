package com.todaysoft.lims.testing.secondpcr.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class SecondPcrSubmitTaskContext
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
