package com.todaysoft.lims.technical.model.response;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.DataTemplateColumn;

public class DataTemplateModel
{
    private String id;
    
    private String name;
    
    private Boolean delFlag;
    
    private Boolean priFlag;
    
    private String createId;
    
    private Date createTime;
    
    private String createName;
    
    private List<DataTemplateColumn> columnList;
    
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
    
    public Boolean getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public Boolean getPriFlag()
    {
        return priFlag;
    }
    
    public void setPriFlag(Boolean priFlag)
    {
        this.priFlag = priFlag;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public List<DataTemplateColumn> getColumnList()
    {
        return columnList;
    }
    
    public void setColumnList(List<DataTemplateColumn> columnList)
    {
        this.columnList = columnList;
    }
    
}
