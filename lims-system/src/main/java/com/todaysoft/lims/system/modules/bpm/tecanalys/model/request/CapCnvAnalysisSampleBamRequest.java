package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;


public class CapCnvAnalysisSampleBamRequest {

    private String sampleCode;

    private String bamFilePath;

    private Integer sex;

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getBamFilePath() {
        return bamFilePath;
    }

    public void setBamFilePath(String bamFilePath) {
        this.bamFilePath = bamFilePath;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
