package com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LibraryCaptureSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String reagentKitName;
    
    private String methods;
    
    private String description;
    
    private String batchCode;
    
    private BigDecimal libraryInputSize;
    
    private List<LibraryCaptureSheetGroup> groups;
    
    private String testerName;
    
    private Date submitTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    public String getReagentKitName()
    {
        return reagentKitName;
    }
    
    public void setReagentKitName(String reagentKitName)
    {
        this.reagentKitName = reagentKitName;
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
    
    public List<LibraryCaptureSheetGroup> getGroups()
    {
        return groups;
    }
    
    public void setGroups(List<LibraryCaptureSheetGroup> groups)
    {
        this.groups = groups;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
}
