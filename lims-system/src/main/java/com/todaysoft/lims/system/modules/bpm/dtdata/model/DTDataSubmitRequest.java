package com.todaysoft.lims.system.modules.bpm.dtdata.model;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;

import java.util.List;

public class DTDataSubmitRequest
{
    private String id;
    
    private List<DTDataSubmitTaskArgs> tasks;

    private List<DTDataSubmitTaskSuccessArgs> successArgs;

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
    
    public List<DTDataSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DTDataSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }

    public List<DTDataSubmitTaskSuccessArgs> getSuccessArgs() {
        return successArgs;
    }

    public void setSuccessArgs(List<DTDataSubmitTaskSuccessArgs> successArgs) {
        this.successArgs = successArgs;
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
