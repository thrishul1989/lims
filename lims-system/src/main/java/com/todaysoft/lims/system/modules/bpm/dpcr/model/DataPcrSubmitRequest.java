package com.todaysoft.lims.system.modules.bpm.dpcr.model;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;

import java.util.List;

public class DataPcrSubmitRequest
{
    private String id;
    
    private List<DataPcrSubmitTaskArgs> tasks;

    private DataAnalysisParseModel parseModel;

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

    public DataAnalysisParseModel getParseModel() {
        return parseModel;
    }

    public void setParseModel(DataAnalysisParseModel parseModel) {
        this.parseModel = parseModel;
    }

    public List<TestingDataPic> getPicList() {
        return picList;
    }

    public void setPicList(List<TestingDataPic> picList) {
        this.picList = picList;
    }
}
