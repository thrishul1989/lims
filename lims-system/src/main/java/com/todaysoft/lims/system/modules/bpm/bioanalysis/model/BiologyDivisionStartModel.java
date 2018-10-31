package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;


import java.util.List;

public class BiologyDivisionStartModel {

    private String squencingCode;

    private String taskId;

    private List<BiologyDivisionSheetSample> sheetSampleList;

    public String getSquencingCode() {
        return squencingCode;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setSquencingCode(String squencingCode) {
        this.squencingCode = squencingCode;
    }

    public List<BiologyDivisionSheetSample> getSheetSampleList() {
        return sheetSampleList;
    }

    public void setSheetSampleList(List<BiologyDivisionSheetSample> sheetSampleList) {
        this.sheetSampleList = sheetSampleList;
    }
}
