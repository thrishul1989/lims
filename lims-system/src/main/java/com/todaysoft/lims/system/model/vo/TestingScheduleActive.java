package com.todaysoft.lims.system.model.vo;

import com.todaysoft.lims.system.model.vo.order.TestingSchedule;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;

public class TestingScheduleActive
{
    public static final int STATUS_NORMAL = 0;//正常
    
    public static final int STATUS_CANCER = 3;//已取消
    
    private TestingSchedule schedule;
    
    public TestingSchedule getSchedule()
    {
        return schedule;
    }
    
    public void setSchedule(TestingSchedule schedule)
    {
        this.schedule = schedule;
    }
    
    public TestingTask getTask()
    {
        return task;
    }
    
    public void setTask(TestingTask task)
    {
        this.task = task;
    }
    
    public Integer getRunningStatus()
    {
        return runningStatus;
    }
    
    public void setRunningStatus(Integer runningStatus)
    {
        this.runningStatus = runningStatus;
    }
    
    private TestingTask task;
    
    private Integer runningStatus;//任务运行状态0-正常 3-实验取消1-暂停
}
