package com.todaysoft.lims.testing.biologyanaly.model;

public class BiologyAnalyDetailsRecord
{
    private String recordCode;
    
    private String poolingCode;
    
    private String orderCode;
    
    private String receivedSampleCode;
    
    private String referenceSampleCode;
    
    private String referenceSampleType;
    
    private String referencePoolingCode;
    
    private String flag;
    
    private String librarySampleCode;
    
    private String libraryIndex;
    
    private String coordinate;
    
    private String examineeSex;
    
    private String contractCode;
    
    private String testingType;
    
    private String remark;
    
    private String examineeName;
    
    private String sampleName;
    
    private String sampleType;

    //20170714版本加
    private String productCode;

    private String productName;

    private String familyRelation;

    private String examineeDiagnosis;//临床诊断 多个

    private String examineeGenes;//重点关注基因 多个

    private String examineePhenotypes;//临床表型 多个

    private String medicalHistory;//病史摘要

    private String familyMedicalHistory;//家族史摘要

    private String clinicalRecommend;//临床推荐理由
    //
    
    private String sampleTypeId;
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getReferenceSampleType()
    {
        return referenceSampleType;
    }
    
    public void setReferenceSampleType(String referenceSampleType)
    {
        this.referenceSampleType = referenceSampleType;
    }
    
    public String getReferencePoolingCode()
    {
        return referencePoolingCode;
    }
    
    public void setReferencePoolingCode(String referencePoolingCode)
    {
        this.referencePoolingCode = referencePoolingCode;
    }
    
    public String getFlag()
    {
        return flag;
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getRecordCode()
    {
        return recordCode;
    }
    
    public void setRecordCode(String recordCode)
    {
        this.recordCode = recordCode;
    }
    
    public String getPoolingCode()
    {
        return poolingCode;
    }
    
    public void setPoolingCode(String poolingCode)
    {
        this.poolingCode = poolingCode;
    }
    
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }
    
    public String getReferenceSampleCode()
    {
        return referenceSampleCode;
    }
    
    public void setReferenceSampleCode(String referenceSampleCode)
    {
        this.referenceSampleCode = referenceSampleCode;
    }
    
    public String getLibrarySampleCode()
    {
        return librarySampleCode;
    }
    
    public void setLibrarySampleCode(String librarySampleCode)
    {
        this.librarySampleCode = librarySampleCode;
    }
    
    public String getLibraryIndex()
    {
        return libraryIndex;
    }
    
    public void setLibraryIndex(String libraryIndex)
    {
        this.libraryIndex = libraryIndex;
    }
    
    public String getCoordinate()
    {
        return coordinate;
    }
    
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }
    
    public String getExamineeSex()
    {
        return examineeSex;
    }
    
    public void setExamineeSex(String examineeSex)
    {
        this.examineeSex = examineeSex;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFamilyRelation() {
        return familyRelation;
    }

    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }

    public String getExamineeDiagnosis() {
        return examineeDiagnosis;
    }

    public void setExamineeDiagnosis(String examineeDiagnosis) {
        this.examineeDiagnosis = examineeDiagnosis;
    }

    public String getExamineeGenes() {
        return examineeGenes;
    }

    public void setExamineeGenes(String examineeGenes) {
        this.examineeGenes = examineeGenes;
    }

    public String getExamineePhenotypes() {
        return examineePhenotypes;
    }

    public void setExamineePhenotypes(String examineePhenotypes) {
        this.examineePhenotypes = examineePhenotypes;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getFamilyMedicalHistory() {
        return familyMedicalHistory;
    }

    public void setFamilyMedicalHistory(String familyMedicalHistory) {
        this.familyMedicalHistory = familyMedicalHistory;
    }

    public String getClinicalRecommend() {
        return clinicalRecommend;
    }

    public void setClinicalRecommend(String clinicalRecommend) {
        this.clinicalRecommend = clinicalRecommend;
    }

    public String getSampleTypeId()
    {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
}
