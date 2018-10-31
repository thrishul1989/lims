package com.todaysoft.lims.system.model.searcher;


public class DocumentSearcher
{
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String title;
    
    private String genecode;
    
    private String genename;
    
    private String diseasecode;
    
    private String diseasename;
    
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getGenecode()
    {
        return genecode;
    }
    
    public void setGenecode(String genecode)
    {
        this.genecode = genecode;
    }
    
    public String getGenename()
    {
        return genename;
    }
    
    public void setGenename(String genename)
    {
        this.genename = genename;
    }
    
    public String getDiseasecode()
    {
        return diseasecode;
    }
    
    public void setDiseasecode(String diseasecode)
    {
        this.diseasecode = diseasecode;
    }
    
    public String getDiseasename()
    {
        return diseasename;
    }
    
    public void setDiseasename(String diseasename)
    {
        this.diseasename = diseasename;
    }
    
}
