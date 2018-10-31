package com.todaysoft.lims.testing.mlpatest.context;

import java.util.*;

import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.mlpatest.model.MlpaTestSheetVariables;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class MlpaTestSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private Map<String, MlpaTestSubmitTaskContext> tasks = new HashMap<String, MlpaTestSubmitTaskContext>();

    private Map<String, MlpaTestSubmitNextTaskContext> nextTasks = new HashMap<String, MlpaTestSubmitNextTaskContext>();

    private Map<String, MlpaTestSubmitNextTaskContext> nextTasksByIds = new HashMap<String, MlpaTestSubmitNextTaskContext>();

    private Map<String, MlpaTestSubmitScheduleContext> gotoNextSchedules = new HashMap<String, MlpaTestSubmitScheduleContext>();

    public Set<MlpaTestSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<MlpaTestSubmitTaskContext>(tasks.values());
    }

    public Set<MlpaTestSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<MlpaTestSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<MlpaTestSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<MlpaTestSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        MlpaTestSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
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
        MlpaTestSubmitTaskContext context = new MlpaTestSubmitTaskContext();
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive)
    {
        MlpaTestSubmitScheduleContext scheduleContext = new MlpaTestSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);
        gotoNextSchedules.put(schedule.getId(), scheduleContext);
    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task, TestingTaskRunVariable nextTaskVariables )
    {
        MlpaTestSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTask.setTestingTaskVariablesEntity(nextTaskVariables);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
