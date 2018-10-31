package com.todaysoft.lims.testing.dtdata.model;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;

import java.util.List;

public class DTDataSubmitRequest
{
    private String id;
    
    private List<DTDataSubmitTaskArgs> tasks;

    private List<TestingDataPic> picList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<DTDataSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DTDataSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }

    public List<TestingDataPic> getPicList() {
        return picList;
    }

    public void setPicList(List<TestingDataPic> picList) {
        this.picList = picList;
    }
}
