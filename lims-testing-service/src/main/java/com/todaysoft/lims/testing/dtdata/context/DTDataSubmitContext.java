package com.todaysoft.lims.testing.dtdata.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.dtdata.model.DTDataSubmitTaskArgs;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class DTDataSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private Map<String, DTDataSubmitTaskContext> tasks = new HashMap<String, DTDataSubmitTaskContext>();

    private Map<String, DTDataSubmitNextTaskContext> nextTasks = new HashMap<String, DTDataSubmitNextTaskContext>();

    private Map<String, DTDataSubmitNextTaskContext> nextTasksByIds = new HashMap<String, DTDataSubmitNextTaskContext>();

    private Map<String, DTDataSubmitTaskArgs> submitTaskResults = new HashMap<String, DTDataSubmitTaskArgs>();

    private Map<String, DTDataSubmitScheduleContext> gotoNextSchedules = new HashMap<String, DTDataSubmitScheduleContext>();

    private Map<String, DTDataSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, DTDataSubmitScheduleContext>();

    public Set<DTDataSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<DTDataSubmitTaskContext>(tasks.values());
    }

    public Set<DTDataSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<DTDataSubmitScheduleContext>(gotoErrorSchedules.values());
    }

    public Set<DTDataSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<DTDataSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<DTDataSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<DTDataSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        DTDataSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
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

    public void setContextForSubmitRequest(List<DTDataSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }

        for (DTDataSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }

    public void setContextForTestingTask(TestingTask task)
    {
        DTDataSubmitTaskContext context = new DTDataSubmitTaskContext();
        DTDataSubmitTaskArgs result = submitTaskResults.get(task.getId());

        if (null == result)
        {
            throw new IllegalStateException();
        }

        context.setQualified(result.isQualified());
        context.setUnqualifiedRemark(result.getUnqualifiedRemark());
        context.setUnqualifiedStrategy(result.getUnqualifiedStrategy());
        context.setTestingTaskEntity(task);
        context.setSuccessArgs(result.getSuccessArgs());
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive)
    {
        DTDataSubmitTaskContext taskContext = tasks.get(task.getId());
        DTDataSubmitScheduleContext scheduleContext = new DTDataSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);
        if(taskContext.isQualified()){
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId(), scheduleContext);

            DTDataSubmitNextTaskContext nextTaskContext = new DTDataSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId);
            nextTaskContext.setScheduleId(schedule.getId());
            nextTaskContext.setInputSampleId(nextNodeInputSampleId);
            nextTaskContext.setTestingSample(task.getInputSample());
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }else{
            scheduleContext.setQualified(taskContext.isQualified());
            scheduleContext.setUnqualifiedStrategy(taskContext.getUnqualifiedStrategy());
            scheduleContext.setUnqualifiedRemark(taskContext.getUnqualifiedRemark());
            scheduleContext.setTestingTask(task);
            gotoErrorSchedules.put(schedule.getId(), scheduleContext);
        }

    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        DTDataSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
