package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;

public class CollectionCapcnvPicRequest {

    private String cnvAnalysisPicId;

    private Integer ifCollection;

    private String ref;

    private String clinicalJudgment;

    public String getCnvAnalysisPicId()
    {
        return cnvAnalysisPicId;
    }

    public void setCnvAnalysisPicId(String cnvAnalysisPicId)
    {
        this.cnvAnalysisPicId = cnvAnalysisPicId;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getIfCollection() {
        return ifCollection;
    }

    public void setIfCollection(Integer ifCollection) {
        this.ifCollection = ifCollection;
    }

    public String getClinicalJudgment() {
        return clinicalJudgment;
    }

    public void setClinicalJudgment(String clinicalJudgment) {
        this.clinicalJudgment = clinicalJudgment;
    }
}
