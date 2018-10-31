package com.todaysoft.lims.system.modules.bpm.cycleConfig.model;


public class TestingConfigSearcher 
{
    private String id;
    
    private String testingMethodId;
    
    private String configName;
    
    private String velidateConfigName;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTestingMethodId()
    {
        return testingMethodId;
    }

    public void setTestingMethodId(String testingMethodId)
    {
        this.testingMethodId = testingMethodId;
    }

    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getVelidateConfigName()
    {
        return velidateConfigName;
    }

    public void setVelidateConfigName(String velidateConfigName)
    {
        this.velidateConfigName = velidateConfigName;
    }
    
}
