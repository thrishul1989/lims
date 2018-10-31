package com.todaysoft.lims.schedule.service;

import com.todaysoft.lims.schedule.service.core.event.ProgramMonitorNewBiologyEvent;

public interface IProgramMonitorNewBiologyService
{
    void updatePlan(ProgramMonitorNewBiologyEvent programMonitorNewBiologyEvent);
}
