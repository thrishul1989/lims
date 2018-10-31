package com.todaysoft.lims.testing.firstpcrsanger.context;

import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;

public class FirstPcrSangerSubmitNextTaskContext
{
    private String temporaryId;

    private String scheduleId;

    private String inputSampleId;

    private String taskSemantic;

    private String taskName;

    private TestingTask testingTaskEntity;

    private TestingTask preTestingTaskEntity;

    private TestingSample testingSample;

    private String pcrTaskCode;//pcr任务编号

    private String pcrTestCode;//pcr试验编号

    private String forwardPrimerLocationTemp;//引物临时位置

    private String combineCode;

    private String primerId;

    private Integer runningStatus;

    private TestingTaskRunVariable testingTaskVariablesEntity;

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

    public String getPcrTaskCode() {
        return pcrTaskCode;
    }

    public void setPcrTaskCode(String pcrTaskCode) {
        this.pcrTaskCode = pcrTaskCode;
    }

    public String getPcrTestCode() {
        return pcrTestCode;
    }

    public void setPcrTestCode(String pcrTestCode) {
        this.pcrTestCode = pcrTestCode;
    }

    public TestingTaskRunVariable getTestingTaskVariablesEntity() {
        return testingTaskVariablesEntity;
    }

    public void setTestingTaskVariablesEntity(TestingTaskRunVariable testingTaskVariablesEntity) {
        this.testingTaskVariablesEntity = testingTaskVariablesEntity;
    }

    public String getForwardPrimerLocationTemp() {
        return forwardPrimerLocationTemp;
    }

    public void setForwardPrimerLocationTemp(String forwardPrimerLocationTemp) {
        this.forwardPrimerLocationTemp = forwardPrimerLocationTemp;
    }

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public String getPrimerId() {
        return primerId;
    }

    public void setPrimerId(String primerId) {
        this.primerId = primerId;
    }

    public TestingTask getPreTestingTaskEntity() {
        return preTestingTaskEntity;
    }

    public void setPreTestingTaskEntity(TestingTask preTestingTaskEntity) {
        this.preTestingTaskEntity = preTestingTaskEntity;
    }

    public Integer getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(Integer runningStatus) {
        this.runningStatus = runningStatus;
    }
}
