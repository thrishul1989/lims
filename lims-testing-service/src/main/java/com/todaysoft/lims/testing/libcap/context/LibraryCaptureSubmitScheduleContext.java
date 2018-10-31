package com.todaysoft.lims.testing.libcap.context;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;

public class LibraryCaptureSubmitScheduleContext
{
    private String id;
    
    private TestingSchedule testingScheduleEntity;
    
    private List<LibraryCaptureSubmitNextTaskContext> nextTasks;
    
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
    
    public List<LibraryCaptureSubmitNextTaskContext> getNextTasks()
    {
        return nextTasks;
    }
    
    public void setNextTasks(List<LibraryCaptureSubmitNextTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }
}
