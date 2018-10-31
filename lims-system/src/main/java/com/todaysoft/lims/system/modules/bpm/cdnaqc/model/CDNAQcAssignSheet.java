package com.todaysoft.lims.system.modules.bpm.cdnaqc.model;

import java.util.List;

public class CDNAQcAssignSheet
{
    private String qualityControlReagentKitId;

    private String qualityControlTesterId;

    private String qualityControlMethods;

    private String description;

    private List<CDNAQcAssignSheetTask> tasks;

    public String getQualityControlReagentKitId()
    {
        return qualityControlReagentKitId;
    }

    public void setQualityControlReagentKitId(String qualityControlReagentKitId)
    {
        this.qualityControlReagentKitId = qualityControlReagentKitId;
    }

    public String getQualityControlTesterId()
    {
        return qualityControlTesterId;
    }

    public void setQualityControlTesterId(String qualityControlTesterId)
    {
        this.qualityControlTesterId = qualityControlTesterId;
    }

    public String getQualityControlMethods()
    {
        return qualityControlMethods;
    }

    public void setQualityControlMethods(String qualityControlMethods)
    {
        this.qualityControlMethods = qualityControlMethods;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<CDNAQcAssignSheetTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<CDNAQcAssignSheetTask> tasks) {
        this.tasks = tasks;
    }
}
