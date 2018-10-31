package com.todaysoft.lims.system.modules.bpm.model.assign.sheet;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.model.assign.sheet.task.BiAnalysisAssignSheetTask;

public class BiAnalysisAssignSheet
{
    private String id;
    
    private String taskCode;//任务编号
    
    private String testingName;//实验员
    
    private String sendName;//任务下达人
    
    private Date sendDate;//下达时间
    
    private List<BiAnalysisAssignSheetTask> assignSheetTaskList = Lists.newArrayList();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTaskCode()
    {
        return taskCode;
    }
    
    public void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
    }
    
    public String getTestingName()
    {
        return testingName;
    }
    
    public void setTestingName(String testingName)
    {
        this.testingName = testingName;
    }
    
    public String getSendName()
    {
        return sendName;
    }
    
    public void setSendName(String sendName)
    {
        this.sendName = sendName;
    }
    
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    public List<BiAnalysisAssignSheetTask> getAssignSheetTaskList()
    {
        return assignSheetTaskList;
    }
    
    public void setAssignSheetTaskList(List<BiAnalysisAssignSheetTask> assignSheetTaskList)
    {
        this.assignSheetTaskList = assignSheetTaskList;
    }
    
}
