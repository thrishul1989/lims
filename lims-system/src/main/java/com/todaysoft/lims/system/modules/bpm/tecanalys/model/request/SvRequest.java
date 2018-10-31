package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;

public class SvRequest
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String analysisSampleId;
    
    private String gene;
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getAnalysisSampleId()
    {
        return analysisSampleId;
    }
    
    public void setAnalysisSampleId(String analysisSampleId)
    {
        this.analysisSampleId = analysisSampleId;
    }
}
