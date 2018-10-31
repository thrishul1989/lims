package com.todaysoft.lims.system.modules.bpm.mlpadata.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.longqc.model.LongQcSubmitTaskModel;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TestingDataPic;

public class MlpaDataSubmitSheetModel
{
    private String id;

    private int type;//1检测 2验证
    
    private List<MlpaDataSubmitTaskModel> tasks;

    private List<MlpaDataSubmitTaskSuccessArgs> successArgs;

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

    public List<MlpaDataSubmitTaskModel> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<MlpaDataSubmitTaskModel> tasks)
    {
        this.tasks = tasks;
    }

    public List<MlpaDataSubmitTaskSuccessArgs> getSuccessArgs() {
        return successArgs;
    }

    public void setSuccessArgs(List<MlpaDataSubmitTaskSuccessArgs> successArgs) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
