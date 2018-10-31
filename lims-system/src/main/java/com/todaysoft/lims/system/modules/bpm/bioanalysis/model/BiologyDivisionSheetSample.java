package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

public class BiologyDivisionSheetSample {

    private String id;

    private String sheetId;

    private String divisionSampleId;

    private String sampleCode;

    private String dataCode;

    private String indexCode;

    private Boolean isAdd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getDivisionSampleId() {
        return divisionSampleId;
    }

    public void setDivisionSampleId(String divisionSampleId) {
        this.divisionSampleId = divisionSampleId;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public Boolean getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
    }
}