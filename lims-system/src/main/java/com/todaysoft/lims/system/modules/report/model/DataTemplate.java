package com.todaysoft.lims.system.modules.report.model;

import com.google.common.collect.Maps;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;

import java.util.List;
import java.util.Map;

public class DataTemplate
{
    private String id;
    
    private String name;
    
    private Integer startRowIndex;

    private Integer titleIndex;
    
    private List<DataTemplateColumn> columnList;

    private TestingMethod testingMethod;//检测方法
    
    private boolean delFlag;
    
    private int pageNo;
    
    private int pageSize;

    private Map<String,String> map = Maps.newHashMap();//列名key 特殊语义value

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
    
    public Integer getStartRowIndex()
    {
        return startRowIndex;
    }
    
    public void setStartRowIndex(Integer startRowIndex)
    {
        this.startRowIndex = startRowIndex;
    }
    
    public List<DataTemplateColumn> getColumnList()
    {
        return columnList;
    }
    
    public void setColumnList(List<DataTemplateColumn> columnList)
    {
        this.columnList = columnList;
    }
    
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public TestingMethod getTestingMethod() {
        return testingMethod;
    }

    public void setTestingMethod(TestingMethod testingMethod) {
        this.testingMethod = testingMethod;
    }

    public Integer getTitleIndex() {
        return titleIndex;
    }

    public void setTitleIndex(Integer titleIndex) {
        this.titleIndex = titleIndex;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
