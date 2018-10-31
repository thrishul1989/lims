package com.todaysoft.lims.system.modules.bpm.model.assign.sheet.task;

public class QTAssignSheetTask
{
    private String content;
    
    private String sourceSampleCode; //测序编号
    
    private String absoluteId;
    
    private String activitiTaskId;//工作流
    
    public String getSourceSampleCode()
    {
        return sourceSampleCode;
    }
    
    public void setSourceSampleCode(String sourceSampleCode)
    {
        this.sourceSampleCode = sourceSampleCode;
    }
    
    public String getAbsoluteId()
    {
        return absoluteId;
    }
    
    public void setAbsoluteId(String absoluteId)
    {
        this.absoluteId = absoluteId;
    }
    
    public String getActivitiTaskId()
    {
        return activitiTaskId;
    }
    
    public void setActivitiTaskId(String activitiTaskId)
    {
        this.activitiTaskId = activitiTaskId;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
}
