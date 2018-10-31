package com.todaysoft.lims.technical.mybatis.entity;

public class TechnicalAnalyVerify {
    private String id;

    private String recordId;

    private String inputSampleId;

    private String sampleFamilyRelation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getInputSampleId() {
        return inputSampleId;
    }

    public void setInputSampleId(String inputSampleId) {
        this.inputSampleId = inputSampleId;
    }

    public String getSampleFamilyRelation() {
        return sampleFamilyRelation;
    }

    public void setSampleFamilyRelation(String sampleFamilyRelation) {
        this.sampleFamilyRelation = sampleFamilyRelation;
    }
}