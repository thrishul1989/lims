package com.todaysoft.lims.technical.export.mybatis.model;

public class ReportExportSampleBaseInfo {
    private String id;

    private String taskId;
    
    private String orderCode;

    private String sampleCode;

    private String examineeName;

    private String sex;

    private String age;

    private String sampleType;

    private String samplingInfoDate;

    private String inspectionUnit;

    private String medicalRecordNumber;

    private String analysisProject;

    private String clinicalDiagnosis;

    private String clinicalPhenotype;

    private String focusOnGenes;

    private String medicalHistoryDescription;

    private String familyHistoryDescription;
    
    private String analysisResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode == null ? null : sampleCode.trim();
    }

    public String getExamineeName() {
        return examineeName;
    }

    public void setExamineeName(String examineeName) {
        this.examineeName = examineeName == null ? null : examineeName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType == null ? null : sampleType.trim();
    }

    public String getSamplingInfoDate() {
        return samplingInfoDate;
    }

    public void setSamplingInfoDate(String samplingInfoDate) {
        this.samplingInfoDate = samplingInfoDate == null ? null : samplingInfoDate.trim();
    }

    public String getInspectionUnit() {
        return inspectionUnit;
    }

    public void setInspectionUnit(String inspectionUnit) {
        this.inspectionUnit = inspectionUnit == null ? null : inspectionUnit.trim();
    }

    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber == null ? null : medicalRecordNumber.trim();
    }

    public String getAnalysisProject() {
        return analysisProject;
    }

    public void setAnalysisProject(String analysisProject) {
        this.analysisProject = analysisProject == null ? null : analysisProject.trim();
    }

    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis == null ? null : clinicalDiagnosis.trim();
    }

    public String getClinicalPhenotype() {
        return clinicalPhenotype;
    }

    public void setClinicalPhenotype(String clinicalPhenotype) {
        this.clinicalPhenotype = clinicalPhenotype == null ? null : clinicalPhenotype.trim();
    }

    public String getFocusOnGenes() {
        return focusOnGenes;
    }

    public void setFocusOnGenes(String focusOnGenes) {
        this.focusOnGenes = focusOnGenes == null ? null : focusOnGenes.trim();
    }

    public String getMedicalHistoryDescription() {
        return medicalHistoryDescription;
    }

    public void setMedicalHistoryDescription(String medicalHistoryDescription) {
        this.medicalHistoryDescription = medicalHistoryDescription == null ? null : medicalHistoryDescription.trim();
    }

    public String getFamilyHistoryDescription() {
        return familyHistoryDescription;
    }

    public void setFamilyHistoryDescription(String familyHistoryDescription) {
        this.familyHistoryDescription = familyHistoryDescription == null ? null : familyHistoryDescription.trim();
    }

    public String getAnalysisResult()
    {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult)
    {
        this.analysisResult = analysisResult;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
}