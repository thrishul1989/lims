package com.todaysoft.lims.testing.base.model;

import java.util.List;

public class TestingMethod
{
    private String id;
    
    private String name;
    
    private List<TestingNode> nodes;
    
    private List<TestingProbe> probes;
    
    private List<AnalysingCoordinate> coordinates;
    
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
    
    public List<TestingNode> getNodes()
    {
        return nodes;
    }
    
    public void setNodes(List<TestingNode> nodes)
    {
        this.nodes = nodes;
    }
    
    public List<TestingProbe> getProbes()
    {
        return probes;
    }
    
    public void setProbes(List<TestingProbe> probes)
    {
        this.probes = probes;
    }
    
    public List<AnalysingCoordinate> getCoordinates()
    {
        return coordinates;
    }
    
    public void setCoordinates(List<AnalysingCoordinate> coordinates)
    {
        this.coordinates = coordinates;
    }
}
