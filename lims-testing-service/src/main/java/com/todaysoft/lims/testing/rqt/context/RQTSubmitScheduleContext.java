package com.todaysoft.lims.testing.rqt.context;

import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.model.TaskConfig;

public class RQTSubmitScheduleContext
{
    private String id;
    
    private TaskConfig nextNodeConfig;
    
    private TestingSchedule testingScheduleEntity;
    
    private Set<RQTSubmitNextTaskContext> nextTasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public TaskConfig getNextNodeConfig()
    {
        return nextNodeConfig;
    }
    
    public void setNextNodeConfig(TaskConfig nextNodeConfig)
    {
        this.nextNodeConfig = nextNodeConfig;
    }
    
    public TestingSchedule getTestingScheduleEntity()
    {
        return testingScheduleEntity;
    }
    
    public void setTestingScheduleEntity(TestingSchedule testingScheduleEntity)
    {
        this.testingScheduleEntity = testingScheduleEntity;
    }
    
    public Set<RQTSubmitNextTaskContext> getNextTasks()
    {
        return nextTasks;
    }
    
    public void setNextTasks(Set<RQTSubmitNextTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }
}
