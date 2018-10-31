package com.todaysoft.lims.technical.model;

import java.util.Date;


public class AbnormalSolveRecord
{
    private String Id;
    
    private String taskId;
    
    private String strategy;
    
    private String remark;
    
    private String solverId;
    
    private String solverName;
    
    private Date solveTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSolverId() {
        return solverId;
    }

    public void setSolverId(String solverId) {
        this.solverId = solverId;
    }

    public String getSolverName() {
        return solverName;
    }

    public void setSolverName(String solverName) {
        this.solverName = solverName;
    }

    public Date getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(Date solveTime) {
        this.solveTime = solveTime;
    }
}
