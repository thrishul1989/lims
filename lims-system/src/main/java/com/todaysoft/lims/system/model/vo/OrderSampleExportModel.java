package com.todaysoft.lims.system.model.vo;


import com.google.common.collect.Lists;

import java.util.List;

public class OrderSampleExportModel
{
    private String sampleCode;

    private String sampleName;

    private String receivedSampleLongLocation;

    private String receivedSampleTermLocation;

    private List<DnaSampleTestingInfo> dnaList = Lists.newArrayList();

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getReceivedSampleLongLocation() {
        return receivedSampleLongLocation;
    }

    public void setReceivedSampleLongLocation(String receivedSampleLongLocation) {
        this.receivedSampleLongLocation = receivedSampleLongLocation;
    }

    public String getReceivedSampleTermLocation() {
        return receivedSampleTermLocation;
    }

    public void setReceivedSampleTermLocation(String receivedSampleTermLocation) {
        this.receivedSampleTermLocation = receivedSampleTermLocation;
    }

    public List<DnaSampleTestingInfo> getDnaList() {
        return dnaList;
    }

    public void setDnaList(List<DnaSampleTestingInfo> dnaList) {
        this.dnaList = dnaList;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }
}
