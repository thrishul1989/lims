package com.todaysoft.lims.system.modules.bpm.libcap.model;

public class LibraryCaptureAssignArgs
{
    private String keys;

    private String sampleType;
    
    public String getKeys()
    {
        return keys;
    }
    
    public void setKeys(String keys)
    {
        this.keys = keys;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }
}
