package com.todaysoft.lims.technical.model.response;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.technical.model.request.ClaimTemplateColumnModel;

public class ClaimTemplateModel
{
    private String id;
    
    private String name;
    
    private String symbol;
    
    private String explanation;
    
    private String templateId;
    
    private Boolean delFlag;
    
    private Date createTime;
    
    private String createId;
    
    private String createName;
    
    private List<ClaimTemplateColumnModel> columnList;
    
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
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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
    
    public List<ClaimTemplateColumnModel> getColumnList()
    {
        return columnList;
    }
    
    public void setColumnList(List<ClaimTemplateColumnModel> columnList)
    {
        this.columnList = columnList;
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