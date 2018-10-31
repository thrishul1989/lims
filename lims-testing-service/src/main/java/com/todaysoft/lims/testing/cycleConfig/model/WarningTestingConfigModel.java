package com.todaysoft.lims.testing.cycleConfig.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingMethod;

public class WarningTestingConfigModel
{
    private String id;
    //private String testingMethodId;
    
    private TestingMethod testingMethod;
    
    private String configName;
    
    private String configDesc;
    
    private int threshold;
    
    private Date createTime;
    
    private boolean delFlag;
    
    private Date deleteTime;
    
    private String testingMethodName;
    
    private Integer methodType;
    
    private List<ScheduleTestingNodeConfigModel> nodes;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public TestingMethod getTestingMethod()
    {
        return testingMethod;
    }

    public void setTestingMethod(TestingMethod testingMethod)
    {
        this.testingMethod = testingMethod;
    }

    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    public String getConfigDesc()
    {
        return configDesc;
    }

    public void setConfigDesc(String configDesc)
    {
        this.configDesc = configDesc;
    }

    public int getThreshold()
    {
        return threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public boolean isDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getTestingMethodName()
    {
        return testingMethodName;
    }

    public void setTestingMethodName(String testingMethodName)
    {
        this.testingMethodName = testingMethodName;
    }

    public Integer getMethodType()
    {
        return methodType;
    }

    public void setMethodType(Integer methodType)
    {
        this.methodType = methodType;
    }

    public List<ScheduleTestingNodeConfigModel> getNodes()
    {
        return nodes;
    }

    public void setNodes(List<ScheduleTestingNodeConfigModel> nodes)
    {
        this.nodes = nodes;
    }
    
}
