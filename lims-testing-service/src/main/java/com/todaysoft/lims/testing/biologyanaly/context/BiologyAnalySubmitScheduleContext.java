package com.todaysoft.lims.testing.biologyanaly.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.model.TaskConfig;

public class BiologyAnalySubmitScheduleContext
{
    private TestingSchedule testingScheduleEntity;
    
    private TaskConfig testingScheduleNextNodeConfig;
    
    public TestingSchedule getTestingScheduleEntity()
    {
        return testingScheduleEntity;
    }
    
    public void setTestingScheduleEntity(TestingSchedule testingScheduleEntity)
    {
        this.testingScheduleEntity = testingScheduleEntity;
    }
    
    public TaskConfig getTestingScheduleNextNodeConfig()
    {
        return testingScheduleNextNodeConfig;
    }
    
    public void setTestingScheduleNextNodeConfig(TaskConfig testingScheduleNextNodeConfig)
    {
        this.testingScheduleNextNodeConfig = testingScheduleNextNodeConfig;
    }
}
