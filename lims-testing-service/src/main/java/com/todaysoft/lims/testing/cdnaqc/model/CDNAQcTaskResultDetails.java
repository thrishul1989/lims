package com.todaysoft.lims.testing.cdnaqc.model;

public class CDNAQcTaskResultDetails
{
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;
    
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
}
