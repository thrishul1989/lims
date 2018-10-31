package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

public class UploadEvidenceRespModelData
{
    private String taskId;
    
    private UploadEvidenceRespModelStatus status;
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public UploadEvidenceRespModelStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(UploadEvidenceRespModelStatus status)
    {
        this.status = status;
    }
    
}
