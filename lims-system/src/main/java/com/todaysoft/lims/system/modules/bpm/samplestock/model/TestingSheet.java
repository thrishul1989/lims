package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import java.util.Date;
import java.util.List;

public class TestingSheet
{
    
    private String id;
    
    private List<TestingSheetTask> testingSheetTaskList;
    
    private String semantic;//任务单类型标记
    
    private String taskName;//任务单类型名称
    
    private String code;//任务单编号
    
    private String description;//任务单描述
    
    private String testerId;//操作员ID
    
    private String testerName;//操作员姓名
    
    private String assignerId;//下单人ID
    
    private String req;
    
    public String getReq()
    {
        return req;
    }

    public void setReq(String req)
    {
        this.req = req;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<TestingSheetTask> getTestingSheetTaskList()
    {
        return testingSheetTaskList;
    }

    public void setTestingSheetTaskList(List<TestingSheetTask> testingSheetTaskList)
    {
        this.testingSheetTaskList = testingSheetTaskList;
    }

    public String getSemantic()
    {
        return semantic;
    }

    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTesterId()
    {
        return testerId;
    }

    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }

    public String getTesterName()
    {
        return testerName;
    }

    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }

    public String getAssignerId()
    {
        return assignerId;
    }

    public void setAssignerId(String assignerId)
    {
        this.assignerId = assignerId;
    }

    public String getAssignerName()
    {
        return assignerName;
    }

    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }

    public Date getAssignTime()
    {
        return assignTime;
    }

    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }

    public String getSubmiterId()
    {
        return submiterId;
    }

    public void setSubmiterId(String submiterId)
    {
        this.submiterId = submiterId;
    }

    public String getSubmiterName()
    {
        return submiterName;
    }

    public void setSubmiterName(String submiterName)
    {
        this.submiterName = submiterName;
    }

    public Date getSubmitTime()
    {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getVariables()
    {
        return variables;
    }

    public void setVariables(String variables)
    {
        this.variables = variables;
    }

    private String assignerName;//下单人姓名
    
    private Date assignTime;//下单时间
    
    private String submiterId;//操作员ID
    
    private String submiterName;//操作员姓名
    
    private Date submitTime;//任务单完成提交时间
    
    private Date createTime;
    
    private String variables;//json变量
    
}
