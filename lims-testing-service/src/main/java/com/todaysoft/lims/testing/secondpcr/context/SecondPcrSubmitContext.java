package com.todaysoft.lims.testing.secondpcr.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.secondpcr.model.SecondPcrSubmitTaskArgs;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class SecondPcrSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private Map<String, SecondPcrSubmitTaskContext> tasks = new HashMap<String, SecondPcrSubmitTaskContext>();

    private Map<String, SecondPcrSubmitNextTaskContext> nextTasks = new HashMap<String, SecondPcrSubmitNextTaskContext>();

    private Map<String, SecondPcrSubmitNextTaskContext> nextTasksByIds = new HashMap<String, SecondPcrSubmitNextTaskContext>();

    private Map<String, SecondPcrSubmitTaskArgs> submitTaskResults = new HashMap<String, SecondPcrSubmitTaskArgs>();

    private Map<String, SecondPcrSubmitScheduleContext> gotoNextSchedules = new HashMap<String, SecondPcrSubmitScheduleContext>();

    public Set<SecondPcrSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<SecondPcrSubmitTaskContext>(tasks.values());
    }

    public Set<SecondPcrSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<SecondPcrSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<SecondPcrSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<SecondPcrSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        SecondPcrSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
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

    public void setContextForSubmitRequest(List<SecondPcrSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }

        for (SecondPcrSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }

    public void setContextForTestingTask(TestingTask task)
    {
        SecondPcrSubmitTaskContext context = new SecondPcrSubmitTaskContext();
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive, TaskConfig nextNodeConfig)
    {
        SecondPcrSubmitScheduleContext scheduleContext = new SecondPcrSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);

        String nextNodeInputSampleId = task.getInputSample().getId();
        scheduleContext.setNextNodeConfig(nextNodeConfig);
        scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
        gotoNextSchedules.put(schedule.getId(), scheduleContext);

        SecondPcrSubmitNextTaskContext nextTaskContext = new SecondPcrSubmitNextTaskContext();
        nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId);
        nextTaskContext.setScheduleId(schedule.getId());
        nextTaskContext.setInputSampleId(nextNodeInputSampleId);
        nextTaskContext.setTestingSample(task.getInputSample());
        nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
        nextTaskContext.setTaskName(nextNodeConfig.getName());
        nextTaskContext.setSecondPcrSubmitTaskArgs(submitTaskResults.get(task.getId()));
        nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        SecondPcrSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
