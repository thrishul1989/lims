package com.todaysoft.lims.testing.dpcrsanger.context;

import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingTask;

public class DataPcrSangerSubmitNextTaskContext
{
    private String temporaryId;

    private String scheduleId;

    private String inputSampleId;

    private String taskSemantic;

    private String taskName;

    private TestingTask testingTaskEntity;

    private TestingSample testingSample;

    public String getTemporaryId()
    {
        return temporaryId;
    }

    public void setTemporaryId(String temporaryId)
    {
        this.temporaryId = temporaryId;
    }

    public String getScheduleId()
    {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }

    public String getInputSampleId()
    {
        return inputSampleId;
    }

    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
    }

    public String getTaskSemantic()
    {
        return taskSemantic;
    }

    public void setTaskSemantic(String taskSemantic)
    {
        this.taskSemantic = taskSemantic;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }

    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }

    public TestingSample getTestingSample() {
        return testingSample;
    }

    public void setTestingSample(TestingSample testingSample) {
        this.testingSample = testingSample;
    }
}
