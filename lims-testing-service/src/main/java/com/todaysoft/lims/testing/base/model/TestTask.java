package com.todaysoft.lims.testing.base.model;

import java.util.Date;

public class TestTask
{
    private String id;
    
    private String method;
    
    private String activitiTaskId;
    
    private String semantic;
    
    private String testingCode;
    
    private String testingName;
    
    private String assignerName;//下单人姓名
    
    private Date assignTime;//下单时间
    
    private String remark;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public void setMethod(String method)
    {
        this.method = method;
    }
    
    public String getActivitiTaskId()
    {
        return activitiTaskId;
    }
    
    public void setActivitiTaskId(String activitiTaskId)
    {
        this.activitiTaskId = activitiTaskId;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getTestingName()
    {
        return testingName;
    }
    
    public void setTestingName(String testingName)
    {
        this.testingName = testingName;
    }
    
    public String getAssignerName()
    {
        return assignerName;
    }
    
    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }
    
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
