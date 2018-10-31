package com.todaysoft.lims.maintain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;

@Entity
@Table(name = "LIMS_REPORT_DATA_TEMPLATE_COLUMN")
public class DataTemplateColumn extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 6543944247081790861L;
    
    private DataTemplate template;
    
    private Integer columnIndex;
    
    private String columnName;
    
    private String semantic;//特殊语义标识
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID")
    @JsonIgnore
    public DataTemplate getTemplate()
    {
        return template;
    }
    
    public void setTemplate(DataTemplate template)
    {
        this.template = template;
    }
    
    @Column(name = "COLUMN_INDEX")
    public Integer getColumnIndex()
    {
        return columnIndex;
    }
    
    public void setColumnIndex(Integer columnIndex)
    {
        this.columnIndex = columnIndex;
    }
    
    @Column(name = "COLUMN_NAME")
    public String getColumnName()
    {
        return columnName;
    }
    
    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }
    
    @Column(name = "SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
}
