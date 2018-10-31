package com.todaysoft.lims.testing.base.model;

import java.util.List;

public class VariableModel
{
    private String scheduleIds;
    
    private List<TestingStartRecord> startRecors;
    
    public List<TestingStartRecord> getStartRecors()
    {
        return startRecors;
    }
    
    public void setStartRecors(List<TestingStartRecord> startRecors)
    {
        this.startRecors = startRecors;
    }
    
    public String getScheduleIds()
    {
        return scheduleIds;
    }
    
    public void setScheduleIds(String scheduleIds)
    {
        this.scheduleIds = scheduleIds;
    }
}
