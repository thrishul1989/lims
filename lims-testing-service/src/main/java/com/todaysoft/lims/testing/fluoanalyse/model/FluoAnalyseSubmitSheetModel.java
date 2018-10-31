package com.todaysoft.lims.testing.fluoanalyse.model;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;

import java.util.List;

public class FluoAnalyseSubmitSheetModel
{
    private String id;
    
    private List<FluoAnalyseSubmitTaskModel> tasks;

    private List<FluoDataSubmitTaskSuccessArgs> successArgs;

    private List<TestingDataPic> picList;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<FluoAnalyseSubmitTaskModel> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FluoAnalyseSubmitTaskModel> tasks)
    {
        this.tasks = tasks;
    }

    public List<FluoDataSubmitTaskSuccessArgs> getSuccessArgs() {
        return successArgs;
    }

    public void setSuccessArgs(List<FluoDataSubmitTaskSuccessArgs> successArgs) {
        this.successArgs = successArgs;
    }

    public List<TestingDataPic> getPicList() {
        return picList;
    }

    public void setPicList(List<TestingDataPic> picList) {
        this.picList = picList;
    }
}
