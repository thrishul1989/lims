package com.todaysoft.lims.testing.base.entity.report;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_EXPORT_DETECTION_RESULT")
public class ReportExportDetectionResult  extends UuidKeyEntity{

    private static final long serialVersionUID = 2574104551537520572L;

    private String taskId;

    private String methodName;

    private String details;
    
    private HashMap<String, String> result;
    
    private String clinicalJudgment;
    
    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    @Column(name = "METHOD_NAME")
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    @Column(name = "DETAILS")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    @Column(name = "CLINICAL_JUDGMENT")
    public String getClinicalJudgment()
    {
        return clinicalJudgment;
    }

    public void setClinicalJudgment(String clinicalJudgment)
    {
        this.clinicalJudgment = clinicalJudgment;
    }

    @Transient
    public HashMap<String, String> getResult()
    {
        return result;
    }

    public void setResult(HashMap<String, String> result)
    {
        this.result = result;
    }
}