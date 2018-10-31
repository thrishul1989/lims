package com.todaysoft.lims.technical.model.request;


import java.util.List;

public class CapCnvPictureRequest {

    private String sampleCode;

    private List<CapCnvPictureGeneData> data;

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public List<CapCnvPictureGeneData> getData() {
        return data;
    }

    public void setData(List<CapCnvPictureGeneData> data) {
        this.data = data;
    }
}
