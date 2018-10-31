package com.todaysoft.lims.technical.model.request;


public class DataAnalysisReportModel {

    private String taskId;

    private String squencingCode;

    private String remark;

    private String taskRefer;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSquencingCode() {
        return squencingCode;
    }

    public void setSquencingCode(String squencingCode) {
        this.squencingCode = squencingCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaskRefer() {
        return taskRefer;
    }

    public void setTaskRefer(String taskRefer) {
        this.taskRefer = taskRefer;
    }
}
