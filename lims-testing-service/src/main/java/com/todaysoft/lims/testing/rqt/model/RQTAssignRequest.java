package com.todaysoft.lims.testing.rqt.model;

import java.math.BigDecimal;
import java.util.List;

public class RQTAssignRequest
{
    private String reagentKitId;
    
    private String testerId;
    
    private BigDecimal designDatasize;
    
    private String description;
    
    private List<RQTAssignTaskArgs> tasks;
    
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
    
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    public BigDecimal getDesignDatasize()
    {
        return designDatasize;
    }
    
    public void setDesignDatasize(BigDecimal designDatasize)
    {
        this.designDatasize = designDatasize;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<RQTAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<RQTAssignTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
