package com.todaysoft.lims.technical.model.request;

public class TechnicalReportRequest {

    private String reportState;

    private String remark;

    public String getReportState() {
        return reportState;
    }

    public void setReportState(String reportState) {
        this.reportState = reportState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
