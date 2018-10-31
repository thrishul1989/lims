package com.todaysoft.lims.kbm.es.model;

public class Phenotype
{
    private String id;
    
    private String omim;
    
    private String name;
    
    private String enname;
    
    private String diseases;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOmim()
    {
        return omim;
    }
    
    public void setOmim(String omim)
    {
        this.omim = omim;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getEnname()
    {
        return enname;
    }
    
    public void setEnname(String enname)
    {
        this.enname = enname;
    }
    
    public String getDiseases()
    {
        return diseases;
    }
    
    public void setDiseases(String diseases)
    {
        this.diseases = diseases;
    }
}
