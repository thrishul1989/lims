package com.todaysoft.lims.technical.model.request;


import java.util.List;

public class CapCnvAnalysisRequest {

    private String bedCode;

    private List<CapCnvAnalysisSampleBamRequest> compareList;

    private CapCnvAnalysisSampleBamRequest analysisSample;

    public String getBedCode() {
        return bedCode;
    }

    public void setBedCode(String bedCode) {
        this.bedCode = bedCode;
    }

    public List<CapCnvAnalysisSampleBamRequest> getCompareList() {
        return compareList;
    }

    public void setCompareList(List<CapCnvAnalysisSampleBamRequest> compareList) {
        this.compareList = compareList;
    }

    public CapCnvAnalysisSampleBamRequest getAnalysisSample() {
        return analysisSample;
    }

    public void setAnalysisSample(CapCnvAnalysisSampleBamRequest analysisSample) {
        this.analysisSample = analysisSample;
    }
}
