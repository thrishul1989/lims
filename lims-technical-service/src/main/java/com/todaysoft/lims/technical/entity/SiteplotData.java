package com.todaysoft.lims.technical.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="analysis-siteplot")
public class SiteplotData extends IdEntity
{
    private String analysisSampleId;
    private String chr;
    private String start;
    private String stop;
    private String geneSpan;
    private String gene;
    private String exon;
    private String len;
    private String readNum;
    public String getAnalysisSampleId()
    {
        return analysisSampleId;
    }
    public void setAnalysisSampleId(String analysisSampleId)
    {
        this.analysisSampleId = analysisSampleId;
    }
    public String getChr()
    {
        return chr;
    }
    public void setChr(String chr)
    {
        this.chr = chr;
    }
    public String getStart()
    {
        return start;
    }
    public void setStart(String start)
    {
        this.start = start;
    }
    public String getStop()
    {
        return stop;
    }
    public void setStop(String stop)
    {
        this.stop = stop;
    }
    public String getGeneSpan()
    {
        return geneSpan;
    }
    public void setGeneSpan(String geneSpan)
    {
        this.geneSpan = geneSpan;
    }
    public String getGene()
    {
        return gene;
    }
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    public String getExon()
    {
        return exon;
    }
    public void setExon(String exon)
    {
        this.exon = exon;
    }
    public String getLen()
    {
        return len;
    }
    public void setLen(String len)
    {
        this.len = len;
    }
    public String getReadNum()
    {
        return readNum;
    }
    public void setReadNum(String readNum)
    {
        this.readNum = readNum;
    }
    
}
