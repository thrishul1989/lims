package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import com.google.common.collect.Lists;

import java.util.List;

public class TechnicalAnalySubmitForm
{
    private String id;
    
    private String token;

    private int submitType;//1是提交任务单，2是提交本地任务

    private String taskIds;//本次提交的任务id,隔开
    
    private List<TechnicalAnalySubmitRecordArgs> records = Lists.newArrayList();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public List<TechnicalAnalySubmitRecordArgs> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<TechnicalAnalySubmitRecordArgs> records)
    {
        this.records = records;
    }

    public int getSubmitType() {
        return submitType;
    }

    public void setSubmitType(int submitType) {
        this.submitType = submitType;
    }

    public String getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }

}
