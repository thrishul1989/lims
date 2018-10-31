package com.todaysoft.lims.biology.model.request;

import java.util.List;

import com.todaysoft.lims.biology.model.entity.DataTemplateColumn;

public class DataTemplateRequest
{
    private String id;
    
    private String name;
    
    private Boolean delFlag;
    
    private Boolean priFlag;
    
    private String createId;
    
    private String createName;
    
    private List<DataTemplateColumn> columnList;
    
    private List<String> deleteIds;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Integer offset;
    
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
    
    public List<String> getDeleteIds()
    {
        return deleteIds;
    }
    
    public void setDeleteIds(List<String> deleteIds)
    {
        this.deleteIds = deleteIds;
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
    
    public Integer getOffset()
    {
        pageNo = null == pageNo ? 1 : pageNo;
        pageSize = null == pageSize ? 10 : pageSize;
        return (pageNo - 1) * pageSize;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
}
