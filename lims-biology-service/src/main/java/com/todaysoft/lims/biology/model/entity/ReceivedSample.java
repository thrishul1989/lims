package com.todaysoft.lims.biology.model.entity;

import java.io.Serializable;

public class ReceivedSample implements Serializable
{
    private String sampleId;

    private String sampleCode;

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }
}
