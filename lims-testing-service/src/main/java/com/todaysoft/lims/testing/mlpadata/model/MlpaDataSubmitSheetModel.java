package com.todaysoft.lims.testing.mlpadata.model;

import com.todaysoft.lims.testing.base.entity.TestingDataPic;

import java.util.List;


public class MlpaDataSubmitSheetModel
{
    private String id;

    private int type;//1检测 2验证
    
    private List<MlpaDataSubmitTaskModel> tasks;

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
