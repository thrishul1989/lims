package com.todaysoft.lims.testing.mybatis.model;

public class TestingSheetModel {

    private String id;

    private String testerId;//操作员ID

    private String testerName;//操作员姓名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }

    public String getTesterName() {
        return testerName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName;
    }
}