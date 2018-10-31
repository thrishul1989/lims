package com.todaysoft.lims.testing.dtdata.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;

public class DTDataSubmitScheduleContext
{
    private String id;

    private boolean qualified;

    private String unqualifiedRemark;

    private String unqualifiedStrategy;

    private String nextNodeInputSampleId;

    private TaskConfig nextNodeConfig;

    private TestingSchedule testingScheduleEntity;

    private TestingScheduleActive testingScheduleActiveEntity;

    private TestingTask testingTask;

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

    public boolean isQualified() {
        return qualified;
    }

    public void setQualified(boolean qualified) {
        this.qualified = qualified;
    }

    public String getUnqualifiedRemark() {
        return unqualifiedRemark;
    }

    public void setUnqualifiedRemark(String unqualifiedRemark) {
        this.unqualifiedRemark = unqualifiedRemark;
    }

    public String getUnqualifiedStrategy() {
        return unqualifiedStrategy;
    }

    public void setUnqualifiedStrategy(String unqualifiedStrategy) {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }

    public TestingTask getTestingTask() {
        return testingTask;
    }

    public void setTestingTask(TestingTask testingTask) {
        this.testingTask = testingTask;
    }
}
