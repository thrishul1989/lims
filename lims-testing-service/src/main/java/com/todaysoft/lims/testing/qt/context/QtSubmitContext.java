package com.todaysoft.lims.testing.qt.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.qt.model.QtSubmitRequest;
import com.todaysoft.lims.testing.qt.model.QtSubmitSampleArgs;

public class QtSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private QtSubmitTaskContext task;
    
    private QtSubmitNextTaskContext nextTask;
    
    private TaskConfig nextNodeConfig;
    
    private QtSubmitRequest result;
    
    private Map<String, QtSubmitScheduleContext> schedules = new HashMap<String, QtSubmitScheduleContext>();
    
    public TestingSheet getSheetEntity()
    {
        return this.sheetEntity;
    }
    
    public QtSubmitTaskContext getTask()
    {
        return this.task;
    }
    
    public TaskConfig getNextNodeConfig()
    {
        return nextNodeConfig;
    }
    
    public QtSubmitNextTaskContext getNextTask()
    {
        return this.nextTask;
    }
    
    public QtSubmitRequest getSubmitResult()
    {
        return result;
    }
    
    public UserMinimalModel getSubmiter()
    {
        return this.submiter;
    }
    
    public Set<QtSubmitScheduleContext> getRelatedSchedules()
    {
        return new HashSet<QtSubmitScheduleContext>(schedules.values());
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForSubmitResult(QtSubmitRequest request)
    {
        this.result = request;
    }
    
    public void setContextForTestingSheet(TestingSheet sheet)
    {
        this.sheetEntity = sheet;
    }
    
    public void setContextForTestingTask(TestingTask testingTask)
    {
        this.task = new QtSubmitTaskContext();
        this.task.setTestingTaskEntity(testingTask);
    }
    
    public void setContextForPrimarySampleQTResult(QtSubmitSampleArgs args)
    {
        this.task.setConcn(args.getConcn());
        this.task.setFragmentLength(args.getFragmentLength());
        this.task.setLibrarySizeOfQbit(args.getLibrarySizeOfQbit());
        this.task.setLibrarySizeOfQPCR(args.getLibrarySizeOfQPCR());
        this.task.setLibrarySizeOf2100(args.getLibrarySizeOf2100());
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
        
        QtSubmitScheduleContext scheduleContext = new QtSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        schedules.put(schedule.getId(), scheduleContext);
    }
    
    public void setContextForNextNodeTaskConfig()
    {
        nextTask = new QtSubmitNextTaskContext();
        nextTask.setTaskName(nextNodeConfig.getName());
        nextTask.setTaskSemantic(nextNodeConfig.getSemantic());
        nextTask.setInputSample(task.getTestingTaskEntity().getInputSample());
    }
    
    public void setContextForCreateNextNodeTask(TestingTask task)
    {
        nextTask.setTestingTaskEntity(task);
    }
}
