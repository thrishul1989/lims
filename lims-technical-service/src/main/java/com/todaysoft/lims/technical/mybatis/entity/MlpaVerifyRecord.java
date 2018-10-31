package com.todaysoft.lims.technical.mybatis.entity;

public class MlpaVerifyRecord {
    private String id;

    private String verifyRecord;

    private String dnaSampleId;

    private String combineCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerifyRecord() {
        return verifyRecord;
    }

    public void setVerifyRecord(String verifyRecord) {
        this.verifyRecord = verifyRecord;
    }

    public String getDnaSampleId() {
        return dnaSampleId;
    }

    public void setDnaSampleId(String dnaSampleId) {
        this.dnaSampleId = dnaSampleId;
    }

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }
}