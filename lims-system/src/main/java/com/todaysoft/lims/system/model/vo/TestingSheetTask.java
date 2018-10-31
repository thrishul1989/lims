package com.todaysoft.lims.system.model.vo;

import java.util.Date;
import java.util.List;

public class TestingSheetTask
{
    
    private String id;
    
    private TestingSheet testingSheet;//任务单ID
    
    private String testingTaskId;//检测任务ID
    
    private Integer resultType;//1正常2失败可重做3失败不可重做
    
    private String remark;//备注
    
    private String resultDetails;//结果详情
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public TestingSheet getTestingSheet()
    {
        return testingSheet;
    }
    
    public void setTestingSheet(TestingSheet testingSheet)
    {
        this.testingSheet = testingSheet;
    }
    
    public String getTestingTaskId()
    {
        return testingTaskId;
    }
    
    public void setTestingTaskId(String testingTaskId)
    {
        this.testingTaskId = testingTaskId;
    }
    
    public Integer getResultType()
    {
        return resultType;
    }
    
    public void setResultType(Integer resultType)
    {
        this.resultType = resultType;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getResultDetails()
    {
        return resultDetails;
    }
    
    public void setResultDetails(String resultDetails)
    {
        this.resultDetails = resultDetails;
    }
    
}
