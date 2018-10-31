package com.todaysoft.lims.biology.model.response;


import com.todaysoft.lims.biology.model.api.annotationcallback.CapCnvDataStatus;

public class FamilyAnnotatioData {

    private String snpIndel;

    private String snpIndelJson;

    private String cnv;

    private String sv;

    private String svJson;

    private String statistics;

    private String regioncount;

    private CapCnvDataStatus status;

    private String taskId;

    public String getSnpIndel() {
        return snpIndel;
    }

    public void setSnpIndel(String snpIndel) {
        this.snpIndel = snpIndel;
    }

    public String getSnpIndelJson() {
        return snpIndelJson;
    }

    public void setSnpIndelJson(String snpIndelJson) {
        this.snpIndelJson = snpIndelJson;
    }

    public String getCnv() {
        return cnv;
    }

    public void setCnv(String cnv) {
        this.cnv = cnv;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public String getSvJson() {
        return svJson;
    }

    public void setSvJson(String svJson) {
        this.svJson = svJson;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getRegioncount() {
        return regioncount;
    }

    public void setRegioncount(String regioncount) {
        this.regioncount = regioncount;
    }

    public CapCnvDataStatus getStatus() {
        return status;
    }

    public void setStatus(CapCnvDataStatus status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
