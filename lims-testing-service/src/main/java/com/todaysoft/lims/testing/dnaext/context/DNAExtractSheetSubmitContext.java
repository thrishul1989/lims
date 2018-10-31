package com.todaysoft.lims.testing.dnaext.context;

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
import com.todaysoft.lims.testing.dnaext.model.DNAExtractSheetVariables;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractTaskVariables;

public class DNAExtractSheetSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet testingSheetEntity;
    
    private DNAExtractSheetVariables extractSheetVariables;
    
    private Map<String, DNAExtractSheetSubmitTaskContext> tasks = new HashMap<String, DNAExtractSheetSubmitTaskContext>();
    
    private Map<String, DNAExtractSheetSubmitNextTaskContext> nextTasks = new HashMap<String, DNAExtractSheetSubmitNextTaskContext>();
    
    private Map<String, DNAExtractSheetSubmitScheduleContext> schedules = new HashMap<String, DNAExtractSheetSubmitScheduleContext>();
    
    private Map<String, TestingSample> outputSamples = new HashMap<String, TestingSample>();
    
    private Map<String, DNAExtractSheetSubmitNextTaskContext> nextTasksByIds = new HashMap<String, DNAExtractSheetSubmitNextTaskContext>();
    
    public Map<String, DNAExtractSheetSubmitNextTaskContext> getNextTasks()
    {
        return nextTasks;
    }
    
    public void setNextTasks(Map<String, DNAExtractSheetSubmitNextTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }
    
    public void setContextForExtractTestingSheet(TestingSheet sheet)
    {
        testingSheetEntity = sheet;
        extractSheetVariables = JsonUtils.asObject(sheet.getVariables(), DNAExtractSheetVariables.class);
    }
    
    public void setConextForTestingTaskEntity(TestingTask task)
    {
        DNAExtractSheetSubmitTaskContext taskContext = obtainTaskContext(task.getId());
        taskContext.setTestingTaskEntity(task);
    }
    
    public void setConextForTestingTaskVariables(String taskId, DNAExtractTaskVariables variables)
    {
        DNAExtractSheetSubmitTaskContext taskContext = obtainTaskContext(taskId);
        taskContext.setTestingCode(variables.getTestingCode());
        taskContext.setOutputSampleTypeId(variables.getOutputSampleTypeId());
        taskContext.setOutputSampleTypeName(variables.getOutputSampleTypeName());
        taskContext.setOutputSampleCode(variables.getOutputSampleCode());
        taskContext.setOutputSampleSize(variables.getOutputSampleSize());
    }
    
    public void setContextForTestingTaskRelatedSchedule(String taskId, TestingSchedule schedule, TaskConfig nextNodeConfig)
    {
        DNAExtractSheetSubmitTaskContext taskContext = obtainTaskContext(taskId);
        
        DNAExtractSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(taskContext.getOutputSampleCode(), nextNodeConfig.getSemantic());
        nextTaskContext.setSampleSize(taskContext.getOutputSampleSize());
        nextTaskContext.setSampleTypeId(taskContext.getOutputSampleTypeId());
        nextTaskContext.setSampleTypeName(taskContext.getOutputSampleTypeName());
        nextTaskContext.setTestingCode(taskContext.getTestingCode());
        nextTaskContext.setTaskName(nextNodeConfig.getName());
        
        DNAExtractSheetSubmitScheduleContext scheduleContext = obtainScheduleContext(schedule.getId());
        scheduleContext.setId(schedule.getId());
        scheduleContext.setNextNodeInputSampleCode(taskContext.getOutputSampleCode());
        scheduleContext.setNextNodeConfig(nextNodeConfig);
        scheduleContext.setTestingScheduleEntity(schedule);
    }
    
    public void setContextForCreateNextNodeTask(String sampleCode, String semantic, TestingTask nextTask, TestingTaskRunVariable nextTaskVariables)
    {
        DNAExtractSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(sampleCode, semantic);
        nextTaskContext.setTestingTaskEntity(nextTask);
        nextTaskContext.setTestingTaskVariablesEntity(nextTaskVariables);
        
        nextTasksByIds.put(nextTask.getId(), nextTaskContext);
    }
    
    public void setContextForInsertOutputSample(TestingSample outputSample)
    {
        outputSamples.put(outputSample.getSampleCode(), outputSample);
    }
    
    private DNAExtractSheetSubmitTaskContext obtainTaskContext(String taskId)
    {
        DNAExtractSheetSubmitTaskContext taskContext = tasks.get(taskId);
        
        if (null == taskContext)
        {
            taskContext = new DNAExtractSheetSubmitTaskContext();
            taskContext.setId(taskId);
            tasks.put(taskId, taskContext);
        }
        
        return taskContext;
    }
    
    private DNAExtractSheetSubmitScheduleContext obtainScheduleContext(String scheduleId)
    {
        DNAExtractSheetSubmitScheduleContext scheduleContext = schedules.get(scheduleId);
        
        if (null == scheduleContext)
        {
            scheduleContext = new DNAExtractSheetSubmitScheduleContext();
            scheduleContext.setId(scheduleId);
            schedules.put(scheduleId, scheduleContext);
        }
        
        return scheduleContext;
    }
    
    private DNAExtractSheetSubmitNextTaskContext obtainNextTaskContext(String outputSampleCode, String nextTaskSemantic)
    {
        String key = outputSampleCode + "_" + nextTaskSemantic;
        DNAExtractSheetSubmitNextTaskContext nextTaskContext = nextTasks.get(key);
        
        if (null == nextTaskContext)
        {
            nextTaskContext = new DNAExtractSheetSubmitNextTaskContext();
            nextTaskContext.setSampleCode(outputSampleCode);
            nextTaskContext.setTaskSemantic(nextTaskSemantic);
            nextTasks.put(key, nextTaskContext);
        }
        
        return nextTaskContext;
    }
    
    public TestingTask getNextTask(String sampleCode, String semantic)
    {
        DNAExtractSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(sampleCode, semantic);
        return nextTaskContext.getTestingTaskEntity();
    }
    
    public DNAExtractSheetSubmitNextTaskContext getNextTaskConextById(String id)
    {
        return nextTasksByIds.get(id);
    }
    
    public Set<DNAExtractSheetSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<DNAExtractSheetSubmitTaskContext>(tasks.values());
    }
    
    public Set<DNAExtractSheetSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<DNAExtractSheetSubmitNextTaskContext>(nextTasks.values());
    }
    
    public Set<DNAExtractSheetSubmitScheduleContext> getRelatedSchedules()
    {
        return new HashSet<DNAExtractSheetSubmitScheduleContext>(schedules.values());
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
    
    public DNAExtractSheetVariables getExtractSheetVariables()
    {
        return extractSheetVariables;
    }
}
