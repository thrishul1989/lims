package com.todaysoft.lims.system.modules.bpm.libcap.model;

import java.math.BigDecimal;
import java.util.List;

public class LibraryCaptureAssignRequest
{
    private String reagentKit;
    
    private String testerId;
    
    private String methods;
    
    private String description;
    
    private String batchCode;
    
    private BigDecimal libraryInputSize;
    
    private List<LibraryCaptureAssignGroupArgs> groups;
    
    public String getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(String reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    public String getMethods()
    {
        return methods;
    }
    
    public void setMethods(String methods)
    {
        this.methods = methods;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getBatchCode()
    {
        return batchCode;
    }
    
    public void setBatchCode(String batchCode)
    {
        this.batchCode = batchCode;
    }
    
    public BigDecimal getLibraryInputSize()
    {
        return libraryInputSize;
    }
    
    public void setLibraryInputSize(BigDecimal libraryInputSize)
    {
        this.libraryInputSize = libraryInputSize;
    }
    
    public List<LibraryCaptureAssignGroupArgs> getGroups()
    {
        return groups;
    }
    
    public void setGroups(List<LibraryCaptureAssignGroupArgs> groups)
    {
        this.groups = groups;
    }
    
}
