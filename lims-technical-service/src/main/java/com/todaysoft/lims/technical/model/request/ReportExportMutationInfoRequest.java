package com.todaysoft.lims.technical.model.request;

import java.util.List;

public class ReportExportMutationInfoRequest
{
    private String taskId;
    private List<String> recordIds;
    private String technicalFamilyGroupId;
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    public List<String> getRecordIds()
    {
        return recordIds;
    }
    public void setRecordIds(List<String> recordIds)
    {
        this.recordIds = recordIds;
    }
    public String getTechnicalFamilyGroupId()
    {
        return technicalFamilyGroupId;
    }
    public void setTechnicalFamilyGroupId(String technicalFamilyGroupId)
    {
        this.technicalFamilyGroupId = technicalFamilyGroupId;
    }
    
}
