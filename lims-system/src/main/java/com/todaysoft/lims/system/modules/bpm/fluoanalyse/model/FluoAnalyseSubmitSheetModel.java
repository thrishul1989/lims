package com.todaysoft.lims.system.modules.bpm.fluoanalyse.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.longqc.model.LongQcSubmitTaskModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.DataAnalysisParseModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;

public class FluoAnalyseSubmitSheetModel
{
    private String id;
    
    private List<FluoAnalyseSubmitTaskModel> tasks;

    private List<FluoDataSubmitTaskSuccessArgs> successArgs;

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
