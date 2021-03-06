package com.todaysoft.lims.reagent.model.request;

public class ProbePagingRequest
{
    private int pageNo;
    
    private int pageSize;
    
    private String code;
    
    private String name;
    
    private Integer status;
    
    private Integer delFlag;
    
    private String testingPlatForm;
    
    public String getTestingPlatForm()
    {
        return testingPlatForm;
    }

    public void setTestingPlatForm(String testingPlatForm)
    {
        this.testingPlatForm = testingPlatForm;
    }

    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
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
