package com.todaysoft.lims.testing.abnormal.model;

import java.util.Date;

public class AbnormalHistoryModel
{
    private String taskName;//异常节点
    
    private String resultRemark;//异常原因
    
    private String strategy;//处理结果
    
    private String solverName;
    
    private Date solveTime;
    //---------------------状态查询字段-----------
    private Date endTime;//异常上报时间
    
    private String annormalRemark;//处理备注

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getResultRemark()
    {
        return resultRemark;
    }

    public void setResultRemark(String resultRemark)
    {
        this.resultRemark = resultRemark;
    }

    public String getStrategy()
    {
        return strategy;
    }

    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }

    public String getSolverName()
    {
        return solverName;
    }

    public void setSolverName(String solverName)
    {
        this.solverName = solverName;
    }

    public Date getSolveTime()
    {
        return solveTime;
    }

    public void setSolveTime(Date solveTime)
    {
        this.solveTime = solveTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getAnnormalRemark()
    {
        return annormalRemark;
    }

    public void setAnnormalRemark(String annormalRemark)
    {
        this.annormalRemark = annormalRemark;
    }
    
    
}
