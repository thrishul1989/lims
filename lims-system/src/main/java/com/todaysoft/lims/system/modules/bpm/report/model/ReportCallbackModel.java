package com.todaysoft.lims.system.modules.bpm.report.model;


public class ReportCallbackModel
{
    private String reportKey;

    private String generateStatus;//报告生成状态 0-处理中 1-已生成 2-生成失败

    private String reportUrl;//报告路径

    private String fileName;//报告名称

    private String message;//详细信息

    public String getReportKey() {
        return reportKey;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    public String getGenerateStatus() {
        return generateStatus;
    }

    public void setGenerateStatus(String generateStatus) {
        this.generateStatus = generateStatus;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
