package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

public class BiologyFamilyStartAnalyRequest
{
    private String familyAnnotationId;

    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getFamilyAnnotationId() {
        return familyAnnotationId;
    }

    public void setFamilyAnnotationId(String familyAnnotationId) {
        this.familyAnnotationId = familyAnnotationId;
    }
}
