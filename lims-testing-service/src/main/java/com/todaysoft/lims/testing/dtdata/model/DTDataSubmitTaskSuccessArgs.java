package com.todaysoft.lims.testing.dtdata.model;


import com.google.common.collect.Maps;

import java.util.Map;

public class DTDataSubmitTaskSuccessArgs {

    private String combineCode;

    private String sampleCode;

    private String diseaseType;

    private String testGene;

    private String repeatNumber;

    private String normalValue;

    private String remark;

    private String negaOrPositive;

    private String conclusion;

    private Map<String,String> map = Maps.newHashMap();//其他多余字段不涉及代码的放map里面

    public String getCombineCode() {
        return combineCode;
    }

    public void setCombineCode(String combineCode) {
        this.combineCode = combineCode;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getTestGene() {
        return testGene;
    }

    public void setTestGene(String testGene) {
        this.testGene = testGene;
    }

    public String getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(String repeatNumber) {
        this.repeatNumber = repeatNumber;
    }

    public String getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(String normalValue) {
        this.normalValue = normalValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNegaOrPositive() {
        return negaOrPositive;
    }

    public void setNegaOrPositive(String negaOrPositive) {
        this.negaOrPositive = negaOrPositive;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
