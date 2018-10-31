package com.todaysoft.lims.testing.pooling.context;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.pooling.model.PoolingSheetVariables;
import com.todaysoft.lims.testing.pooling.model.PoolingTaskVariables;

public class PoolingSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private PoolingSheetVariables sheetVariables;
    
    private TaskConfig nextNodeConfig;
    
    private TestingSample outputSample;
    
    private PoolingSubmitNextTaskContext nextTask;
    
    private Map<String, PoolingSubmitTaskContext> tasks = new HashMap<String, PoolingSubmitTaskContext>();
    
    private Set<PoolingSubmitTaskContext> poolingTasks = new HashSet<PoolingSubmitTaskContext>();
    
    private Map<String, PoolingSubmitScheduleContext> schedules = new HashMap<String, PoolingSubmitScheduleContext>();
    
    private Map<String, PoolingSubmitScheduleContext> gotoNextSchedules = new HashMap<String, PoolingSubmitScheduleContext>();
    
    private Map<String, PoolingSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, PoolingSubmitScheduleContext>();
    
    public UserMinimalModel getSubmiter()
    {
        return submiter;
    }
    
    public TestingSheet getSheetEntity()
    {
        return sheetEntity;
    }
    
    public String getOutputSampleCode()
    {
        return sheetVariables.getPoolingCode();
    }
    
    public TaskConfig getNextNodeConfig()
    {
        return nextNodeConfig;
    }
    
    public boolean isTaskPoolingable(String taskId)
    {
        PoolingSubmitTaskContext taskContext = tasks.get(taskId);
        return taskContext.isPoolingable();
    }
    
    public boolean isPoolingable()
    {
        return !poolingTasks.isEmpty();
    }
    
    public Set<PoolingSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<PoolingSubmitTaskContext>(tasks.values());
    }
    
    public Set<PoolingSubmitTaskContext> getPoolingTasks()
    {
        return poolingTasks;
    }
    
    public PoolingSubmitNextTaskContext getNextTask()
    {
        return nextTask;
    }
    
    public Set<PoolingSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<PoolingSubmitScheduleContext>(gotoNextSchedules.values());
    }
    
    public Set<PoolingSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<PoolingSubmitScheduleContext>(gotoErrorSchedules.values());
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForTestingSheet(TestingSheet entity)
    {
        this.sheetEntity = entity;
    }
    
    public void setContextForTestingSheetVariables(PoolingSheetVariables sheetVariables)
    {
        this.sheetVariables = sheetVariables;
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        PoolingSubmitTaskContext taskContext = new PoolingSubmitTaskContext();
        taskContext.setId(task.getId());
        taskContext.setTestingTaskEntity(task);
        tasks.put(task.getId(), taskContext);
    }
    
    public void setContextForTestingTaskVariables(String taskId, PoolingTaskVariables variables)
    {
        PoolingSubmitTaskContext taskContext = tasks.get(taskId);
        taskContext.setTestingTaskVariables(variables);
        
        boolean poolingable = null != variables.getRqtv() && BigDecimal.ZERO.compareTo(variables.getRqtv()) != 0;
        taskContext.setPoolingable(poolingable);
        
        if (poolingable)
        {
            poolingTasks.add(taskContext);
        }
    }
    
    public void setContextForTestingSchedule(TestingSchedule schedule)
    {
        PoolingSubmitScheduleContext scheduleContext = schedules.get(schedule.getId());
        
        if (null == scheduleContext)
        {
            scheduleContext = new PoolingSubmitScheduleContext();
            scheduleContext.setId(schedule.getId());
            scheduleContext.setTestingScheduleEntity(schedule);
            schedules.put(schedule.getId(), scheduleContext);
            gotoNextSchedules.put(schedule.getId(), scheduleContext);
        }
    }
    
    public void setContextForTestingScheduleNextNode(TaskConfig nextNodeConfig)
    {
        if (null == this.nextNodeConfig)
        {
            this.nextNodeConfig = nextNodeConfig;
        }
        else
        {
            if (!this.nextNodeConfig.getSemantic().equals(nextNodeConfig.getSemantic()))
            {
                // 所有流程下一节点任务应该一样
                throw new IllegalStateException();
            }
        }
    }
    
    public void setContextForTestingScheduleError(String scheduleId)
    {
        PoolingSubmitScheduleContext scheduleContext = schedules.get(scheduleId);
        gotoNextSchedules.remove(scheduleId);
        gotoErrorSchedules.put(scheduleId, scheduleContext);
    }
    
    public void setContextForCreateOutputSample(TestingSample outputSample)
    {
        this.outputSample = outputSample;
    }
    
    public void setContextForNextNodeTaskConfig()
    {
        nextTask = new PoolingSubmitNextTaskContext();
        nextTask.setTaskName(nextNodeConfig.getName());
        nextTask.setTaskSemantic(nextNodeConfig.getSemantic());
        nextTask.setInputSample(outputSample);
    }
    
    public void setContextForCreateNextNodeTask(TestingTask task)
    {
        nextTask.setTestingTaskEntity(task);
    }
}
