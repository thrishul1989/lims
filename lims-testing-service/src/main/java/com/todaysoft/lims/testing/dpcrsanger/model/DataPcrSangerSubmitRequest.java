package com.todaysoft.lims.testing.dpcrsanger.model;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;

import java.util.List;

public class DataPcrSangerSubmitRequest
{
    private String id;
    
    private List<DataPcrSangerSubmitTaskArgs> tasks;

    private List<TestingDataPic> picList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<DataPcrSangerSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DataPcrSangerSubmitTaskArgs> tasks)
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
