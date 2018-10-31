package com.todaysoft.lims.product.listener.ESModel;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder(toBuilder = true)
@AllArgsConstructor
public class ESDisease
{
    private String id;
    
    private String omim;
    
    private String name;
    
    private String enname;
    
    private String alias;
    
    private String genes;
    
    private String phenotypes;
    
    private String documents;
    
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
    
    public String getAlias()
    {
        return alias;
    }
    
    public void setAlias(String alias)
    {
        this.alias = alias;
    }
    
    public String getGenes()
    {
        return genes;
    }
    
    public void setGenes(String genes)
    {
        this.genes = genes;
    }
    
    public String getPhenotypes()
    {
        return phenotypes;
    }
    
    public void setPhenotypes(String phenotypes)
    {
        this.phenotypes = phenotypes;
    }
    
    public String getDocuments()
    {
        return documents;
    }
    
    public void setDocuments(String documents)
    {
        this.documents = documents;
    }
}
