package com.todaysoft.lims.system.modules.bpm.libcap.model;

import java.util.List;

public class LibraryCaptureSheetTaskModel
{
    private String id;
    
    private String orderType;
    
    private String products;
    
    private String originalSampleCode;
    
    private String originalSampleName;
    
    private String originalSampleTypeName;
    
    private String sampleCode;
    
    private List<LibraryCaptureSheetGroupModel> assignedGroups;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public String getOriginalSampleCode()
    {
        return originalSampleCode;
    }
    
    public void setOriginalSampleCode(String originalSampleCode)
    {
        this.originalSampleCode = originalSampleCode;
    }
    
    public String getOriginalSampleName()
    {
        return originalSampleName;
    }
    
    public void setOriginalSampleName(String originalSampleName)
    {
        this.originalSampleName = originalSampleName;
    }
    
    public String getOriginalSampleTypeName()
    {
        return originalSampleTypeName;
    }
    
    public void setOriginalSampleTypeName(String originalSampleTypeName)
    {
        this.originalSampleTypeName = originalSampleTypeName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public List<LibraryCaptureSheetGroupModel> getAssignedGroups()
    {
        return assignedGroups;
    }
    
    public void setAssignedGroups(List<LibraryCaptureSheetGroupModel> assignedGroups)
    {
        this.assignedGroups = assignedGroups;
    }
}
