package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

public class TemproaryTestingTask
{
    private String taskId;
    
    private String taskName;
    
    private Date assignerTime;
    
    private Date endTime;
    
    private String taskCode;
    
    private String status;
    
    private String endStatus;
    
    private String testerName;
    
    private String remark;
    
    private Date pauseTime;
    
    private String pauseName;
    
    private Date restartTime;
    
    private String restartName;
    
    private String testingMethodName;
    
    private String sampleCode;
    private String receivedSampleCode;//原始样本编号
    private String testingSampleCode; //实验样本编号
    
    private String verifyChromosomePosition;//验证位点-染色体位置

    public String getReceivedSampleCode() {
        return receivedSampleCode;
    }

    public String getTestingSampleCode() {
        return testingSampleCode;
    }

    public void setTestingSampleCode(String testingSampleCode) {
        this.testingSampleCode = testingSampleCode;
    }

    public void setReceivedSampleCode(String receivedSampleCode) {
        this.receivedSampleCode = receivedSampleCode;
    }

    public String getVerifyChromosomePosition()
    {
        return verifyChromosomePosition;
    }
    
    public void setVerifyChromosomePosition(String verifyChromosomePosition)
    {
        this.verifyChromosomePosition = verifyChromosomePosition;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getTestingMethodName()
    {
        return testingMethodName;
    }
    
    public void setTestingMethodName(String testingMethodName)
    {
        this.testingMethodName = testingMethodName;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public Date getPauseTime()
    {
        return pauseTime;
    }
    
    public void setPauseTime(Date pauseTime)
    {
        this.pauseTime = pauseTime;
    }
    
    public String getPauseName()
    {
        return pauseName;
    }
    
    public void setPauseName(String pauseName)
    {
        this.pauseName = pauseName;
    }
    
    public Date getRestartTime()
    {
        return restartTime;
    }
    
    public void setRestartTime(Date restartTime)
    {
        this.restartTime = restartTime;
    }
    
    public String getRestartName()
    {
        return restartName;
    }
    
    public void setRestartName(String restartName)
    {
        this.restartName = restartName;
    }
    
    public TemproaryTestingTask(String taskName, Date endTime)
    {
        super();
        this.taskName = taskName;
        this.endTime = endTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    public Date getAssignerTime()
    {
        return assignerTime;
    }
    
    public void setAssignerTime(Date assignerTime)
    {
        this.assignerTime = assignerTime;
    }
    
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public String getTaskCode()
    {
        return taskCode;
    }
    
    public void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getEndStatus()
    {
        return endStatus;
    }
    
    public void setEndStatus(String endStatus)
    {
        this.endStatus = endStatus;
    }
    
    public TemproaryTestingTask(String taskName, String status)
    {
        super();
        this.taskName = taskName;
        this.status = status;
    }
    
    public TemproaryTestingTask()
    {
        super();
    }
    
}
