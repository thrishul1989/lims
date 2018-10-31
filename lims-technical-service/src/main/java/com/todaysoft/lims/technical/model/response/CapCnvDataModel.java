package com.todaysoft.lims.technical.model.response;

/**
 * Created by HSHY-032 on 2018/1/16.
 */
public class CapCnvDataModel {

    private String capCnv;

    private String taskId;

    private CapCnvDataStatus status;

    public String getCapCnv() {
        return capCnv;
    }

    public void setCapCnv(String capCnv) {
        this.capCnv = capCnv;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public CapCnvDataStatus getStatus() {
        return status;
    }

    public void setStatus(CapCnvDataStatus status) {
        this.status = status;
    }
}
