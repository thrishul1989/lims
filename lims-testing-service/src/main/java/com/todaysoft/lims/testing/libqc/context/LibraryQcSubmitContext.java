package com.todaysoft.lims.testing.libqc.context;

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
import com.todaysoft.lims.testing.libqc.model.LibraryQcSubmitTaskArgs;

public class LibraryQcSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private Map<String, LibraryQcSubmitTaskContext> tasks = new HashMap<String, LibraryQcSubmitTaskContext>();
    
    private Map<String, LibraryQcSubmitNextTaskContext> nextTasks = new HashMap<String, LibraryQcSubmitNextTaskContext>();
    
    private Map<String, LibraryQcSubmitNextTaskContext> nextTasksByIds = new HashMap<String, LibraryQcSubmitNextTaskContext>();
    
    private Map<String, LibraryQcSubmitSolveTaskContext> solveTasks = new HashMap<String, LibraryQcSubmitSolveTaskContext>();
    
    private Map<String, LibraryQcSubmitTaskArgs> submitTaskResults = new HashMap<String, LibraryQcSubmitTaskArgs>();
    
    private Map<String, LibraryQcSubmitScheduleContext> gotoNextSchedules = new HashMap<String, LibraryQcSubmitScheduleContext>();
    
    private Map<String, LibraryQcSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, LibraryQcSubmitScheduleContext>();
    
    private Map<String, LibraryQcSubmitScheduleContext> gotoSolveSchedules = new HashMap<String, LibraryQcSubmitScheduleContext>();
    
    public Map<String, LibraryQcSubmitNextTaskContext> getNextTasks()
    {
        return nextTasks;
    }

    public void setNextTasks(Map<String, LibraryQcSubmitNextTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }

    public Set<LibraryQcSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<LibraryQcSubmitTaskContext>(tasks.values());
    }
    
    public Set<LibraryQcSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<LibraryQcSubmitNextTaskContext>(nextTasks.values());
    }
    
    public Set<LibraryQcSubmitSolveTaskContext> getSolveUnqualifiedTasks()
    {
        return new HashSet<LibraryQcSubmitSolveTaskContext>(solveTasks.values());
    }
    
    public Set<LibraryQcSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<LibraryQcSubmitScheduleContext>(gotoNextSchedules.values());
    }
    
    public Set<LibraryQcSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<LibraryQcSubmitScheduleContext>(gotoErrorSchedules.values());
    }
    
    public Set<LibraryQcSubmitScheduleContext> getGotoSolveSchedules()
    {
        return new HashSet<LibraryQcSubmitScheduleContext>(gotoSolveSchedules.values());
    }
    
    public TestingTask getNextTask(String scheduleId, String inputSampleId)
    {
        LibraryQcSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
        return nextTaskContext.getTestingTaskEntity();
    }
    
    public TestingSheet getSheetEntity()
    {
        return this.sheetEntity;
    }
    
    public UserMinimalModel getSubmiter()
    {
        return this.submiter;
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForTestingSheet(TestingSheet sheet)
    {
        this.sheetEntity = sheet;
    }
    
    public void setContextForSubmitRequest(List<LibraryQcSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }
        
        for (LibraryQcSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        LibraryQcSubmitTaskArgs result = submitTaskResults.get(task.getId());
        
        if (null == result)
        {
            throw new IllegalStateException();
        }
        
        LibraryQcSubmitTaskContext context = new LibraryQcSubmitTaskContext();
        context.setConcn(result.getConcn());
        context.setVolume(result.getVolume());
        context.setRatio2628(result.getRatio2628());
        context.setRatio2623(result.getRatio2623());
        context.setFragmentLengthStart(result.getFragmentLengthStart());
        context.setFragmentLengthEnd(result.getFragmentLengthEnd());
        context.setQualityLevel(result.getQualityLevel());
        context.setQualified(result.isQualified());
        context.setUnqualifiedRemark(result.getUnqualifiedRemark());
        context.setUnqualifiedStrategy(result.getUnqualifiedStrategy());
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }
    
    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TaskConfig nextNodeConfig)
    {
        LibraryQcSubmitTaskContext taskContext = tasks.get(task.getId());
        
        LibraryQcSubmitScheduleContext scheduleContext = new LibraryQcSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        
        if (taskContext.isQualified())
        {
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setQualified(true);
            scheduleContext.setNextNodeConfig(nextNodeConfig);
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId(), scheduleContext);
            
            LibraryQcSubmitNextTaskContext nextTaskContext = new LibraryQcSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId);
            nextTaskContext.setInputSampleId(nextNodeInputSampleId);
            nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
            nextTaskContext.setTaskName(nextNodeConfig.getName());
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }
        else
        {
            scheduleContext.setQualified(false);
            scheduleContext.setUnqualifiedRemark(taskContext.getUnqualifiedRemark());
            scheduleContext.setUnqualifiedStrategy(taskContext.getUnqualifiedStrategy());
            
            if ("1".equals(taskContext.getUnqualifiedStrategy()))
            {
                gotoErrorSchedules.put(schedule.getId(), scheduleContext);
            }
            else
            {
                LibraryQcSubmitSolveTaskContext solveTaskContext = solveTasks.get(task.getId());
                
                if (null == solveTaskContext)
                {
                    solveTaskContext = new LibraryQcSubmitSolveTaskContext();
                    solveTaskContext.setUnqualifiedTask(task);
                    solveTaskContext.setStrategy(taskContext.getUnqualifiedStrategy());
                    solveTaskContext.setRelatedSchedules(new HashMap<String, LibraryQcSubmitScheduleContext>());
                    solveTasks.put(task.getId(), solveTaskContext);
                }
                
                if (!solveTaskContext.getRelatedSchedules().containsKey(schedule.getId()))
                {
                    solveTaskContext.getRelatedSchedules().put(schedule.getId(), scheduleContext);
                }
                
                gotoSolveSchedules.put(schedule.getId(), scheduleContext);
            }
        }
    }
    
    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        LibraryQcSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
