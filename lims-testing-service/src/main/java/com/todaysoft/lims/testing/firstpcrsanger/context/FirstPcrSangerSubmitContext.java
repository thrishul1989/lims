package com.todaysoft.lims.testing.firstpcrsanger.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerSheetVariables;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerSubmitTaskArgs;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class FirstPcrSangerSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private FirstPcrSangerSheetVariables firstPcrSheetVariables;
    
    private Map<String, FirstPcrSangerSubmitTaskContext> tasks = new HashMap<String, FirstPcrSangerSubmitTaskContext>();
    
    private Map<String, FirstPcrSangerSubmitNextTaskContext> nextTasks = new HashMap<String, FirstPcrSangerSubmitNextTaskContext>();
    
    private Map<String, TestingSample> nextTaskTestingSamples = new HashMap<String, TestingSample>();
    
    private Map<String, FirstPcrSangerSubmitNextTaskContext> nextTasksByIds = new HashMap<String, FirstPcrSangerSubmitNextTaskContext>();
    
    private Map<String, FirstPcrSangerSubmitTaskArgs> submitTaskResults = new HashMap<String, FirstPcrSangerSubmitTaskArgs>();
    
    private Map<String, FirstPcrSangerSubmitScheduleContext> gotoNextSchedules = new HashMap<String, FirstPcrSangerSubmitScheduleContext>();
    
    private Map<String, FirstPcrSangerSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, FirstPcrSangerSubmitScheduleContext>();
    
    private Map<String, FirstPcrSangerSubmitScheduleContext> abnormalCancerTasks = new HashMap<String, FirstPcrSangerSubmitScheduleContext>();
    
    public Map<String, FirstPcrSangerSubmitNextTaskContext> getNextTasks()
    {
        return nextTasks;
    }
    
    public void setNextTasks(Map<String, FirstPcrSangerSubmitNextTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }
    
    public Set<FirstPcrSangerSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<FirstPcrSangerSubmitTaskContext>(tasks.values());
    }
    
    public Set<FirstPcrSangerSubmitScheduleContext> getAbnormalCancerTasks()
    {
        return new HashSet<FirstPcrSangerSubmitScheduleContext>(abnormalCancerTasks.values());
    }
    
    public Set<FirstPcrSangerSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<FirstPcrSangerSubmitNextTaskContext>(nextTasks.values());
    }
    
    public Set<FirstPcrSangerSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<FirstPcrSangerSubmitScheduleContext>(gotoNextSchedules.values());
    }
    
    public Set<FirstPcrSangerSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<FirstPcrSangerSubmitScheduleContext>(gotoErrorSchedules.values());
    }
    
    public TestingTask getNextTaskCreatedId(String scheduleId, String taskId)
    {
        FirstPcrSangerSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + taskId);
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
        firstPcrSheetVariables = JsonUtils.asObject(sheet.getVariables(), FirstPcrSangerSheetVariables.class);
    }
    
    public void setContextForSubmitRequest(List<FirstPcrSangerSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }
        
        for (FirstPcrSangerSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        FirstPcrSangerSubmitTaskArgs result = submitTaskResults.get(task.getId());
        
        if (null == result)
        {
            throw new IllegalStateException();
        }
        
        FirstPcrSangerSubmitTaskContext context = new FirstPcrSangerSubmitTaskContext();
        context.setResult(result.getResult());
        context.setRemark(result.getRemark());
        context.setDispose(result.getDispose());
        context.setTestingTaskEntity(task);
        context.setPcrTaskCode(result.getPcrTaskCode());
        context.setPcrTestCode(result.getPcrTestCode());
        context.setForwardPrimerLocationTemp(result.getForwardPrimerLocationTemp());
        context.setCombineCode(result.getCombineCode());
        context.setPrimerId(result.getPrimerId());
        context.setRunningStatus(result.getRunningStatus());
        tasks.put(task.getId(), context);
    }
    
    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive, TaskConfig nextNodeConfig, String productId)
    {
        FirstPcrSangerSubmitTaskContext taskContext = tasks.get(task.getId());
        int result = taskContext.getResult().intValue();
        FirstPcrSangerSubmitScheduleContext scheduleContext = new FirstPcrSangerSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);
        if (taskContext.getRunningStatus().intValue() == TestingScheduleActive.STATUS_CANCER)
        {
            result = 3;
        }
        
        if (result == 0)
        {
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setNextNodeConfig(nextNodeConfig);
            scheduleContext.setTestingTask(task);
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId() + "_" + task.getId(), scheduleContext);
            
            FirstPcrSangerSubmitNextTaskContext nextTaskContext = new FirstPcrSangerSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId() + "_" + task.getId());
            nextTaskContext.setScheduleId(schedule.getId());
            nextTaskContext.setInputSampleId(nextNodeInputSampleId);
            nextTaskContext.setTestingSample(task.getInputSample());
            nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
            nextTaskContext.setTaskName(nextNodeConfig.getName());
            nextTaskContext.setPcrTaskCode(taskContext.getPcrTaskCode());
            nextTaskContext.setPcrTestCode(taskContext.getPcrTestCode());
            nextTaskContext.setCombineCode(taskContext.getCombineCode());
            nextTaskContext.setForwardPrimerLocationTemp(taskContext.getForwardPrimerLocationTemp());
            nextTaskContext.setPrimerId(taskContext.getPrimerId());
            nextTaskContext.setRunningStatus(taskContext.getRunningStatus());
            nextTaskContext.setPreTestingTaskEntity(task);
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }
        else if (result == 1)
        {
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setDispose(taskContext.getDispose());
            scheduleContext.setRemark(taskContext.getRemark());
            scheduleContext.setTestingTask(task);
            gotoErrorSchedules.put(schedule.getId() + "_" + task.getId(), scheduleContext);
        }
        else
        {
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setDispose(taskContext.getDispose());
            scheduleContext.setRemark(taskContext.getRemark());
            scheduleContext.setTestingTask(task);
            abnormalCancerTasks.put(task.getId(), scheduleContext);
        }
    }
    
    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task, TestingTaskRunVariable nextTaskVariables)
    {
        FirstPcrSangerSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTask.setTestingTaskVariablesEntity(nextTaskVariables);
        nextTasksByIds.put(task.getId(), nextTask);
    }
    
    public FirstPcrSangerSubmitNextTaskContext getNextTaskConextById(String id)
    {
        return nextTasksByIds.get(id);
    }
    
    public FirstPcrSangerSheetVariables getFirstPcrSheetVariables()
    {
        return firstPcrSheetVariables;
    }
    
    public Map<String, TestingSample> getNextTaskTestingSamples()
    {
        return nextTaskTestingSamples;
    }
    
    public TestingSample getNextTaskTestingSampleByTempId(String temId)
    {
        return nextTaskTestingSamples.get(temId);
    }
    
    public void setNextTaskTestingSample(String temId, TestingSample testingSample)
    {
        nextTaskTestingSamples.put(temId, testingSample);
    }
    
    public void setNextTaskTestingSamples(Map<String, TestingSample> nextTaskTestingSamples)
    {
        this.nextTaskTestingSamples = nextTaskTestingSamples;
    }
}
