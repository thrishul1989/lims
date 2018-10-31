package com.todaysoft.lims.testing.libcap.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingCaptureGroup;
import com.todaysoft.lims.testing.base.entity.TestingSample;

public class LibraryCaptureSubmitGroupContext
{
    private BigDecimal concn;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private TestingCaptureGroup group;
    
    private TestingSample outputSample;
    
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
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
    
    public TestingCaptureGroup getGroup()
    {
        return group;
    }
    
    public void setGroup(TestingCaptureGroup group)
    {
        this.group = group;
    }
    
    public TestingSample getOutputSample()
    {
        return outputSample;
    }
    
    public void setOutputSample(TestingSample outputSample)
    {
        this.outputSample = outputSample;
    }
}
