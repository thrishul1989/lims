package com.todaysoft.lims.testing.resampling.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingResamplingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TestingNode;

public class ResamplingRestartContext
{
    private TestingSample resendSample;
    
    private Map<String, ResamplingRestartTaskContext> tasks = new HashMap<String, ResamplingRestartTaskContext>();
    
    private Map<String, ResamplingRestartScheduleContext> schedules = new HashMap<String, ResamplingRestartScheduleContext>();
    
    public TestingSample getResendSample()
    {
        return resendSample;
    }
    
    public Set<ResamplingRestartTaskContext> getRestartTasks()
    {
        return new HashSet<ResamplingRestartTaskContext>(tasks.values());
    }
    
    public Set<ResamplingRestartScheduleContext> getSchedules()
    {
        return new HashSet<ResamplingRestartScheduleContext>(schedules.values());
    }
    
    public void setContextForResendSample(TestingSample resendSample)
    {
        this.resendSample = resendSample;
    }
    
    public void setContextForScheduleUnrestartable(TestingSchedule schedule, TestingResamplingSchedule resamplingSchedule, String error)
    {
        ResamplingRestartScheduleContext scheduleContext = schedules.get(schedule.getId());
        
        if (null == scheduleContext)
        {
            scheduleContext = new ResamplingRestartScheduleContext();
            scheduleContext.setRestartable(false);
            scheduleContext.setRestartError(error);
            scheduleContext.setSchedule(schedule);
            scheduleContext.setResamplingSchedules(new HashMap<String, TestingResamplingSchedule>());
            schedules.put(schedule.getId(), scheduleContext);
        }
        
        if (!scheduleContext.getResamplingSchedules().containsKey(resamplingSchedule.getId()))
        {
            scheduleContext.getResamplingSchedules().put(resamplingSchedule.getId(), resamplingSchedule);
        }
    }
    
    public void setContextForScheduleRestart(TestingSchedule schedule, TestingResamplingSchedule resamplingSchedule, TestingNode node, String scheduleNodes)
    {
        ResamplingRestartScheduleContext scheduleContext = schedules.get(schedule.getId());
        
        if (null == scheduleContext)
        {
            scheduleContext = new ResamplingRestartScheduleContext();
            scheduleContext.setRestartable(true);
            scheduleContext.setSchedule(schedule);
            scheduleContext.setScheduleNodes(scheduleNodes);
            scheduleContext.setNextTasks(new HashMap<String, ResamplingRestartTaskContext>());
            scheduleContext.setResamplingSchedules(new HashMap<String, TestingResamplingSchedule>());
            schedules.put(schedule.getId(), scheduleContext);
        }
        
        if (!scheduleContext.getResamplingSchedules().containsKey(resamplingSchedule.getId()))
        {
            scheduleContext.getResamplingSchedules().put(resamplingSchedule.getId(), resamplingSchedule);
        }
        
        ResamplingRestartTaskContext taskContext = tasks.get(node.getType() + "_" + resendSample.getId());
        
        if (null == taskContext)
        {
            taskContext = new ResamplingRestartTaskContext();
            taskContext.setInputSample(resendSample);
            taskContext.setTaskName(node.getName());
            taskContext.setTaskSemantic(node.getType());
            tasks.put(taskContext.getTemporaryId(), taskContext);
        }
        
        if (!scheduleContext.getNextTasks().containsKey(taskContext.getTemporaryId()))
        {
            scheduleContext.getNextTasks().put(taskContext.getTemporaryId(), taskContext);
        }
    }
    
    public void setContextForCreateRestartTask(ResamplingRestartTaskContext taskContext, TestingTask restartTask)
    {
        taskContext.setTestingTaskEntity(restartTask);
    }
}
