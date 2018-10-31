package com.todaysoft.lims.testing.libcre.context;

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
import com.todaysoft.lims.testing.libcre.model.LibraryCreateSheetVariables;
import com.todaysoft.lims.testing.libcre.model.LibraryCreateTaskVariables;

public class LibraryCreateSheetSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet testingSheetEntity;
    
    private LibraryCreateSheetVariables libraryCreateSheetVariables;
    
    private Map<String, LibraryCreateSheetSubmitTaskContext> tasks = new HashMap<String, LibraryCreateSheetSubmitTaskContext>();
    
    private Map<String, LibraryCreateSheetSubmitNextTaskContext> nextTasks = new HashMap<String, LibraryCreateSheetSubmitNextTaskContext>();
    
    private Map<String, LibraryCreateSheetSubmitScheduleContext> schedules = new HashMap<String, LibraryCreateSheetSubmitScheduleContext>();
    
    private Map<String, TestingSample> outputSamples = new HashMap<String, TestingSample>();
    
    private Map<String, LibraryCreateSheetSubmitNextTaskContext> nextTasksByIds = new HashMap<String, LibraryCreateSheetSubmitNextTaskContext>();
    
    public Map<String, LibraryCreateSheetSubmitNextTaskContext> getNextTasks()
    {
        return nextTasks;
    }
    
    public void setNextTasks(Map<String, LibraryCreateSheetSubmitNextTaskContext> nextTasks)
    {
        this.nextTasks = nextTasks;
    }
    
    public void setContextForCreateTestingSheet(TestingSheet sheet)
    {
        testingSheetEntity = sheet;
        libraryCreateSheetVariables = JsonUtils.asObject(sheet.getVariables(), LibraryCreateSheetVariables.class);
    }
    
    public void setConextForTestingTaskEntity(TestingTask task)
    {
        LibraryCreateSheetSubmitTaskContext taskContext = obtainTaskContext(task.getId());
        taskContext.setTestingTaskEntity(task);
    }
    
    public void setConextForTestingTaskVariables(String taskId, LibraryCreateTaskVariables variables)
    {
        LibraryCreateSheetSubmitTaskContext taskContext = obtainTaskContext(taskId);
        taskContext.setTestingCode(variables.getTestingCode());
        taskContext.setLibraryIndex(variables.getLibraryIndex());
        taskContext.setOutputSampleTypeId(variables.getOutputSampleTypeId());
        taskContext.setOutputSampleTypeName(variables.getOutputSampleTypeName());
        taskContext.setOutputSampleCode(variables.getOutputSampleCode());
        taskContext.setOutputSampleSize(variables.getOutputSampleSize());
    }
    
    public void setContextForTestingTaskRelatedSchedule(String taskId, TestingSchedule schedule, TaskConfig nextNodeConfig)
    {
        LibraryCreateSheetSubmitTaskContext taskContext = obtainTaskContext(taskId);
        
        LibraryCreateSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(taskContext.getOutputSampleCode(), nextNodeConfig.getSemantic());
        nextTaskContext.setSampleSize(taskContext.getOutputSampleSize());
        nextTaskContext.setSampleTypeId(taskContext.getOutputSampleTypeId());
        nextTaskContext.setSampleTypeName(taskContext.getOutputSampleTypeName());
        nextTaskContext.setTestingCode(taskContext.getTestingCode());
        nextTaskContext.setTaskName(nextNodeConfig.getName());
        
        LibraryCreateSheetSubmitScheduleContext scheduleContext = obtainScheduleContext(schedule.getId());
        scheduleContext.setId(schedule.getId());
        scheduleContext.setNextNodeInputSampleCode(taskContext.getOutputSampleCode());
        scheduleContext.setNextNodeConfig(nextNodeConfig);
        scheduleContext.setTestingScheduleEntity(schedule);
    }
    
    public void setContextForCreateNextNodeTask(String sampleCode, String semantic, TestingTask nextTask, TestingTaskRunVariable nextTaskVariables)
    {
        LibraryCreateSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(sampleCode, semantic);
        nextTaskContext.setTestingTaskEntity(nextTask);
        nextTaskContext.setTestingTaskVariablesEntity(nextTaskVariables);
        
        nextTasksByIds.put(nextTask.getId(), nextTaskContext);
    }
    
    public void setContextForInsertOutputSample(TestingSample outputSample)
    {
        outputSamples.put(outputSample.getSampleCode(), outputSample);
    }
    
    private LibraryCreateSheetSubmitTaskContext obtainTaskContext(String taskId)
    {
        LibraryCreateSheetSubmitTaskContext taskContext = tasks.get(taskId);
        
        if (null == taskContext)
        {
            taskContext = new LibraryCreateSheetSubmitTaskContext();
            taskContext.setId(taskId);
            tasks.put(taskId, taskContext);
        }
        
        return taskContext;
    }
    
    private LibraryCreateSheetSubmitScheduleContext obtainScheduleContext(String scheduleId)
    {
        LibraryCreateSheetSubmitScheduleContext scheduleContext = schedules.get(scheduleId);
        
        if (null == scheduleContext)
        {
            scheduleContext = new LibraryCreateSheetSubmitScheduleContext();
            scheduleContext.setId(scheduleId);
            schedules.put(scheduleId, scheduleContext);
        }
        
        return scheduleContext;
    }
    
    private LibraryCreateSheetSubmitNextTaskContext obtainNextTaskContext(String outputSampleCode, String nextTaskSemantic)
    {
        String key = outputSampleCode + "_" + nextTaskSemantic;
        LibraryCreateSheetSubmitNextTaskContext nextTaskContext = nextTasks.get(key);
        
        if (null == nextTaskContext)
        {
            nextTaskContext = new LibraryCreateSheetSubmitNextTaskContext();
            nextTaskContext.setSampleCode(outputSampleCode);
            nextTaskContext.setTaskSemantic(nextTaskSemantic);
            nextTasks.put(key, nextTaskContext);
        }
        
        return nextTaskContext;
    }
    
    public TestingTask getNextTask(String sampleCode, String semantic)
    {
        LibraryCreateSheetSubmitNextTaskContext nextTaskContext = obtainNextTaskContext(sampleCode, semantic);
        return nextTaskContext.getTestingTaskEntity();
    }
    
    public LibraryCreateSheetSubmitNextTaskContext getNextTaskConextById(String id)
    {
        return nextTasksByIds.get(id);
    }
    
    public Set<LibraryCreateSheetSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<LibraryCreateSheetSubmitTaskContext>(tasks.values());
    }
    
    public Set<LibraryCreateSheetSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<LibraryCreateSheetSubmitNextTaskContext>(nextTasks.values());
    }
    
    public Set<LibraryCreateSheetSubmitScheduleContext> getRelatedSchedules()
    {
        return new HashSet<LibraryCreateSheetSubmitScheduleContext>(schedules.values());
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
    
    public TestingSheet getLibraryCreateSheetEntity()
    {
        return testingSheetEntity;
    }
    
    public LibraryCreateSheetVariables getLibraryCreateSheetVariables()
    {
        return libraryCreateSheetVariables;
    }
}
