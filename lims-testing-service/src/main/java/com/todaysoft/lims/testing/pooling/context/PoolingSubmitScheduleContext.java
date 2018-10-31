package com.todaysoft.lims.testing.pooling.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;

public class PoolingSubmitScheduleContext
{
    private String id;
    
    private TestingSchedule testingScheduleEntity;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public TestingSchedule getTestingScheduleEntity()
    {
        return testingScheduleEntity;
    }
    
    public void setTestingScheduleEntity(TestingSchedule testingScheduleEntity)
    {
        this.testingScheduleEntity = testingScheduleEntity;
    }
}
