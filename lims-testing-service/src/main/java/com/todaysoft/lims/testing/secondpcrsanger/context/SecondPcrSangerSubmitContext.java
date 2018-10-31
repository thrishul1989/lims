package com.todaysoft.lims.testing.secondpcrsanger.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.firstpcrsanger.context.FirstPcrSangerSubmitScheduleContext;
import com.todaysoft.lims.testing.secondpcrsanger.model.SecondPcrSangerSubmitTaskArgs;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class SecondPcrSangerSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private Map<String, SecondPcrSangerSubmitTaskContext> tasks = new HashMap<String, SecondPcrSangerSubmitTaskContext>();

    private Map<String, SecondPcrSangerSubmitNextTaskContext> nextTasks = new HashMap<String, SecondPcrSangerSubmitNextTaskContext>();

    private Map<String, SecondPcrSangerSubmitNextTaskContext> nextTasksByIds = new HashMap<String, SecondPcrSangerSubmitNextTaskContext>();

    private Map<String, SecondPcrSangerSubmitTaskArgs> submitTaskResults = new HashMap<String, SecondPcrSangerSubmitTaskArgs>();

    private Map<String, SecondPcrSangerSubmitScheduleContext> gotoNextSchedules = new HashMap<String, SecondPcrSangerSubmitScheduleContext>();

    private Map<String, TestingScheduleActive> abnormalCancerTasks = new HashMap<String, TestingScheduleActive>();

    public Set<TestingScheduleActive> getAbnormalCancerTasksSet()
    {
        return new HashSet<TestingScheduleActive>(abnormalCancerTasks.values());
    }

    public Set<SecondPcrSangerSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<SecondPcrSangerSubmitTaskContext>(tasks.values());
    }

    public Set<SecondPcrSangerSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<SecondPcrSangerSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<SecondPcrSangerSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<SecondPcrSangerSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public TestingTask getNextTaskCreatedId(String scheduleId, String taskId)
    {
        SecondPcrSangerSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + taskId);
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
    }

    public void setContextForSubmitRequest(List<SecondPcrSangerSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }

        for (SecondPcrSangerSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }

    public void setContextForTestingTask(TestingTask task)
    {
        SecondPcrSangerSubmitTaskContext context = new SecondPcrSangerSubmitTaskContext();
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive, TaskConfig nextNodeConfig)
    {
        SecondPcrSangerSubmitScheduleContext scheduleContext = new SecondPcrSangerSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingTask(task);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);

        if(scheduleActive.getRunningStatus().intValue() == TestingScheduleActive.STATUS_CANCER)
        {
            abnormalCancerTasks.put(task.getId(),scheduleActive);
        }else{
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setNextNodeConfig(nextNodeConfig);
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId()+"_"+task.getId(), scheduleContext);

            SecondPcrSangerSubmitNextTaskContext nextTaskContext = new SecondPcrSangerSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId() + "_" + task.getId());
            nextTaskContext.setScheduleId(schedule.getId());
            nextTaskContext.setInputSampleId(nextNodeInputSampleId);
            nextTaskContext.setTestingSample(task.getInputSample());
            nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
            nextTaskContext.setTaskName(nextNodeConfig.getName());
            nextTaskContext.setSecondPcrSubmitTaskArgs(submitTaskResults.get(task.getId()));
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }

    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        SecondPcrSangerSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }

    public Map<String, TestingScheduleActive> getAbnormalCancerTasks() {
        return abnormalCancerTasks;
    }

    public void setAbnormalCancerTasks(Map<String, TestingScheduleActive> abnormalCancerTasks) {
        this.abnormalCancerTasks = abnormalCancerTasks;
    }
}
