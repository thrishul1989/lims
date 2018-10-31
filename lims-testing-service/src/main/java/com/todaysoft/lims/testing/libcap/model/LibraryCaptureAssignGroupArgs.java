package com.todaysoft.lims.testing.libcap.model;

import java.math.BigDecimal;
import java.util.List;

public class LibraryCaptureAssignGroupArgs
{
    private String testingCode;
    
    private String probeId;
    
    private BigDecimal probeDatasize;
    
    private List<LibraryCaptureAssignTaskArgs> tasks;
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getProbeId()
    {
        return probeId;
    }
    
    public void setProbeId(String probeId)
    {
        this.probeId = probeId;
    }
    
    public BigDecimal getProbeDatasize()
    {
        return probeDatasize;
    }
    
    public void setProbeDatasize(BigDecimal probeDatasize)
    {
        this.probeDatasize = probeDatasize;
    }
    
    public List<LibraryCaptureAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LibraryCaptureAssignTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
