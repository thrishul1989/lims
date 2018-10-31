package com.todaysoft.lims.biology.model.request;


public class BiologyReAnnotationRequest {

    private String taskId;

    private Integer type;// 1-改为合格 2-上报 3-重新生信分析

    private String remark;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
