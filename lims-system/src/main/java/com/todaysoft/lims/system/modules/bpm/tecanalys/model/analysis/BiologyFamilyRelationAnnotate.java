package com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis;

public class BiologyFamilyRelationAnnotate {
    private String id;

    private String ganalysisFamilyId;

    private String cnv;

    private String makeCptyNumberVariation;

    private String snvJson;

    private String sv;

    private String svJson;

    private String regioncountDmdexon;

    private String statisticsDmdexon;

    private Integer status;//家系分析状态(0-进行中 1-成功 2-出现错误 3-解析文件中 4-解析文件出错 5-解析文件成功）

    private String descrption;//失败描述

    private String analysisDesc;//解析时候的结果描述   冗余字段

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGanalysisFamilyId() {
        return ganalysisFamilyId;
    }

    public void setGanalysisFamilyId(String ganalysisFamilyId) {
        this.ganalysisFamilyId = ganalysisFamilyId;
    }

    public String getCnv() {
        return cnv;
    }

    public void setCnv(String cnv) {
        this.cnv = cnv;
    }

    public String getMakeCptyNumberVariation() {
        return makeCptyNumberVariation;
    }

    public void setMakeCptyNumberVariation(String makeCptyNumberVariation) {
        this.makeCptyNumberVariation = makeCptyNumberVariation;
    }

    public String getSnvJson() {
        return snvJson;
    }

    public void setSnvJson(String snvJson) {
        this.snvJson = snvJson;
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

    public String getRegioncountDmdexon() {
        return regioncountDmdexon;
    }

    public void setRegioncountDmdexon(String regioncountDmdexon) {
        this.regioncountDmdexon = regioncountDmdexon;
    }

    public String getStatisticsDmdexon() {
        return statisticsDmdexon;
    }

    public void setStatisticsDmdexon(String statisticsDmdexon) {
        this.statisticsDmdexon = statisticsDmdexon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getAnalysisDesc() {
        return analysisDesc;
    }

    public void setAnalysisDesc(String analysisDesc) {
        this.analysisDesc = analysisDesc;
    }
}