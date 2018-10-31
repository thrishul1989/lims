package com.todaysoft.lims.biology.model;


public class ScheduleSampleModel {

    private String activeTask;//流程节点

    private String id; //流程id

    private Integer proccessState;

    public Integer getProccessState() {
        return proccessState;
    }

    public void setProccessState(Integer proccessState) {
        this.proccessState = proccessState;
    }

    public String getActiveTask() {
        return activeTask;
    }

    public void setActiveTask(String activeTask) {
        this.activeTask = activeTask;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
