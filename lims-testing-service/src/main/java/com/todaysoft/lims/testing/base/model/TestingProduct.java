package com.todaysoft.lims.testing.base.model;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class TestingProduct
{
    private String id;
    
    private String code;

    private String name;
    
    private String testingSampleType;
    
    private List<String> supportedSampleTypes;
    
    private List<TestingMethod> methods;
    
    public boolean isSampleSupport(String sampleType)
    {
        // 未分配具体类型默认表示支持所有类型
        if (CollectionUtils.isEmpty(supportedSampleTypes))
        {
            return true;
        }
        
        for (String type : supportedSampleTypes)
        {
            if (type.equals(sampleType))
            {
                return true;
            }
        }
        
        return false;
    }
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupportedSampleTypes(List<String> supportedSampleTypes)
    {
        this.supportedSampleTypes = supportedSampleTypes;
    }
    
    public List<TestingMethod> getMethods()
    {
        return methods;
    }
    
    public void setMethods(List<TestingMethod> methods)
    {
        this.methods = methods;
    }
}
