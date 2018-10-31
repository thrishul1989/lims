package com.todaysoft.lims.sample.model;

import java.io.Serializable;
import java.util.List;

public class TestingTaskConfig implements Serializable
{
    private static final long serialVersionUID = -5005357417641207224L;
    
    private String id;
    
    private String name;
    
    private String semantic;
    
    private List<String> candidates;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public List<String> getCandidates()
    {
        return candidates;
    }
    
    public void setCandidates(List<String> candidates)
    {
        this.candidates = candidates;
    }
}
