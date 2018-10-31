package com.todaysoft.lims.testing.pcrngsdata.model;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;

import java.util.List;

public class PcrNgsDataSubmitRequest
{
    private String id;
    
    private List<PcrNgsDataSubmitTaskArgs> tasks;

    private List<TestingDataPic> picList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<PcrNgsDataSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsDataSubmitTaskArgs> tasks)
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
