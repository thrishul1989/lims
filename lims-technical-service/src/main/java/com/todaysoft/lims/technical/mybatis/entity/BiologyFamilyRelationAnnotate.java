package com.todaysoft.lims.technical.mybatis.entity;

public class BiologyFamilyRelationAnnotate
{
    private String id;
    
    private String ganalysisFamilyId;
    
    private String cnv;
    
    private String makeCptyNumberVariation;
    
    private String snvJson;
    
    private String sv;
    
    private String svJson;
    
    private String regioncountDmdexon;
    
    private String statisticsDmdexon;
    
    private Integer status;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getGanalysisFamilyId()
    {
        return ganalysisFamilyId;
    }
    
    public void setGanalysisFamilyId(String ganalysisFamilyId)
    {
        this.ganalysisFamilyId = ganalysisFamilyId;
    }
    
    public String getCnv()
    {
        return cnv;
    }
    
    public void setCnv(String cnv)
    {
        this.cnv = cnv;
    }
    
    public String getMakeCptyNumberVariation()
    {
        return makeCptyNumberVariation;
    }
    
    public void setMakeCptyNumberVariation(String makeCptyNumberVariation)
    {
        this.makeCptyNumberVariation = makeCptyNumberVariation;
    }
    
    public String getSnvJson()
    {
        return snvJson;
    }
    
    public void setSnvJson(String snvJson)
    {
        this.snvJson = snvJson;
    }
    
    public String getSv()
    {
        return sv;
    }
    
    public void setSv(String sv)
    {
        this.sv = sv;
    }
    
    public String getSvJson()
    {
        return svJson;
    }
    
    public void setSvJson(String svJson)
    {
        this.svJson = svJson;
    }
    
    public String getRegioncountDmdexon()
    {
        return regioncountDmdexon;
    }
    
    public void setRegioncountDmdexon(String regioncountDmdexon)
    {
        this.regioncountDmdexon = regioncountDmdexon;
    }
    
    public String getStatisticsDmdexon()
    {
        return statisticsDmdexon;
    }
    
    public void setStatisticsDmdexon(String statisticsDmdexon)
    {
        this.statisticsDmdexon = statisticsDmdexon;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
}