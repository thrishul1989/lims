package com.todaysoft.lims.testing.dtdata.context;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.dtdata.model.DTDataSubmitTaskSuccessArgs;

public class DTDataSubmitTaskContext
{
    private boolean qualified;

    private String unqualifiedRemark;

    private String unqualifiedStrategy;

    private String sampleCode;

    private List<DTDataSubmitTaskSuccessArgs> successArgs;

    private TestingTask testingTaskEntity;

    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
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

    public List<DTDataSubmitTaskSuccessArgs> getSuccessArgs() {
        return successArgs;
    }

    public void setSuccessArgs(List<DTDataSubmitTaskSuccessArgs> successArgs) {
        this.successArgs = successArgs;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }
}
