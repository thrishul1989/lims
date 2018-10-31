package com.todaysoft.lims.maintain.commons;

public class Monitor
{
    private String name;
    
    private int totalCount;
    
    private int processedCount;
    
    private boolean start;
    
    private boolean finish;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public boolean isStartable()
    {
        return (start && finish) || !start;
    }
    
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
