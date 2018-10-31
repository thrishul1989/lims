package com.todaysoft.lims.product.entity.report;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_REPORT_DATA_TEMPLATE")
public class DataTemplate extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1322920421951108595L;
    
    private String name;
    
    private Integer startRowIndex;
    
    private List<DataTemplateColumn> columnList;
    
    private boolean delFlag;
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "START_ROW_INDEX")
    public Integer getStartRowIndex()
    {
        return startRowIndex;
    }
    
    public void setStartRowIndex(Integer startRowIndex)
    {
        this.startRowIndex = startRowIndex;
    }
    
    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<DataTemplateColumn> getColumnList()
    {
        return columnList;
    }
    
    public void setColumnList(List<DataTemplateColumn> columnList)
    {
        this.columnList = columnList;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
}
