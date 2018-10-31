package com.todaysoft.lims.biology.model;

public class BiologyFamilyAnnotationTaskRelation {
    private String id;

    private String familyAnalysisId;

    private String annotationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamilyAnalysisId() {
        return familyAnalysisId;
    }

    public void setFamilyAnalysisId(String familyAnalysisId) {
        this.familyAnalysisId = familyAnalysisId;
    }

    public String getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }
}