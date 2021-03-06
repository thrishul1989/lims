package com.todaysoft.lims.testing.dpcrsanger.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;

public class DataPcrSangerSubmitScheduleContext
{
    private String id;

    private Integer result;

    private String dispose;

    private String remark;

    private String combineType; //纯合/杂合

    private String mutationSource; //突变来源

    private String nextNodeInputSampleId;

    private TaskConfig nextNodeConfig;

    private TestingSchedule testingScheduleEntity;

    private TestingScheduleActive testingScheduleActiveEntity;

    private TestingTask testingTask;

    private Integer flag;//是否删除掉的activeTask 不进入下一步

    private String primerId;

    private String combineCode;

    public String getPrimerId() {
        return primerId;
    }

    public void setPrimerId(String primerId) {
        this.primerId = primerId;
    }

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDispose() {
        return dispose;
    }

    public void setDispose(String dispose) {
        this.dispose = dispose;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCombineType() {
        return combineType;
    }

    public void setCombineType(String combineType) {
        this.combineType = combineType;
    }

    public String getMutationSource() {
        return mutationSource;
    }

    public void setMutationSource(String mutationSource) {
        this.mutationSource = mutationSource;
    }

    public TestingTask getTestingTask() {
        return testingTask;
    }

    public void setTestingTask(TestingTask testingTask) {
        this.testingTask = testingTask;
    }
}
