package com.todaysoft.lims.testing.dpcr.model;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;

import java.util.List;

public class DataPcrSubmitRequest
{
    private String id;
    
    private List<DataPcrSubmitTaskArgs> tasks;

    private List<TestingDataPic> picList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<DataPcrSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DataPcrSubmitTaskArgs> tasks)
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
