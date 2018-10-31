package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

public class AbnormalSolveModel
{
    private String taskName;
    
    private String solveName;
    
    private Date solveTime;
    
    private String remark;

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getSolveName()
    {
        return solveName;
    }

    public void setSolveName(String solveName)
    {
        this.solveName = solveName;
    }

    public Date getSolveTime()
    {
        return solveTime;
    }

    public void setSolveTime(Date solveTime)
    {
        this.solveTime = solveTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
