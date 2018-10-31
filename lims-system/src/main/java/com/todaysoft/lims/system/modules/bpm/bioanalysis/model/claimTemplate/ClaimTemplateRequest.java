package com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate;

import java.util.List;

public class ClaimTemplateRequest
{
    private String id;
    
    private String name;
    
    private String symbol;
    
    private String explanation;
    
    private String templateId;
    
    private Boolean delFlag;
    
    private String createId;
    
    private String createName;
    
    private List<ClaimTemplateColumn> columnList;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getExplanation()
    {
        return explanation;
    }
    
    public void setExplanation(String explanation)
    {
        this.explanation = explanation;
    }
    
    public String getTemplateId()
    {
        return templateId;
    }
    
    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }
    
    public Boolean getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public List<ClaimTemplateColumn> getColumnList()
    {
        return columnList;
    }
    
    public void setColumnList(List<ClaimTemplateColumn> columnList)
    {
        this.columnList = columnList;
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
    
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    
}
