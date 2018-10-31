package com.todaysoft.lims.testing.firstpcr.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;

public class FirstPcrSubmitScheduleContext
{
    private String id;

    private Integer result;

    private String remark;

    private String dispose;//失败情况下 处理方式 一次PCR，重新设计引物,取消实验

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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getDispose() {
        return dispose;
    }

    public void setDispose(String dispose) {
        this.dispose = dispose;
    }

    public TestingTask getTestingTask() {
        return testingTask;
    }

    public void setTestingTask(TestingTask testingTask) {
        this.testingTask = testingTask;
    }
}
