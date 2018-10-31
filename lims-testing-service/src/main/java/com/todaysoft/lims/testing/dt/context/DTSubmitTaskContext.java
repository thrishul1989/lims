package com.todaysoft.lims.testing.dt.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class DTSubmitTaskContext
{
    private Integer result;

    private String remark;

    private String dispose;

    private String pcrTaskCode;//pcr任务编号

    private String pcrTestCode;//pcr试验编号

    private String forwardPrimerLocationTemp;//引物临时位置
    
    private TestingTask testingTaskEntity;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDispose() {
        return dispose;
    }

    public void setDispose(String dispose) {
        this.dispose = dispose;
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

    public String getForwardPrimerLocationTemp() {
        return forwardPrimerLocationTemp;
    }

    public void setForwardPrimerLocationTemp(String forwardPrimerLocationTemp) {
        this.forwardPrimerLocationTemp = forwardPrimerLocationTemp;
    }
}
