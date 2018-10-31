package com.todaysoft.lims.product.model;

import java.util.List;

public class ProductSimpleModel
{
    private String id;
    
    private String code;
    
    private String name;
    
    private List<String> technicalPrincipals;
    
    private Integer testingDuration;
    
    private String testingSampleType;
    
    private List<String> supportedSampleTypes;
    
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
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public List<String> getTechnicalPrincipals()
    {
        return technicalPrincipals;
    }
    
    public void setTechnicalPrincipals(List<String> technicalPrincipals)
    {
        this.technicalPrincipals = technicalPrincipals;
    }
    
    public Integer getTestingDuration()
    {
        return testingDuration;
    }
    
    public void setTestingDuration(Integer testingDuration)
    {
        this.testingDuration = testingDuration;
    }
    
    public String getTestingSampleType()
    {
        return testingSampleType;
    }
    
    public void setTestingSampleType(String testingSampleType)
    {
        this.testingSampleType = testingSampleType;
    }
    
    public List<String> getSupportedSampleTypes()
    {
        return supportedSampleTypes;
    }
    
    public void setSupportedSampleTypes(List<String> supportedSampleTypes)
    {
        this.supportedSampleTypes = supportedSampleTypes;
    }
}
