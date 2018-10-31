package com.todaysoft.lims.sample.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ResamplingCancelSolveRecordModel
{
    private String cancelRecordId;
    
    private String strategy;
    
    private String riskTestingNode;
    
    private String remark;
    
    private String solverId;
    
    private String solverName;
    
    private Date solveTime;
    
    public String getCancelRecordId()
    {
        return cancelRecordId;
    }

    public void setCancelRecordId(String cancelRecordId)
    {
        this.cancelRecordId = cancelRecordId;
    }

    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    public String getRiskTestingNode()
    {
        return riskTestingNode;
    }
    
    public void setRiskTestingNode(String riskTestingNode)
    {
        this.riskTestingNode = riskTestingNode;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getSolverId()
    {
        return solverId;
    }
    
    public void setSolverId(String solverId)
    {
        this.solverId = solverId;
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
}
