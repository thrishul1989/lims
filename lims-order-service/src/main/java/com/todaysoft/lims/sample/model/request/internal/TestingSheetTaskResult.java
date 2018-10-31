package com.todaysoft.lims.sample.model.request.internal;

public class TestingSheetTaskResult
{
    private Integer id;
    
    private String activitiTaskId;
    
    private Integer result;
    
    // 其他的失败原因、成功提取的量等字段
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getActivitiTaskId()
    {
        return activitiTaskId;
    }
    
    public void setActivitiTaskId(String activitiTaskId)
    {
        this.activitiTaskId = activitiTaskId;
    }
    
    public Integer getResult()
    {
        return result;
    }
    
    public void setResult(Integer result)
    {
        this.result = result;
    }
}
