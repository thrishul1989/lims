package com.todaysoft.lims.system.modules.bpm.dtdata.model;

import java.util.Date;

public class DTDataTask
{
    private String id;
    
    private String sampleName;
    
    private String sampleCode;
    
    private String dtTaskCode;//pcr任务编号
    
    private String products;
    
    private String productCode;
    
    private String combineCode;//合并号
    
    private String genes;
    
    private String primers;
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String remark;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private String dataTemplateId;
    
    private String productName;
    
    private Date plannedFinishDate;
    
    private Date startTime;
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getDataTemplateId()
    {
        return dataTemplateId;
    }
    
    public void setDataTemplateId(String dataTemplateId)
    {
        this.dataTemplateId = dataTemplateId;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public boolean isResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getDtTaskCode()
    {
        return dtTaskCode;
    }
    
    public void setDtTaskCode(String dtTaskCode)
    {
        this.dtTaskCode = dtTaskCode;
    }
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getGenes()
    {
        return genes;
    }
    
    public void setGenes(String genes)
    {
        this.genes = genes;
    }
    
    public String getPrimers()
    {
        return primers;
    }
    
    public void setPrimers(String primers)
    {
        this.primers = primers;
    }
    
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }
    
    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }
    
    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }
    
    public String getUrgentName()
    {
        return urgentName;
    }
    
    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
}