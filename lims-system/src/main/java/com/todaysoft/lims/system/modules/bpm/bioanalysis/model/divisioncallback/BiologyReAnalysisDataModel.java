package com.todaysoft.lims.system.modules.bpm.bioanalysis.model.divisioncallback;


public class BiologyReAnalysisDataModel {

    private String cnv;

    private String sv;

    private String svJson;

    private String snpIndel;

    private String snpIndelJson;

    private String regioncount;

    private String statistics;

    private String vcf;

    private String statisticsState;

    private BamFileModel bam;

    private CapCnvDataStatus status;

    private String taskId;

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

    public String getRegioncount() {
        return regioncount;
    }

    public void setRegioncount(String regioncount) {
        this.regioncount = regioncount;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getVcf() {
        return vcf;
    }

    public void setVcf(String vcf) {
        this.vcf = vcf;
    }

    public String getStatisticsState() {
        return statisticsState;
    }

    public void setStatisticsState(String statisticsState) {
        this.statisticsState = statisticsState;
    }

    public BamFileModel getBam() {
        return bam;
    }

    public void setBam(BamFileModel bam) {
        this.bam = bam;
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
