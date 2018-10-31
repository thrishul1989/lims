package com.todaysoft.lims.testing.technicalanaly.model;

import com.google.common.collect.Lists;
import com.todaysoft.lims.testing.base.entity.TestingDataPic;

import java.util.List;

public class TechnicalAnalySubmitRequest
{
    private String id;

    private Integer submitType;//1是提交任务单，2是提交本地任务

    private List<String> taskList = Lists.newArrayList();//提交的任务id
    
    private List<TechnicalAnalySubmitRecord> records = Lists.newArrayList();

    private List<TestingDataPic> picList = Lists.newArrayList();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<TechnicalAnalySubmitRecord> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<TechnicalAnalySubmitRecord> records)
    {
        this.records = records;
    }

    public List<TestingDataPic> getPicList() {
        return picList;
    }

    public void setPicList(List<TestingDataPic> picList) {
        this.picList = picList;
    }

    public Integer getSubmitType() {
        return submitType;
    }

    public void setSubmitType(Integer submitType) {
        this.submitType = submitType;
    }

    public List<String> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<String> taskList) {
        this.taskList = taskList;
    }
}
