package com.todaysoft.lims.technical.model.searcher;

public class SvSearcher
{
    private String analysisSampleId;
    
    private String gene;//只供regioncout查询
    
    public String getAnalysisSampleId()
    {
        return analysisSampleId;
    }
    
    public void setAnalysisSampleId(String analysisSampleId)
    {
        this.analysisSampleId = analysisSampleId;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
}
