package com.todaysoft.lims.technical.mybatis.entity;

public class BiologyFamilyAnalysisSampleRelation {
    private String id;

    private String familyAnalysisId;

    private String sampleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamilyAnalysisId() {
        return familyAnalysisId;
    }

    public void setFamilyAnalysisId(String familyAnalysisId) {
        this.familyAnalysisId = familyAnalysisId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }
}