package com.todaysoft.lims.testing.rqt.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.rqt.model.RQTSheetVariables;
import com.todaysoft.lims.testing.rqt.model.RQTSubmitTaskArgs;
import com.todaysoft.lims.testing.rqt.model.RQTTaskVariables;

public class RQTSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private RQTSheetVariables sheetVariables;
    
    private Map<String, RQTSubmitTaskArgs> taskArgs = new HashMap<String, RQTSubmitTaskArgs>();
    
    private Map<String, RQTSubmitTaskContext> tasks = new HashMap<String, RQTSubmitTaskContext>();
    
    private Map<String, RQTSubmitNextTaskContext> nextTasks = new HashMap<String, RQTSubmitNextTaskContext>();
    
    private Map<String, RQTSubmitNextTaskContext> nextTasksByIds = new HashMap<String, RQTSubmitNextTaskContext>();
    
    private Map<String, RQTSubmitScheduleContext> schedules = new HashMap<String, RQTSubmitScheduleContext>();
    
    public UserMinimalModel getSubmiter()
    {
        return submiter;
    }
    
    public TestingSheet getSheetEntity()
    {
        return sheetEntity;
    }
    
    public RQTSheetVariables getSheetVariables()
    {
        return sheetVariables;
    }
    
    public Set<RQTSubmitScheduleContext> getRelatedSchedules()
    {
        return new HashSet<RQTSubmitScheduleContext>(schedules.values());
    }
    
    public Set<RQTSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<RQTSubmitTaskContext>(tasks.values());
    }
    
    public Set<RQTSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<RQTSubmitNextTaskContext>(nextTasks.values());
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForTestingSheet(TestingSheet entity)
    {
        this.sheetEntity = entity;
    }
    
    public void setContextForTestingSheetVariables(RQTSheetVariables variables)
    {
        this.sheetVariables = variables;
    }
    
    public void setContextForTaskArgs(List<RQTSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }
        
        for (RQTSubmitTaskArgs task : tasks)
        {
            taskArgs.put(task.getId(), task);
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        RQTSubmitTaskArgs result = taskArgs.get(task.getId());
        
        if (null == result)
        {
            throw new IllegalStateException();
        }
        
        RQTSubmitTaskContext context = new RQTSubmitTaskContext();
        context.setCtv(result.getCtv());
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }
    
    public void setContextForTestingTaskVariables(String taskId, RQTTaskVariables variables)
    {
        RQTSubmitTaskContext context = tasks.get(taskId);
        context.setTestingCode(variables.getTestingCode());
        context.setPoolingConcn(variables.getPoolingConcn());
        context.setSampleInputVolume(variables.getSampleInputVolume());
        context.setTeInputVolume(variables.getTeInputVolume());
        context.setTestingDatasize(variables.getTestingDatasize());
    }
    
    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TaskConfig nextNodeConfig)
    {
        RQTSubmitTaskContext taskContext = tasks.get(task.getId());
        
        String nextTaskTemporaryId = task.getId() + "_N";
        RQTSubmitNextTaskContext nextTaskContext = nextTasks.get(nextTaskTemporaryId);
        
        if (null == nextTaskContext)
        {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("testingCode", taskContext.getTestingCode());
            variables.put("ctv", taskContext.getCtv());
            variables.put("poolingConcn", taskContext.getPoolingConcn());
            variables.put("testingDatasize", taskContext.getTestingDatasize());
            
            nextTaskContext = new RQTSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(task.getId() + "_N");
            nextTaskContext.setInputSampleId(task.getInputSample().getId());
            nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
            nextTaskContext.setTaskName(nextNodeConfig.getName());
            nextTaskContext.setVariables(variables);
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }
        
        RQTSubmitScheduleContext scheduleContext = schedules.get(schedule.getId());
        
        if (null == scheduleContext)
        {
            scheduleContext = new RQTSubmitScheduleContext();
            scheduleContext.setId(schedule.getId());
            scheduleContext.setNextNodeConfig(nextNodeConfig);
            scheduleContext.setTestingScheduleEntity(schedule);
            scheduleContext.setNextTasks(new HashSet<RQTSubmitNextTaskContext>());
            schedules.put(schedule.getId(), scheduleContext);
        }
        
        boolean exists = false;
        
        for (RQTSubmitNextTaskContext ntc : scheduleContext.getNextTasks())
        {
            if (ntc.getTemporaryId().equals(nextTaskContext.getTemporaryId()))
            {
                exists = true;
            }
        }
        
        if (!exists)
        {
            scheduleContext.getNextTasks().add(nextTaskContext);
        }
    }
    
    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        RQTSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
