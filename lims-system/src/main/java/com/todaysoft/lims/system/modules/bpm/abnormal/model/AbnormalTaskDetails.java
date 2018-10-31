package com.todaysoft.lims.system.modules.bpm.abnormal.model;

import java.util.Date;
import java.util.List;

public class AbnormalTaskDetails
{
    private String id;
    
    private String name;
    
    private String sheetCode;
    
    private String testerName;
    
    private String receivedSampleType;
    
    private String receivedSampleCode;
    
    private String receivedSampleName;
    
    private String testingSampleType;
    
    private String testingSampleCode;
    
    private String reagentKitName;
    
    private Date submitTime;
    
    private String remark;

    private String inputSampleId;

    private String semantic;
    
    private List<AbnormalTaskSolveStrategy> strategies;
    
    private List<AbnormalTaskRelatedSchedule> schedules;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSheetCode()
    {
        return sheetCode;
    }
    
    public void setSheetCode(String sheetCode)
    {
        this.sheetCode = sheetCode;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public String getReceivedSampleType()
    {
        return receivedSampleType;
    }
    
    public void setReceivedSampleType(String receivedSampleType)
    {
        this.receivedSampleType = receivedSampleType;
    }
    
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }
    
    public String getReceivedSampleName()
    {
        return receivedSampleName;
    }
    
    public void setReceivedSampleName(String receivedSampleName)
    {
        this.receivedSampleName = receivedSampleName;
    }
    
    public String getTestingSampleType()
    {
        return testingSampleType;
    }
    
    public void setTestingSampleType(String testingSampleType)
    {
        this.testingSampleType = testingSampleType;
    }
    
    public String getTestingSampleCode()
    {
        return testingSampleCode;
    }
    
    public void setTestingSampleCode(String testingSampleCode)
    {
        this.testingSampleCode = testingSampleCode;
    }
    
    public String getReagentKitName()
    {
        return reagentKitName;
    }
    
    public void setReagentKitName(String reagentKitName)
    {
        this.reagentKitName = reagentKitName;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public List<AbnormalTaskSolveStrategy> getStrategies()
    {
        return strategies;
    }
    
    public void setStrategies(List<AbnormalTaskSolveStrategy> strategies)
    {
        this.strategies = strategies;
    }
    
    public List<AbnormalTaskRelatedSchedule> getSchedules()
    {
        return schedules;
    }
    
    public void setSchedules(List<AbnormalTaskRelatedSchedule> schedules)
    {
        this.schedules = schedules;
    }

    public String getInputSampleId() {
        return inputSampleId;
    }

    public void setInputSampleId(String inputSampleId) {
        this.inputSampleId = inputSampleId;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }
}
