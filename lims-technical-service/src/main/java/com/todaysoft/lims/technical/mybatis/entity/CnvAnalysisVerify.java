package com.todaysoft.lims.technical.mybatis.entity;

public class CnvAnalysisVerify {
    public static final String FAMILYTESTMETHOD_MLPA="MLPA";
    public static final String FAMILYTESTMETHOD_QPCR="QPCR";
    private String id;

    private Integer isFamilyTestAnalysis;

    private String familyTestMethod;

    private String verifyFamilySampleCode;

    private String verifySampleRelation;

    private String cnvAnalysisResultId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getIsFamilyTestAnalysis() {
        return isFamilyTestAnalysis;
    }

    public void setIsFamilyTestAnalysis(Integer isFamilyTestAnalysis) {
        this.isFamilyTestAnalysis = isFamilyTestAnalysis;
    }

    public String getFamilyTestMethod() {
        return familyTestMethod;
    }

    public void setFamilyTestMethod(String familyTestMethod) {
        this.familyTestMethod = familyTestMethod == null ? null : familyTestMethod.trim();
    }

    public String getVerifyFamilySampleCode() {
        return verifyFamilySampleCode;
    }

    public void setVerifyFamilySampleCode(String verifyFamilySampleCode) {
        this.verifyFamilySampleCode = verifyFamilySampleCode == null ? null : verifyFamilySampleCode.trim();
    }

    public String getVerifySampleRelation() {
        return verifySampleRelation;
    }

    public void setVerifySampleRelation(String verifySampleRelation) {
        this.verifySampleRelation = verifySampleRelation == null ? null : verifySampleRelation.trim();
    }

    public String getCnvAnalysisResultId() {
        return cnvAnalysisResultId;
    }

    public void setCnvAnalysisResultId(String cnvAnalysisResultId) {
        this.cnvAnalysisResultId = cnvAnalysisResultId == null ? null : cnvAnalysisResultId.trim();
    }
}