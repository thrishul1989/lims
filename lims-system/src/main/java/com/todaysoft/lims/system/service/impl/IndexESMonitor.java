package com.todaysoft.lims.system.service.impl;

public class IndexESMonitor
{
    private Monitor products;
    
    private Monitor genes;
    
    private Monitor diseases;
    
    private Monitor phenotypes;
    
    public Monitor getProducts()
    {
        return products;
    }
    
    public void setProducts(Monitor products)
    {
        this.products = products;
    }
    
    public Monitor getGenes()
    {
        return genes;
    }
    
    public void setGenes(Monitor genes)
    {
        this.genes = genes;
    }
    
    public Monitor getDiseases()
    {
        return diseases;
    }
    
    public void setDiseases(Monitor diseases)
    {
        this.diseases = diseases;
    }
    
    public Monitor getPhenotypes()
    {
        return phenotypes;
    }
    
    public void setPhenotypes(Monitor phenotypes)
    {
        this.phenotypes = phenotypes;
    }
}
