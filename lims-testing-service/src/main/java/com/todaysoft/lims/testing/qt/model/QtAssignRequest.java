package com.todaysoft.lims.testing.qt.model;

import java.util.List;

public class QtAssignRequest
{
    private String id;
    
    private String reagentKitId;
    
    private String testerId;
    
    private String description;
    
    private List<QtAssignReferenceArgs> references;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
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
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<QtAssignReferenceArgs> getReferences()
    {
        return references;
    }
    
    public void setReferences(List<QtAssignReferenceArgs> references)
    {
        this.references = references;
    }
}
