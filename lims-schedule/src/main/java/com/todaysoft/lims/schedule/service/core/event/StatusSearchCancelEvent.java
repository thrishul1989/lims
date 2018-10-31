package com.todaysoft.lims.schedule.service.core.event;

public class StatusSearchCancelEvent
{
    private String scheduleId;

    public String getScheduleId()
    {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
}
