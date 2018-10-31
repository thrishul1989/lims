package com.todaysoft.lims.system.modules.bpm.qpcr.model;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;

import java.util.List;

public class QpcrSubmitRequest
{
    private String id;
    
    private List<QpcrSubmitTask> subTasks;

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
    
    public List<QpcrSubmitTask> getSubTasks()
    {
        return subTasks;
    }
    
    public void setSubTasks(List<QpcrSubmitTask> subTasks)
    {
        this.subTasks = subTasks;
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
