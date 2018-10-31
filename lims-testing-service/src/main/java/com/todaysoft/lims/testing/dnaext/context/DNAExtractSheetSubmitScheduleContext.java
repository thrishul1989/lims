package com.todaysoft.lims.testing.dnaext.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.model.TaskConfig;

public class DNAExtractSheetSubmitScheduleContext
{
    private String id;
    
    private String nextNodeInputSampleCode;
    
    private TaskConfig nextNodeConfig;
    
    private TestingSchedule testingScheduleEntity;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getNextNodeInputSampleCode()
    {
        return nextNodeInputSampleCode;
    }
    
    public void setNextNodeInputSampleCode(String nextNodeInputSampleCode)
    {
        this.nextNodeInputSampleCode = nextNodeInputSampleCode;
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
}
