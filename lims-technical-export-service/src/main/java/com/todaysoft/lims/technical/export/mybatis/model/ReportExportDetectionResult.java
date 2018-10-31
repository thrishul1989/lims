package com.todaysoft.lims.technical.export.mybatis.model;

import java.util.HashMap;

public class ReportExportDetectionResult{
    
    public static String CLINICALJUDGMENT_CLINICALPHENOTYPEHIGHCORRELATION="临床表型高度相关";
    
    public static String CLINICALJUDGMENT_CLINICALPHENOTYPECORRELATION="临床表型相关";

    private String id;
    
    private String taskId;

    private String methodName;

    private String details;
    
    private HashMap<String, String> result;
    
    private String clinicalJudgment;
    

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    public String getClinicalJudgment()
    {
        return clinicalJudgment;
    }

    public void setClinicalJudgment(String clinicalJudgment)
    {
        this.clinicalJudgment = clinicalJudgment;
    }

    public HashMap<String, String> getResult()
    {
        return result;
    }

    public void setResult(HashMap<String, String> result)
    {
        this.result = result;
    }

}