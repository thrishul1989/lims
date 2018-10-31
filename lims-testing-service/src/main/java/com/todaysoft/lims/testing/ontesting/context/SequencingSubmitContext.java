package com.todaysoft.lims.testing.ontesting.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.ontesting.model.SequencingSubmitRequest;

public class SequencingSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private SequencingSubmitTaskContext task;
    
    private SequencingSubmitNextTaskContext nextTask;
    
    private TaskConfig nextNodeConfig;
    
    private Map<String, SequencingSubmitScheduleContext> schedules = new HashMap<String, SequencingSubmitScheduleContext>();
    
    public SequencingSubmitTaskContext getTask()
    {
        return this.task;
    }
    
    public SequencingSubmitNextTaskContext getNextTask()
    {
        return this.nextTask;
    }
    
    public Set<SequencingSubmitScheduleContext> getRelatedSchedules()
    {
        return new HashSet<SequencingSubmitScheduleContext>(schedules.values());
    }
    
    public UserMinimalModel getSubmiter()
    {
        return submiter;
    }
    
    public TestingSheet getSheetEntity()
    {
        return sheetEntity;
    }
    
    public TaskConfig getNextNodeConfig()
    {
        return nextNodeConfig;
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForTestingSheet(TestingSheet entity)
    {
        this.sheetEntity = entity;
    }
    
    public void setContextForTestingTask(TestingTask testingTask)
    {
        this.task = new SequencingSubmitTaskContext();
        this.task.setTestingTaskEntity(testingTask);
    }
    
    public void setContextForTestingResult(SequencingSubmitRequest request)
    {
        this.task.setCluster(request.getCluster());
        this.task.setEffectiveRate(request.getEffectiveRate());
        this.task.setEffectiveSize(request.getEffectiveSize());
        this.task.setQ30(request.getQ30());
    }
    
    public void setContextForTestingSchedule(TestingSchedule schedule, TaskConfig nextNodeConfig)
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
        
        SequencingSubmitScheduleContext scheduleContext = new SequencingSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        schedules.put(schedule.getId(), scheduleContext);
    }
    
    public void setContextForNextNodeTaskConfig()
    {
        nextTask = new SequencingSubmitNextTaskContext();
        nextTask.setTaskName(nextNodeConfig.getName());
        nextTask.setTaskSemantic(nextNodeConfig.getSemantic());
        nextTask.setInputSample(task.getTestingTaskEntity().getInputSample());
    }
    
    public void setContextForCreateNextNodeTask(TestingTask task)
    {
        nextTask.setTestingTaskEntity(task);
    }
}
