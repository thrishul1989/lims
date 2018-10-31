package com.todaysoft.lims.testing.report.model;


import com.todaysoft.lims.testing.base.entity.TestingVerifyRecord;

public class VerifyRecordModel
{
    private String combineCode;//合并编号

    private TestingVerifyRecord verifyRecord;//验证方案

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public TestingVerifyRecord getVerifyRecord() {
        return verifyRecord;
    }

    public void setVerifyRecord(TestingVerifyRecord verifyRecord) {
        this.verifyRecord = verifyRecord;
    }
}
