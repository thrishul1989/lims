package com.todaysoft.lims.testing.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;
@Entity
@Table(name = "LIMS_TESTING_ABNORMAL_VIEW")
public class AbnormalRecord extends UuidKeyEntity{


    private String taskId;

    private String strategy;

    private String remark;

    private String solverId;

    private String solverName;

    private Date solveTime;

    private String sampleCode;



    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    @Column(name = "STRATEGY")
    public String getStrategy()
    {
        return strategy;
    }

    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }

    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Column(name = "SOLVER_ID")
    public String getSolverId()
    {
        return solverId;
    }

    public void setSolverId(String solverId)
    {
        this.solverId = solverId;
    }

    @Column(name = "SOLVER_NAME")
    public String getSolverName()
    {
        return solverName;
    }

    public void setSolverName(String solverName)
    {
        this.solverName = solverName;
    }

    @Column(name = "SOLVE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSolveTime()
    {
        return solveTime;
    }

    public void setSolveTime(Date solveTime)
    {
        this.solveTime = solveTime;
    }

    @Column(name = "SAMPLE_CODE")
    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }
}
