package com.todaysoft.lims.biology.model.api;


import com.google.common.collect.Lists;

import java.util.List;

public class BiologyDivisionStartReqModel {

    private String sequencingCode;

    private String sequencerType;

    private String laneCode;

    private List<BiologyDivisionStartSampleModel> samples = Lists.newArrayList();

    public void addToSamples(BiologyDivisionStartSampleModel sample)
    {
        samples.add(sample);
    }

    public String getSequencingCode() {
        return sequencingCode;
    }

    public void setSequencingCode(String sequencingCode) {
        this.sequencingCode = sequencingCode;
    }

    public String getSequencerType() {
        return sequencerType;
    }

    public void setSequencerType(String sequencerType) {
        this.sequencerType = sequencerType;
    }

    public String getLaneCode() {
        return laneCode;
    }

    public void setLaneCode(String laneCode) {
        this.laneCode = laneCode;
    }

    public List<BiologyDivisionStartSampleModel> getSamples() {
        return samples;
    }

    public void setSamples(List<BiologyDivisionStartSampleModel> samples) {
        this.samples = samples;
    }
}
