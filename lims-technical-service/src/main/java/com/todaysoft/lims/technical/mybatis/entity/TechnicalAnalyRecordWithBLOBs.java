package com.todaysoft.lims.technical.mybatis.entity;

public class TechnicalAnalyRecordWithBLOBs extends TechnicalAnalyRecord {
    private String disease;

    private String diseasePhenotype;

    private String diseaseInformation;

    private String changeAa;

    private String otherFields;

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDiseasePhenotype() {
        return diseasePhenotype;
    }

    public void setDiseasePhenotype(String diseasePhenotype) {
        this.diseasePhenotype = diseasePhenotype;
    }

    public String getDiseaseInformation() {
        return diseaseInformation;
    }

    public void setDiseaseInformation(String diseaseInformation) {
        this.diseaseInformation = diseaseInformation;
    }

    public String getChangeAa() {
        return changeAa;
    }

    public void setChangeAa(String changeAa) {
        this.changeAa = changeAa;
    }

    public String getOtherFields() {
        return otherFields;
    }

    public void setOtherFields(String otherFields) {
        this.otherFields = otherFields;
    }
}