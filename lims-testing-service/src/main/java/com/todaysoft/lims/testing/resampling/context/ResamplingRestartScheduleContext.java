package com.todaysoft.lims.testing.resampling.context;

import java.util.Map;

import com.todaysoft.lims.testing.base.entity.TestingResamplingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;

public class ResamplingRestartScheduleContext
{
    private boolean restartable;
    
    private String restartError;
    
    private TestingSchedule schedule;
    
    private String scheduleNodes;
    
    private Map<String, ResamplingRestartTaskContext> nextTasks;
    
    private Map<String, TestingResamplingSchedule> resamplingSchedules;
    
    public boolean isRestartable()
    {
        return restartable;
    }
    
    public void setRestartable(boolean restartable)
    {
        this.restartable = restartable;
    }
    
    public String getRestartError()
    {
        return restartError;
    }
    
    public void setRestartError(String restartError)
    {
        this.restartError = restartError;
    }
    
    public TestingSchedule getSchedule()
    {
        return schedule;
    }
    
    public void setSchedule(TestingSchedule schedule)
    {
        this.schedule = schedule;
    }
    
    public String getScheduleNodes()
    {
        return scheduleNodes;
    }
    
    public void setScheduleNodes(String scheduleNodes)
    {
        this.scheduleNodes = scheduleNodes;
    }
    
    public Map<String, ResamplingRestartTaskContext> getNextTasks()
    {
        return nextTasks;
    }
    
    public void setNextTasks(Map<String, ResamplingRestartTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }
    
    public Map<String, TestingResamplingSchedule> getResamplingSchedules()
    {
        return resamplingSchedules;
    }
    
    public void setResamplingSchedules(Map<String, TestingResamplingSchedule> resamplingSchedules)
    {
        this.resamplingSchedules = resamplingSchedules;
    }
}
