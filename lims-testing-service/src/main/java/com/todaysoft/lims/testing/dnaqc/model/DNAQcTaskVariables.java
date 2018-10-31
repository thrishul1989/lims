package com.todaysoft.lims.testing.dnaqc.model;

import com.todaysoft.lims.testing.base.service.impl.TaskVariables;

public class DNAQcTaskVariables implements TaskVariables
{
    private String testingCode;
    
    private String location;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
}
