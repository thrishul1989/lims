package com.todaysoft.lims.system.service.impl;

public class Monitor
{
    private int totalCount;
    
    private int processedCount;
    
    private boolean start;
    
    private boolean finish;
    
    public int getTotalCount()
    {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
    
    public int getProcessedCount()
    {
        return processedCount;
    }
    
    public void setProcessedCount(int processedCount)
    {
        this.processedCount = processedCount;
    }
    
    public boolean isStart()
    {
        return start;
    }
    
    public void setStart(boolean start)
    {
        this.start = start;
    }
    
    public boolean isFinish()
    {
        return finish;
    }
    
    public void setFinish(boolean finish)
    {
        this.finish = finish;
    }
}
