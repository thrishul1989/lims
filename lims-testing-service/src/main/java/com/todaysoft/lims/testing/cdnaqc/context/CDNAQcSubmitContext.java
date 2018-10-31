package com.todaysoft.lims.testing.cdnaqc.context;

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
import com.todaysoft.lims.testing.cdnaqc.model.CDNAQcSubmitTaskModel;

public class CDNAQcSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private Map<String, CDNAQcSubmitTaskContext> tasks = new HashMap<String, CDNAQcSubmitTaskContext>();
    
    private Map<String, CDNAQcSubmitNextTaskContext> nextTasks = new HashMap<String, CDNAQcSubmitNextTaskContext>();
    
    private Map<String, CDNAQcSubmitNextTaskContext> nextTasksByIds = new HashMap<String, CDNAQcSubmitNextTaskContext>();
    
    private Map<String, CDNAQcSubmitSolveTaskContext> solveTasks = new HashMap<String, CDNAQcSubmitSolveTaskContext>();
    
    private Map<String, CDNAQcSubmitTaskModel> submitTaskResults = new HashMap<String, CDNAQcSubmitTaskModel>();
    
    private Map<String, CDNAQcSubmitScheduleContext> gotoNextSchedules = new HashMap<String, CDNAQcSubmitScheduleContext>();
    
    private Map<String, CDNAQcSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, CDNAQcSubmitScheduleContext>();
    
    private Map<String, CDNAQcSubmitScheduleContext> gotoSolveSchedules = new HashMap<String, CDNAQcSubmitScheduleContext>();
    
    public Map<String, CDNAQcSubmitNextTaskContext> getNextTasks()
    {
        return nextTasks;
    }

    public void setNextTasks(Map<String, CDNAQcSubmitNextTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }

    public Set<CDNAQcSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<CDNAQcSubmitTaskContext>(tasks.values());
    }
    
    public Set<CDNAQcSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<CDNAQcSubmitNextTaskContext>(nextTasks.values());
    }
    
    public Set<CDNAQcSubmitSolveTaskContext> getSolveUnqualifiedTasks()
    {
        return new HashSet<CDNAQcSubmitSolveTaskContext>(solveTasks.values());
    }
    
    public Set<CDNAQcSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<CDNAQcSubmitScheduleContext>(gotoNextSchedules.values());
    }
    
    public Set<CDNAQcSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<CDNAQcSubmitScheduleContext>(gotoErrorSchedules.values());
    }
    
    public TestingTask getNextTask(String scheduleId, String inputSampleId)
    {
        CDNAQcSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
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
    
    public void setContextForSubmitRequest(List<CDNAQcSubmitTaskModel> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }
        
        for (CDNAQcSubmitTaskModel task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        CDNAQcSubmitTaskModel result = submitTaskResults.get(task.getId());
        
        if (null == result)
        {
            throw new IllegalStateException();
        }
        
        CDNAQcSubmitTaskContext context = new CDNAQcSubmitTaskContext();
        context.setConcn(result.getConcn());
        context.setVolume(result.getVolume());
        context.setRatio2628(result.getRatio2628());
        context.setRatio2623(result.getRatio2623());
        context.setQualityLevel(result.getQualityLevel());
        context.setQualified(result.isQualified());
        context.setUnqualifiedRemark(result.getUnqualifiedRemark());
        context.setUnqualifiedStrategy(result.getUnqualifiedStrategy());
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }
    
    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TaskConfig nextNodeConfig)
    {
        CDNAQcSubmitTaskContext taskContext = tasks.get(task.getId());
        
        CDNAQcSubmitScheduleContext scheduleContext = new CDNAQcSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        
        if (taskContext.isQualified())
        {
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setQualified(true);
            scheduleContext.setNextNodeConfig(nextNodeConfig);
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId(), scheduleContext);
            
            CDNAQcSubmitNextTaskContext nextTaskContext = new CDNAQcSubmitNextTaskContext();
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
                CDNAQcSubmitSolveTaskContext solveTaskContext = solveTasks.get(task.getId());
                
                if (null == solveTaskContext)
                {
                    solveTaskContext = new CDNAQcSubmitSolveTaskContext();
                    solveTaskContext.setUnqualifiedTask(task);
                    solveTaskContext.setStrategy(taskContext.getUnqualifiedStrategy());
                    solveTaskContext.setRelatedSchedules(new HashMap<String, CDNAQcSubmitScheduleContext>());
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
        CDNAQcSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
