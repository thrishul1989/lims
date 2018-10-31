package com.todaysoft.lims.system.model.vo.disease;

public class DiseaseGene
{
    
    private String id;
    
    private Gene gene;
    
    private Disease disease;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Gene getGene()
    {
        return gene;
    }
    
    public void setGene(Gene gene)
    {
        this.gene = gene;
    }
    
    public Disease getDisease()
    {
        return disease;
    }
    
    public void setDisease(Disease disease)
    {
        this.disease = disease;
    }
    
}
