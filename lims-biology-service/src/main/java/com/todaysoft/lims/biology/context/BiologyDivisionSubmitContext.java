package com.todaysoft.lims.biology.context;


import com.google.common.collect.Lists;
import com.todaysoft.lims.biology.model.BiologyDivisionSheetSample;
import com.todaysoft.lims.biology.model.BiologyDivisionTask;
import com.todaysoft.lims.biology.model.PoolingDivisionSample;

import java.util.List;

public class BiologyDivisionSubmitContext {

    private String sheetId;

    private BiologyDivisionTask biologyDivisionTask;

    private String returnTaskId;

    private List<PoolingDivisionSample> samples = Lists.newArrayList();//原有的sample

    private List<BiologyDivisionSheetSample> totalSamples = Lists.newArrayList();//所有的sample+新增的

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public void addToTotalSamples(BiologyDivisionSheetSample sample)
    {
        totalSamples.add(sample);
    }

    public BiologyDivisionTask getBiologyDivisionTask() {
        return biologyDivisionTask;
    }

    public void setBiologyDivisionTask(BiologyDivisionTask biologyDivisionTask) {
        this.biologyDivisionTask = biologyDivisionTask;
    }

    public String getReturnTaskId() {
        return returnTaskId;
    }

    public void setReturnTaskId(String returnTaskId) {
        this.returnTaskId = returnTaskId;
    }

    public List<PoolingDivisionSample> getSamples() {
        return samples;
    }

    public void setSamples(List<PoolingDivisionSample> samples) {
        this.samples = samples;
    }

    public List<BiologyDivisionSheetSample> getTotalSamples() {
        return totalSamples;
    }

    public void setTotalSamples(List<BiologyDivisionSheetSample> totalSamples) {
        this.totalSamples = totalSamples;
    }
}
