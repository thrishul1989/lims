package com.todaysoft.lims.testing.pcrngsdata.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;

public class PcrNgsDataSubmitTaskContext
{
    private Integer result;

    private String dispose;

    private String remark;

    private String combineType; //纯合/杂合

    private String mutationSource; //突变来源

    private TestingTask testingTaskEntity;

    private PcrNgsDataSubmitTaskArgs pcrNgsDataSubmitTaskArgs;

    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
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

    public PcrNgsDataSubmitTaskArgs getPcrNgsDataSubmitTaskArgs() {
        return pcrNgsDataSubmitTaskArgs;
    }

    public void setPcrNgsDataSubmitTaskArgs(PcrNgsDataSubmitTaskArgs pcrNgsDataSubmitTaskArgs) {
        this.pcrNgsDataSubmitTaskArgs = pcrNgsDataSubmitTaskArgs;
    }
}
