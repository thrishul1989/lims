package com.todaysoft.lims.testing.base.entity;

import com.google.common.collect.Maps;
import com.todaysoft.lims.persist.UuidKeyEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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

    private Integer titleIndex;
    
    private List<DataTemplateColumn> columnList;
    
    private boolean delFlag;

    private TestingMethod testingMethod;//检测方法

    private Map<String,String> map = Maps.newHashMap();//列名key 特殊语义value
    
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

    @Column(name = "TITLE_INDEX")
    public Integer getTitleIndex() {
        return titleIndex;
    }

    public void setTitleIndex(Integer titleIndex) {
        this.titleIndex = titleIndex;
    }

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy("columnIndex asc")
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

    @OneToOne
    @JoinColumn(name = "TESTING_METHOD_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingMethod getTestingMethod() {
        return testingMethod;
    }

    public void setTestingMethod(TestingMethod testingMethod) {
        this.testingMethod = testingMethod;
    }

    @Transient
    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
