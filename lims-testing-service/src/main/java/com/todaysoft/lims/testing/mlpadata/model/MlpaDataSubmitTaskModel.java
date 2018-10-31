package com.todaysoft.lims.testing.mlpadata.model;

import java.math.BigDecimal;
import java.util.List;

public class MlpaDataSubmitTaskModel
{
    private String id;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;

    private String sampleCode;

    private List<MlpaDataSubmitTaskSuccessArgs> successArgs;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
    
    public String getUnqualifiedRemark()
    {
        return unqualifiedRemark;
    }
    
    public void setUnqualifiedRemark(String unqualifiedRemark)
    {
        this.unqualifiedRemark = unqualifiedRemark;
    }
    
    public String getUnqualifiedStrategy()
    {
        return unqualifiedStrategy;
    }
    
    public void setUnqualifiedStrategy(String unqualifiedStrategy)
    {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public List<MlpaDataSubmitTaskSuccessArgs> getSuccessArgs() {
        return successArgs;
    }

    public void setSuccessArgs(List<MlpaDataSubmitTaskSuccessArgs> successArgs) {
        this.successArgs = successArgs;
    }
}
