package com.todaysoft.lims.technical.mybatis.entity;

public class SangerVerifyRecord {
    private String id;

    private String verifyRecord;

    private String combineCode;

    private String primerId;

    private String dnaSampleId;

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

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public String getPrimerId() {
        return primerId;
    }

    public void setPrimerId(String primerId) {
        this.primerId = primerId;
    }

    public String getDnaSampleId() {
        return dnaSampleId;
    }

    public void setDnaSampleId(String dnaSampleId) {
        this.dnaSampleId = dnaSampleId;
    }
}