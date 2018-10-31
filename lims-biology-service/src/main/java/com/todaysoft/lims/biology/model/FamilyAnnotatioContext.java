package com.todaysoft.lims.biology.model;


import com.google.common.collect.Lists;
import com.todaysoft.lims.biology.model.request.BiologyFamilyStartAnalyRequest;
import com.todaysoft.lims.biology.model.response.FamilyAnnotatioResponse;

import java.util.List;

public class FamilyAnnotatioContext {

    private BiologyFamilyStartAnalyRequest request;

    private BiologyFamilyAnalysis familyAnalysis;

    private List<BiologyAnnotationTask> annotationTasks = Lists.newArrayList();

    private String familyAnnotatioTaskId;

    private Integer familyAnalysisState;//0-失败 1-成功

    private String primarySampleCode;//找出主样本编号 用来生成家系表的任务号

    public String getPrimarySampleCode() {
        return primarySampleCode;
    }

    public void setPrimarySampleCode(String primarySampleCode) {
        this.primarySampleCode = primarySampleCode;
    }

    public BiologyFamilyStartAnalyRequest getRequest() {
        return request;
    }

    public void setRequest(BiologyFamilyStartAnalyRequest request) {
        this.request = request;
    }

    public BiologyFamilyAnalysis getFamilyAnalysis() {
        return familyAnalysis;
    }

    public void setFamilyAnalysis(BiologyFamilyAnalysis familyAnalysis) {
        this.familyAnalysis = familyAnalysis;
    }

    public List<BiologyAnnotationTask> getAnnotationTasks() {
        return annotationTasks;
    }

    public void setAnnotationTasks(List<BiologyAnnotationTask> annotationTasks) {
        this.annotationTasks = annotationTasks;
    }

    public String getFamilyAnnotatioTaskId() {
        return familyAnnotatioTaskId;
    }

    public void setFamilyAnnotatioTaskId(String familyAnnotatioTaskId) {
        this.familyAnnotatioTaskId = familyAnnotatioTaskId;
    }

    public Integer getFamilyAnalysisState() {
        return familyAnalysisState;
    }

    public void setFamilyAnalysisState(Integer familyAnalysisState) {
        this.familyAnalysisState = familyAnalysisState;
    }
}
