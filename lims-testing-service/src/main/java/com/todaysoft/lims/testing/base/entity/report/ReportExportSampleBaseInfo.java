package com.todaysoft.lims.testing.base.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_EXPORT_SAMPLE_BASE_INFO")
public class ReportExportSampleBaseInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = -752791763793063974L;
    
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
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    @Column(name = "EXAMINEE_NAME")
    public String getExamineeName()
    {
        return examineeName;
    }
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    @Column(name = "SEX")
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    @Column(name = "AGE")
    public String getAge()
    {
        return age;
    }
    public void setAge(String age)
    {
        this.age = age;
    }
    @Column(name = "SAMPLE_TYPE")
    public String getSampleType()
    {
        return sampleType;
    }
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    @Column(name = "SAMPLING_INFO_DATE")
    public String getSamplingInfoDate()
    {
        return samplingInfoDate;
    }
    public void setSamplingInfoDate(String samplingInfoDate)
    {
        this.samplingInfoDate = samplingInfoDate;
    }
    @Column(name = "INSPECTION_UNIT")
    public String getInspectionUnit()
    {
        return inspectionUnit;
    }
    public void setInspectionUnit(String inspectionUnit)
    {
        this.inspectionUnit = inspectionUnit;
    }
    @Column(name = "MEDICAL_RECORD_NUMBER")
    public String getMedicalRecordNumber()
    {
        return medicalRecordNumber;
    }
    public void setMedicalRecordNumber(String medicalRecordNumber)
    {
        this.medicalRecordNumber = medicalRecordNumber;
    }
    @Column(name = "ANALYSIS_PROJECT")
    public String getAnalysisProject()
    {
        return analysisProject;
    }
    public void setAnalysisProject(String analysisProject)
    {
        this.analysisProject = analysisProject;
    }
    @Column(name = "CLINICAL_DIAGNOSIS")
    public String getClinicalDiagnosis()
    {
        return clinicalDiagnosis;
    }
    public void setClinicalDiagnosis(String clinicalDiagnosis)
    {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }
    @Column(name = "CLINICAL_PHENOTYPE")
    public String getClinicalPhenotype()
    {
        return clinicalPhenotype;
    }
    public void setClinicalPhenotype(String clinicalPhenotype)
    {
        this.clinicalPhenotype = clinicalPhenotype;
    }
    @Column(name = "FOCUS_ON_GENES")
    public String getFocusOnGenes()
    {
        return focusOnGenes;
    }
    public void setFocusOnGenes(String focusOnGenes)
    {
        this.focusOnGenes = focusOnGenes;
    }
    @Column(name = "MEDICAL_HISTORY_DESCRIPTION")
    public String getMedicalHistoryDescription()
    {
        return medicalHistoryDescription;
    }
    public void setMedicalHistoryDescription(String medicalHistoryDescription)
    {
        this.medicalHistoryDescription = medicalHistoryDescription;
    }
    @Column(name = "FAMILY_HISTORY_DESCRIPTION")
    public String getFamilyHistoryDescription()
    {
        return familyHistoryDescription;
    }
    public void setFamilyHistoryDescription(String familyHistoryDescription)
    {
        this.familyHistoryDescription = familyHistoryDescription;
    }
    @Column(name = "ORDER_CODE")
    public String getOrderCode()
    {
        return orderCode;
    }
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    @Column(name = "ANALYSIS_RESULT")
    public String getAnalysisResult()
    {
        return analysisResult;
    }
    public void setAnalysisResult(String analysisResult)
    {
        this.analysisResult = analysisResult;
    }
    
    
}
