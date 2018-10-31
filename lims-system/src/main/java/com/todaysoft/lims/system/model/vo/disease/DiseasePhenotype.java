package com.todaysoft.lims.system.model.vo.disease;


public class DiseasePhenotype
{
    
    private String id;
    
    private String dependency;
    
    private String phenotype;
    
    private String disease;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getDependency()
    {
        return dependency;
    }
    
    public void setDependency(String dependency)
    {
        this.dependency = dependency;
    }
    
    public String getPhenotype()
    {
        return phenotype;
    }
    
    public void setPhenotype(String phenotype)
    {
        this.phenotype = phenotype;
    }
    
    public String getDisease()
    {
        return disease;
    }
    
    public void setDisease(String disease)
    {
        this.disease = disease;
    }
    
}
