package com.todaysoft.lims.testing.cycleConfig.service.request;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.testing.cycleConfig.model.ScheduleTestingNodeConfigModel;

public class TaskConfigRequest
{
    private String id;
 private String testingMethodId;
    
    private String configName;
    
    private String configDesc;
    
    private int threshold;
    
    private Date createTime;
    
    private boolean delFlag;
    
    private Date deleteTime;
    
    private List<ScheduleTestingNodeConfigModel> nodes;
    
    private String content;
    
    private String process;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProcess()
    {
        return process;
    }

    public void setProcess(String process)
    {
        this.process = process;
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

    public List<ScheduleTestingNodeConfigModel> getNodes()
    {
        return nodes;
    }

    public void setNodes(List<ScheduleTestingNodeConfigModel> nodes)
    {
        this.nodes = nodes;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    };

    
}
