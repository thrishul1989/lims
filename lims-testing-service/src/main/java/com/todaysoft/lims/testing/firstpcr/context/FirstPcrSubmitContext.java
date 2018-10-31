package com.todaysoft.lims.testing.firstpcr.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.*;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSheetVariables;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSubmitTaskArgs;

public class FirstPcrSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private FirstPcrSheetVariables firstPcrSheetVariables;
    
    private Map<String, FirstPcrSubmitTaskContext> tasks = new HashMap<String, FirstPcrSubmitTaskContext>();
    
    private Map<String, FirstPcrSubmitNextTaskContext> nextTasks = new HashMap<String, FirstPcrSubmitNextTaskContext>();
    
    private Map<String, FirstPcrSubmitNextTaskContext> nextTasksByIds = new HashMap<String, FirstPcrSubmitNextTaskContext>();
    
    private Map<String, FirstPcrSubmitTaskArgs> submitTaskResults = new HashMap<String, FirstPcrSubmitTaskArgs>();
    
    private Map<String, FirstPcrSubmitScheduleContext> gotoNextSchedules = new HashMap<String, FirstPcrSubmitScheduleContext>();
    
    private Map<String, FirstPcrSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, FirstPcrSubmitScheduleContext>();

    private Map<String, TestingTask> primerTasks = new HashMap<String, TestingTask>();
    
    private String qcPointTestCode;
    
    private String qcPointPrimerLocation;
    
    public Set<FirstPcrSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<FirstPcrSubmitTaskContext>(tasks.values());
    }
    
    public Set<FirstPcrSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<FirstPcrSubmitNextTaskContext>(nextTasks.values());
    }
    
    public Set<FirstPcrSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<FirstPcrSubmitScheduleContext>(gotoNextSchedules.values());
    }
    
    public Set<FirstPcrSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<FirstPcrSubmitScheduleContext>(gotoErrorSchedules.values());
    }
    
    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        FirstPcrSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
        return nextTaskContext.getTestingTaskEntity().getId();
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
        firstPcrSheetVariables = JsonUtils.asObject(sheet.getVariables(), FirstPcrSheetVariables.class);
    }
    
    public void setContextForSubmitRequest(List<FirstPcrSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }
        
        for (FirstPcrSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        FirstPcrSubmitTaskArgs result = submitTaskResults.get(task.getId());
        
        if (null == result)
        {
            throw new IllegalStateException();
        }
        
        FirstPcrSubmitTaskContext context = new FirstPcrSubmitTaskContext();
        context.setResult(result.getResult());
        context.setRemark(result.getRemark());
        context.setDispose(result.getDispose());
        context.setTestingTaskEntity(task);
        context.setPcrTaskCode(result.getPcrTaskCode());
        context.setPcrTestCode(result.getPcrTestCode());
        context.setForwardPrimerLocationTemp(result.getForwardPrimerLocationTemp());
        tasks.put(task.getId(), context);
    }
    
    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive, TaskConfig nextNodeConfig)
    {
        FirstPcrSubmitTaskContext taskContext = tasks.get(task.getId());
        
        FirstPcrSubmitScheduleContext scheduleContext = new FirstPcrSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);
        
        if (taskContext.getResult().intValue() == 0)
        {
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setNextNodeConfig(nextNodeConfig);
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId(), scheduleContext);
            
            FirstPcrSubmitNextTaskContext nextTaskContext = new FirstPcrSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId);
            nextTaskContext.setScheduleId(schedule.getId());
            nextTaskContext.setInputSampleId(nextNodeInputSampleId);
            nextTaskContext.setTestingSample(task.getInputSample());
            nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
            nextTaskContext.setTaskName(nextNodeConfig.getName());
            nextTaskContext.setPcrTaskCode(taskContext.getPcrTaskCode());
            nextTaskContext.setPcrTestCode(taskContext.getPcrTestCode());
            nextTaskContext.setForwardPrimerLocationTemp(taskContext.getForwardPrimerLocationTemp());
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }
        else
        {
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setDispose(taskContext.getDispose());
            scheduleContext.setRemark(taskContext.getRemark());
            scheduleContext.setTestingTask(task);
            gotoErrorSchedules.put(schedule.getId(), scheduleContext);
        }
    }
    
    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task, TestingTaskRunVariable nextTaskVariables)
    {
        FirstPcrSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTask.setTestingTaskVariablesEntity(nextTaskVariables);
        nextTasksByIds.put(task.getId(), nextTask);
    }
    
    public FirstPcrSubmitNextTaskContext getNextTaskConextById(String id)
    {
        return nextTasksByIds.get(id);
    }
    
    public FirstPcrSheetVariables getFirstPcrSheetVariables()
    {
        return firstPcrSheetVariables;
    }
    
    public String getQcPointTestCode()
    {
        return qcPointTestCode;
    }
    
    public void setQcPointTestCode(String qcPointTestCode)
    {
        this.qcPointTestCode = qcPointTestCode;
    }
    
    public String getQcPointPrimerLocation()
    {
        return qcPointPrimerLocation;
    }
    
    public void setQcPointPrimerLocation(String qcPointPrimerLocation)
    {
        this.qcPointPrimerLocation = qcPointPrimerLocation;
    }

    public Map<String, TestingTask> getPrimerTasks() {
        return primerTasks;
    }

    public void setPrimerTasks(Map<String, TestingTask> primerTasks) {
        this.primerTasks = primerTasks;
    }

    public void setContextForCreateSangerPrimerPrepareTask(TestingTechnicalAnalyRecord record, TestingTask task)
    {
        String key = record.getChromosome() + "_" + record.getBeginLocus() + "_" + record.getVerifyMethod();
        primerTasks.put(key, task);
    }

    public TestingTask getPrimerTask(TestingTechnicalAnalyRecord record)
    {
        String key = record.getChromosome() + "_" + record.getBeginLocus() + "_" + record.getVerifyMethod();
        return primerTasks.get(key);
    }
}
