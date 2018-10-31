package com.todaysoft.lims.testing.base.entity;

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

@Entity
@Table(name = "LIMS_TESTING_RESAMPLING_CANCEL_SOLVE")
public class ResamplingCancelSolveRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = -3566365450332868090L;
    
    public static final String STRATEGY_CANCEL_TESTING = "1";
    
    public static final String STRATEGY_RISK_TESTING = "2";
    
    private TestingResamplingRecord record;
    
    private String strategy;
    
    private String riskTestingNode;
    
    private String remark;
    
    private String solverId;
    
    private String solverName;
    
    private Date solveTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECORD_ID")
    public TestingResamplingRecord getRecord()
    {
        return record;
    }
    
    public void setRecord(TestingResamplingRecord record)
    {
        this.record = record;
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
    
    @Column(name = "RISK_TESTING_NODE")
    public String getRiskTestingNode()
    {
        return riskTestingNode;
    }
    
    public void setRiskTestingNode(String riskTestingNode)
    {
        this.riskTestingNode = riskTestingNode;
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
}
