package com.todaysoft.lims.system.modules.bpm.libcap.model;

import java.math.BigDecimal;
import java.util.List;

public class LibraryCaptureAssignGroupModel
{
    private String probeId;
    
    private String probeName;
    
    private BigDecimal probeDatasize;
    
    private List<LibraryCaptureTask> tasks;
    
    public String getProbeId()
    {
        return probeId;
    }
    
    public void setProbeId(String probeId)
    {
        this.probeId = probeId;
    }
    
    public String getProbeName()
    {
        return probeName;
    }
    
    public void setProbeName(String probeName)
    {
        this.probeName = probeName;
    }
    
    public BigDecimal getProbeDatasize()
    {
        return probeDatasize;
    }
    
    public void setProbeDatasize(BigDecimal probeDatasize)
    {
        this.probeDatasize = probeDatasize;
    }
    
    public List<LibraryCaptureTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LibraryCaptureTask> tasks)
    {
        this.tasks = tasks;
    }
}
