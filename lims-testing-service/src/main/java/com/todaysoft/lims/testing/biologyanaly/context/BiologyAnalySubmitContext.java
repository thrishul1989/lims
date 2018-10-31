package com.todaysoft.lims.testing.biologyanaly.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class BiologyAnalySubmitContext
{
    private UserMinimalModel submiter;
    
    private BiologyAnalySubmitTaskContext taskContext;
    
    private TestingSample mixedSampleEntity;
    
    private Map<String, BiologyAnalySubmitNextTaskContext> nextTasksContext = new HashMap<String, BiologyAnalySubmitNextTaskContext>();
    
    private Map<String, String> scheduleLibraryMapping = new HashMap<String, String>();
    
    private Map<String, BiologyAnalySubmitLibraryContext> libraries = new HashMap<String, BiologyAnalySubmitLibraryContext>();
    
    private Map<String, BiologyAnalySubmitScheduleContext> schedules = new HashMap<String, BiologyAnalySubmitScheduleContext>();
    
    public void setNextTasksContext(Map<String, BiologyAnalySubmitNextTaskContext> nextTasksContext)
    {
        this.nextTasksContext = nextTasksContext;
    }
    
    public String getSequencingCode()
    {
        return mixedSampleEntity.getSampleCode();
    }
    
    public boolean containsLibrary(String library)
    {
        return libraries.containsKey(library);
    }
    
    public BiologyAnalySubmitTaskContext getSubmitTaskContext()
    {
        return this.taskContext;
    }
    
    public Set<BiologyAnalySubmitNextTaskContext> getNextTasksContext()
    {
        return new HashSet<BiologyAnalySubmitNextTaskContext>(nextTasksContext.values());
    }
    
    public Set<BiologyAnalySubmitScheduleContext> getSchedulesContext()
    {
        return new HashSet<BiologyAnalySubmitScheduleContext>(schedules.values());
    }
    
    public TestingTask getScheduleNextTask(String scheduleId)
    {
        BiologyAnalySubmitNextTaskContext nextTaskContext = nextTasksContext.get(scheduleId);
        return nextTaskContext.getTestingTaskEntity();
    }
    
    public UserMinimalModel getSubmiter()
    {
        return submiter;
    }
    
    public void setContextForSubmiter(UserMinimalModel submiter)
    {
        this.submiter = submiter;
    }
    
    public void setContextForTestingTask(TestingTask testingTask)
    {
        this.taskContext = new BiologyAnalySubmitTaskContext();
        this.taskContext.setTestingTaskEntity(testingTask);
    }
    
    public void setContextForMixedSample(TestingSample mixedSample)
    {
        this.mixedSampleEntity = mixedSample;
    }
    
    public void setContextForTestingSchedule(TestingSchedule schedule, TaskConfig scheduleNextNodeConfig)
    {
        if (null != scheduleNextNodeConfig)
        {
            BiologyAnalySubmitScheduleContext scheduleContext = new BiologyAnalySubmitScheduleContext();
            scheduleContext.setTestingScheduleEntity(schedule);
            scheduleContext.setTestingScheduleNextNodeConfig(scheduleNextNodeConfig);
            schedules.put(schedule.getId(), scheduleContext);
            
            String sampleId = scheduleLibraryMapping.get(schedule.getId());
            
            BiologyAnalySubmitNextTaskContext nextTaskContext = new BiologyAnalySubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId());
            nextTaskContext.setInputSampleId(sampleId);
            nextTaskContext.setScheduleId(schedule.getId());
            nextTaskContext.setTaskName(scheduleNextNodeConfig.getName());
            nextTaskContext.setTaskSemantic(scheduleNextNodeConfig.getSemantic());
            nextTasksContext.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }
        
    }
    
    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask entity)
    {
        BiologyAnalySubmitNextTaskContext nextTaskContext = nextTasksContext.get(temporaryId);
        nextTaskContext.setTestingTaskEntity(entity);
    }
    
    public void setContextForLibrarySample(TestingSample library, String index, TestingSchedule relatedSchedule)
    {
        if (libraries.containsKey(library.getSampleCode()))
        {
            return;
        }
        
        BiologyAnalySubmitLibraryContext libraryContext = new BiologyAnalySubmitLibraryContext();
        libraryContext.setIndex(index);
        libraryContext.setSampleCode(library.getSampleCode());
        libraryContext.setReceivedSampleEntity(library.getReceivedSample());
        libraries.put(library.getSampleCode(), libraryContext);
        
        scheduleLibraryMapping.put(relatedSchedule.getId(), library.getId());
    }
}
