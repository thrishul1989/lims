package com.todaysoft.lims.testing.cdnaext.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractSheetVariables;
import com.todaysoft.lims.testing.cdnaext.model.CDNAExtractTaskVariables;

public class CDNAExtractSheetSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet testingSheetEntity;
    
    private CDNAExtractSheetVariables extractSheetVariables;
    
    private Map<String, CDNAExtractSheetSubmitTaskContext> tasks = new HashMap<String, CDNAExtractSheetSubmitTaskContext>();
    
    private Map<String, String> scheduleTaskMapping = new HashMap<String, String>();
    
    private Map<String, CDNAExtractSheetSubmitNextTaskContext> nextTasks = new HashMap<String, CDNAExtractSheetSubmitNextTaskContext>();
    
    private Map<String, CDNAExtractSheetSubmitScheduleContext> schedules = new HashMap<String, CDNAExtractSheetSubmitScheduleContext>();
    
    private Map<String, TestingSample> outputSamples = new HashMap<String, TestingSample>();
    
    private Map<String, CDNAExtractSheetSubmitNextTaskContext> nextTasksByIds = new HashMap<String, CDNAExtractSheetSubmitNextTaskContext>();
    
    public Map<String, CDNAExtractSheetSubmitNextTaskContext> getNextTasks()
    {
        return nextTasks;
    }

    public void setNextTasks(Map<String, CDNAExtractSheetSubmitNextTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }

    public void setContextForExtractTestingSheet(TestingSheet sheet)
    {
        testingSheetEntity = sheet;
        extractSheetVariables = JsonUtils.asObject(sheet.getVariables(), CDNAExtractSheetVariables.class);
    }
    
    public void setConextForTestingTaskEntity(TestingTask task)
    {
        CDNAExtractSheetSubmitTaskContext taskContext = obtainTaskContext(task.getId());
        taskContext.setTestingTaskEntity(task);
    }
    
    public void setConextForTestingTaskVariables(String taskId, CDNAExtractTaskVariables variables)
    {
        CDNAExtractSheetSubmitTaskContext taskContext = obtainTaskContext(taskId);
        taskContext.setTestingCode(variables.getTestingCode());
        taskContext.setOutputSampleTypeId(variables.getOutputSampleTypeId());
        taskContext.setOutputSampleTypeName(variables.getOutputSampleTypeName());
        taskContext.setOutputSampleCode(variables.getOutputSampleCode());
        taskContext.setOutputSampleSize(variables.getOutputSampleSize());
    }
    
    public void setContextForTestingTaskRelatedSchedule(String taskId, TestingSchedule schedule, TaskConfig nextNodeConfig)
    {
        scheduleTaskMapping.put(schedule.getId(), taskId);
        
        CDNAExtractSheetSubmitTaskContext taskContext = obtainTaskContext(taskId);
        
        CDNAExtractSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(taskContext.getOutputSampleCode(), nextNodeConfig.getSemantic());
        nextTaskContext.setSampleSize(taskContext.getOutputSampleSize());
        nextTaskContext.setSampleTypeId(taskContext.getOutputSampleTypeId());
        nextTaskContext.setSampleTypeName(taskContext.getOutputSampleTypeName());
        nextTaskContext.setTestingCode(taskContext.getTestingCode());
        nextTaskContext.setTaskName(nextNodeConfig.getName());
        
        CDNAExtractSheetSubmitScheduleContext scheduleContext = obtainScheduleContext(schedule.getId());
        scheduleContext.setId(schedule.getId());
        scheduleContext.setNextNodeInputSampleCode(taskContext.getOutputSampleCode());
        scheduleContext.setNextNodeConfig(nextNodeConfig);
        scheduleContext.setTestingScheduleEntity(schedule);
    }
    
    public void setContextForCreateNextNodeTask(String sampleCode, String semantic, TestingTask nextTask, TestingTaskRunVariable nextTaskVariables)
    {
        CDNAExtractSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(sampleCode, semantic);
        nextTaskContext.setTestingTaskEntity(nextTask);
        nextTaskContext.setTestingTaskVariablesEntity(nextTaskVariables);
        
        nextTasksByIds.put(nextTask.getId(), nextTaskContext);
    }
    
    public void setContextForInsertOutputSample(TestingSample outputSample)
    {
        outputSamples.put(outputSample.getSampleCode(), outputSample);
    }
    
    private CDNAExtractSheetSubmitTaskContext obtainTaskContext(String taskId)
    {
        CDNAExtractSheetSubmitTaskContext taskContext = tasks.get(taskId);
        
        if (null == taskContext)
        {
            taskContext = new CDNAExtractSheetSubmitTaskContext();
            taskContext.setId(taskId);
            tasks.put(taskId, taskContext);
        }
        
        return taskContext;
    }
    
    private CDNAExtractSheetSubmitScheduleContext obtainScheduleContext(String scheduleId)
    {
        CDNAExtractSheetSubmitScheduleContext scheduleContext = schedules.get(scheduleId);
        
        if (null == scheduleContext)
        {
            scheduleContext = new CDNAExtractSheetSubmitScheduleContext();
            scheduleContext.setId(scheduleId);
            schedules.put(scheduleId, scheduleContext);
        }
        
        return scheduleContext;
    }
    
    private CDNAExtractSheetSubmitNextTaskContext obtainNextTaskContext(String outputSampleCode, String nextTaskSemantic)
    {
        String key = outputSampleCode + "_" + nextTaskSemantic;
        CDNAExtractSheetSubmitNextTaskContext nextTaskContext = nextTasks.get(key);
        
        if (null == nextTaskContext)
        {
            nextTaskContext = new CDNAExtractSheetSubmitNextTaskContext();
            nextTaskContext.setSampleCode(outputSampleCode);
            nextTaskContext.setTaskSemantic(nextTaskSemantic);
            nextTasks.put(key, nextTaskContext);
        }
        
        return nextTaskContext;
    }
    
    public TestingTask getNextTask(String sampleCode, String semantic)
    {
        CDNAExtractSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(sampleCode, semantic);
        return nextTaskContext.getTestingTaskEntity();
    }
    
    public CDNAExtractSheetSubmitNextTaskContext getNextTaskConextById(String id)
    {
        return nextTasksByIds.get(id);
    }
    
    public Set<CDNAExtractSheetSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<CDNAExtractSheetSubmitTaskContext>(tasks.values());
    }
    
    public Map<String, String> getScheduleTaskMapping()
    {
        return scheduleTaskMapping;
    }
    
    public Set<CDNAExtractSheetSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<CDNAExtractSheetSubmitNextTaskContext>(nextTasks.values());
    }
    
    public Set<CDNAExtractSheetSubmitScheduleContext> getRelatedSchedules()
    {
        return new HashSet<CDNAExtractSheetSubmitScheduleContext>(schedules.values());
    }
    
    public UserMinimalModel getSubmiter()
    {
        return submiter;
    }
    
    public TestingSample getOutputSampleEntity(String outputSampleCode)
    {
        return outputSamples.get(outputSampleCode);
    }
    
    public void setSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public TestingSheet getExtractSheetEntity()
    {
        return testingSheetEntity;
    }
    
    public CDNAExtractSheetVariables getExtractSheetVariables()
    {
        return extractSheetVariables;
    }
}
