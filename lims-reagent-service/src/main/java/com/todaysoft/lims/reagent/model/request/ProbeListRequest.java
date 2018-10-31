package com.todaysoft.lims.reagent.model.request;

public class ProbeListRequest
{
    
    private String code;
    
    private String name;
    
    private Integer delFlag;
    
    private String testingPlatForm;
    
    private Integer enabled;
    
    public Integer getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Integer enabled)
    {
        this.enabled = enabled;
    }

    public String getTestingPlatForm()
    {
        return testingPlatForm;
    }
    
    public void setTestingPlatForm(String testingPlatForm)
    {
        this.testingPlatForm = testingPlatForm;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
}
