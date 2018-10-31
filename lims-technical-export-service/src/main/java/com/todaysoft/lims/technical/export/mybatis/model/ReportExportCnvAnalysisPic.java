package com.todaysoft.lims.technical.export.mybatis.model;

public class ReportExportCnvAnalysisPic {
    private String id;

    private String taskId;

    private String cnvAnalysisPicId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getCnvAnalysisPicId() {
        return cnvAnalysisPicId;
    }

    public void setCnvAnalysisPicId(String cnvAnalysisPicId) {
        this.cnvAnalysisPicId = cnvAnalysisPicId == null ? null : cnvAnalysisPicId.trim();
    }
}