package com.todaysoft.lims.testing.secondpcr.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.model.TaskConfig;

public class SecondPcrSubmitScheduleContext
{
    private String id;

    private String nextNodeInputSampleId;

    private TaskConfig nextNodeConfig;

    private TestingSchedule testingScheduleEntity;

    private TestingScheduleActive testingScheduleActiveEntity;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNextNodeInputSampleId()
    {
        return nextNodeInputSampleId;
    }

    public void setNextNodeInputSampleId(String nextNodeInputSampleId)
    {
        this.nextNodeInputSampleId = nextNodeInputSampleId;
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

    public TestingScheduleActive getTestingScheduleActiveEntity()
    {
        return testingScheduleActiveEntity;
    }

    public void setTestingScheduleActiveEntity(TestingScheduleActive testingScheduleActiveEntity)
    {
        this.testingScheduleActiveEntity = testingScheduleActiveEntity;
    }
}
