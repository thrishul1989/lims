package com.todaysoft.lims.testing.qpcr.model;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;

import java.util.List;

public class QpcrSubmitRequest
{
    private String id;
    
    private List<QpcrSubmitTask> subTasks;

    private List<TestingDataPic> picList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<QpcrSubmitTask> getSubTasks()
    {
        return subTasks;
    }
    
    public void setSubTasks(List<QpcrSubmitTask> subTasks)
    {
        this.subTasks = subTasks;
    }

    public List<TestingDataPic> getPicList() {
        return picList;
    }

    public void setPicList(List<TestingDataPic> picList) {
        this.picList = picList;
    }
}
