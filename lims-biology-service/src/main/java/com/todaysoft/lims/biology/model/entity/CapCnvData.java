package com.todaysoft.lims.biology.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "analysis-capcnv")
public class CapCnvData
{
    
    private String id;
    
    private String analysisSampleId;//待分析样本 的记录ID
    
    private String chrLocation;
    
    private String gene;
    
    private String area;
    
    private Double copyNumber;
    
    private Double pValue;
    
    public String getAnalysisSampleId()
    {
        return analysisSampleId;
    }
    
    public void setAnalysisSampleId(String analysisSampleId)
    {
        this.analysisSampleId = analysisSampleId;
    }
    
    public String getChrLocation()
    {
        return chrLocation;
    }
    
    public void setChrLocation(String chrLocation)
    {
        this.chrLocation = chrLocation;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getArea()
    {
        return area;
    }
    
    public void setArea(String area)
    {
        this.area = area;
    }
    
    public Double getCopyNumber()
    {
        return copyNumber;
    }
    
    public void setCopyNumber(Double copyNumber)
    {
        this.copyNumber = copyNumber;
    }
    
    public Double getpValue()
    {
        return pValue;
    }
    
    public void setpValue(Double pValue)
    {
        this.pValue = pValue;
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
