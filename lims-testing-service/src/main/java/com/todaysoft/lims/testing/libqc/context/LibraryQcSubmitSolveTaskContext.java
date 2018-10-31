package com.todaysoft.lims.testing.libqc.context;

import java.util.Map;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class LibraryQcSubmitSolveTaskContext
{
    private String strategy;
    
    private TestingTask unqualifiedTask;
    
    private TestingTask solvedTask;
    
    private Map<String, LibraryQcSubmitScheduleContext> relatedSchedules;
    
    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    public TestingTask getUnqualifiedTask()
    {
        return unqualifiedTask;
    }
    
    public void setUnqualifiedTask(TestingTask unqualifiedTask)
    {
        this.unqualifiedTask = unqualifiedTask;
    }
    
    public TestingTask getSolvedTask()
    {
        return solvedTask;
    }
    
    public void setSolvedTask(TestingTask solvedTask)
    {
        this.solvedTask = solvedTask;
    }
    
    public Map<String, LibraryQcSubmitScheduleContext> getRelatedSchedules()
    {
        return relatedSchedules;
    }
    
    public void setRelatedSchedules(Map<String, LibraryQcSubmitScheduleContext> relatedSchedules)
    {
        this.relatedSchedules = relatedSchedules;
    }
}
