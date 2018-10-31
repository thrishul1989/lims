package com.todaysoft.lims.testing.dt.context;

import java.util.*;

import com.todaysoft.lims.testing.base.entity.*;

import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class DTSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private Map<String, DTSubmitTaskContext> tasks = new HashMap<String, DTSubmitTaskContext>();

    private Map<String, DTSubmitNextTaskContext> nextTasks = new HashMap<String, DTSubmitNextTaskContext>();

    private Map<String, DTSubmitNextTaskContext> nextTasksByIds = new HashMap<String, DTSubmitNextTaskContext>();

    private Map<String, DTSubmitScheduleContext> gotoNextSchedules = new HashMap<String, DTSubmitScheduleContext>();

    public Set<DTSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<DTSubmitTaskContext>(tasks.values());
    }

    public Set<DTSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<DTSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<DTSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<DTSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        DTSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
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
    }

    public void setContextForTestingTask(TestingTask task)
    {
        DTSubmitTaskContext context = new DTSubmitTaskContext();
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive, TaskConfig nextNodeConfig)
    {
        DTSubmitScheduleContext scheduleContext = new DTSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);
        String nextNodeInputSampleId = task.getInputSample().getId();
        scheduleContext.setNextNodeConfig(nextNodeConfig);
        scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
        gotoNextSchedules.put(schedule.getId(), scheduleContext);


        DTSubmitNextTaskContext nextTaskContext = new DTSubmitNextTaskContext();
        nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId);
        nextTaskContext.setScheduleId(schedule.getId());
        nextTaskContext.setInputSampleId(nextNodeInputSampleId);
        nextTaskContext.setTestingSample(task.getInputSample());
        nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
        nextTaskContext.setTaskName(nextNodeConfig.getName());
        nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        DTSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
