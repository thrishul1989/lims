package com.todaysoft.lims.system.modules.bpm.abnormal.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;

public class AbnormalSolveRecord
{
    private String id;
    
    private String taskId;
    
    private String strategy;
    
    private String sheetCode;
    
    private String strategyName;
    
    private String remark;
    
    private String solverId;
    
    private String solverName;
    
    private Date solveTime;
    
    private TestingTask testingTask;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private List<AbnormalSolveSheduleRecord> sheduleList;
    
    public String getSheetCode()
    {
        return sheetCode;
    }
    
    public void setSheetCode(String sheetCode)
    {
        this.sheetCode = sheetCode;
    }
    
    public String getStrategyName()
    {
        return strategyName;
    }
    
    public void setStrategyName(String strategyName)
    {
        this.strategyName = strategyName;
    }
    
    public List<AbnormalSolveSheduleRecord> getSheduleList()
    {
        return sheduleList;
    }
    
    public void setSheduleList(List<AbnormalSolveSheduleRecord> sheduleList)
    {
        this.sheduleList = sheduleList;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
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
    
    public TestingTask getTestingTask()
    {
        return testingTask;
    }
    
    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }
    
}
