package com.todaysoft.lims.testing.pcrngstest.context;

import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;

import java.math.BigDecimal;

public class PcrNgsTestSubmitNextTaskContext
{
    private String temporaryId;

    private String scheduleId;

    private String inputSampleId;

    private String taskSemantic;

    private String taskName;

    private TestingTask testingTaskEntity;

    private TestingSample testingSample;

    private String pcrTaskCode;//pcr任务编号

    private String pcrTestCode;//pcr试验编号

    private String forwardPrimerLocationTemp;//引物临时位置

    private TestingTaskRunVariable testingTaskVariablesEntity;

    private String combineCode;

    private Integer libraryIndex;

    private BigDecimal volume;

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

    public Integer getLibraryIndex() {
        return libraryIndex;
    }

    public void setLibraryIndex(Integer libraryIndex) {
        this.libraryIndex = libraryIndex;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
