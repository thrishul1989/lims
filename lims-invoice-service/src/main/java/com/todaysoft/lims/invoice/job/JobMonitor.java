package com.todaysoft.lims.invoice.job;

public class JobMonitor
{
    private boolean start;
    
    private boolean finish;
    
    public boolean isStartable()
    {
        return (start && finish) || !start;
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
