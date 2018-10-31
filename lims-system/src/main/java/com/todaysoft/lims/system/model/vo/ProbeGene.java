package com.todaysoft.lims.system.model.vo;

import com.todaysoft.lims.system.model.vo.disease.Gene;

public class ProbeGene
{
    private String id;
    
    private Probe probe;
    
    private Gene gene;
    
    public Probe getProbe()
    {
        return probe;
    }
    
    public void setProbe(Probe probe)
    {
        this.probe = probe;
    }
    
    public Gene getGene()
    {
        return gene;
    }
    
    public void setGene(Gene gene)
    {
        this.gene = gene;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
}
