package com.todaysoft.lims.testing.secondpcr.model;

import java.util.Date;

public class SecondPcrSubmitTaskArgs
{
    private String id;

    private String pcrTaskCode;//er次pcr任务编号

    private String pcrTestCode;//er次pcr试验编号

    private String testorName;//实验员

    private Date testDate;//实验时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPcrTaskCode() {
        return pcrTaskCode;
    }

    public void setPcrTaskCode(String pcrTaskCode) {
        this.pcrTaskCode = pcrTaskCode;
    }

    public String getPcrTestCode() {
        return pcrTestCode;
    }

    public void setPcrTestCode(String pcrTestCode) {
        this.pcrTestCode = pcrTestCode;
    }

    public String getTestorName() {
        return testorName;
    }

    public void setTestorName(String testorName) {
        this.testorName = testorName;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }
}
