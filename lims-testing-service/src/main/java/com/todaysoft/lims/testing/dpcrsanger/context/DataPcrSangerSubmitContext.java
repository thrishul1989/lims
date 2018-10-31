package com.todaysoft.lims.testing.dpcrsanger.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSubmitTaskArgs;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class DataPcrSangerSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private Map<String, DataPcrSangerSubmitTaskContext> tasks = new HashMap<String, DataPcrSangerSubmitTaskContext>();

    private Map<String, DataPcrSangerSubmitNextTaskContext> nextTasks = new HashMap<String, DataPcrSangerSubmitNextTaskContext>();

    private Map<String, DataPcrSangerSubmitNextTaskContext> nextTasksByIds = new HashMap<String, DataPcrSangerSubmitNextTaskContext>();

    private Map<String, DataPcrSangerSubmitTaskArgs> submitTaskResults = new HashMap<String, DataPcrSangerSubmitTaskArgs>();

    private Map<String, DataPcrSangerSubmitScheduleContext> gotoNextSchedules = new HashMap<String, DataPcrSangerSubmitScheduleContext>();

    private Map<String, DataPcrSangerSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, DataPcrSangerSubmitScheduleContext>();

    private Map<String, TestingScheduleActive> abnormalCancerTasks = new HashMap<String, TestingScheduleActive>();


    public Set<DataPcrSangerSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<DataPcrSangerSubmitTaskContext>(tasks.values());
    }

    public Set<TestingScheduleActive> getAbnormalCancerTasksSet()
    {
        return new HashSet<TestingScheduleActive>(abnormalCancerTasks.values());
    }

    public Set<DataPcrSangerSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<DataPcrSangerSubmitScheduleContext>(gotoErrorSchedules.values());
    }

    public Set<DataPcrSangerSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<DataPcrSangerSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<DataPcrSangerSubmitScheduleContext> getGotoNextScheduleSets()
    {
        return new HashSet<DataPcrSangerSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public String getNextTaskCreatedId(String scheduleId, String taskId)
    {
        DataPcrSangerSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + taskId);
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

    public void setContextForSubmitRequest(List<DataPcrSangerSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }

        for (DataPcrSangerSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }

    public void setContextForTestingTask(TestingTask task)
    {
        DataPcrSangerSubmitTaskContext context = new DataPcrSangerSubmitTaskContext();
        DataPcrSangerSubmitTaskArgs result = submitTaskResults.get(task.getId());

        if (null == result)
        {
            throw new IllegalStateException();
        }

        context.setResult(result.getResult());
        context.setRemark(result.getRemark());
        context.setDispose(result.getDispose());
        context.setCombineType(result.getCombineType());
        context.setMutationSource(result.getMutationSource());
        context.setTestingTaskEntity(task);
        context.setPrimerId(result.getPrimerId());
        context.setCombineCode(result.getCombineCode());
        context.setDataPcrSangerSubmitTaskArgs(result);
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive)
    {
        DataPcrSangerSubmitTaskContext taskContext = tasks.get(task.getId());
        int resultInt = taskContext.getResult().intValue();
        DataPcrSangerSubmitTaskArgs result = submitTaskResults.get(task.getId());
        DataPcrSangerSubmitScheduleContext scheduleContext = new DataPcrSangerSubmitScheduleContext();
        String nextNodeInputSampleId = task.getInputSample().getId();
        scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);
        scheduleContext.setTestingTask(task);
        scheduleContext.setPrimerId(result.getPrimerId());
        scheduleContext.setCombineCode(result.getCombineCode());
        if(scheduleActive.getRunningStatus().intValue() == TestingScheduleActive.STATUS_CANCER)
        {
            resultInt = 3;
        }
        if(resultInt == 0){
            gotoNextSchedules.put(schedule.getId()+"_"+task.getId(), scheduleContext);
            DataPcrSangerSubmitNextTaskContext nextTaskContext = new DataPcrSangerSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId()+"_"+task.getId());
            nextTaskContext.setScheduleId(schedule.getId());
            nextTaskContext.setInputSampleId(nextNodeInputSampleId);
            nextTaskContext.setTestingSample(task.getInputSample());
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }else if(resultInt == 1){
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setRemark(taskContext.getRemark());
            scheduleContext.setCombineType(taskContext.getCombineType());
            scheduleContext.setDispose(taskContext.getDispose());
            scheduleContext.setTestingTask(task);
            gotoErrorSchedules.put(schedule.getId()+"_"+task.getId(), scheduleContext);
        }else{
            abnormalCancerTasks.put(task.getId(),scheduleActive);
        }

    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        DataPcrSangerSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }

    public void setDataPcrSangerSubmitScheduleContextFlag(String temporaryId, Integer flag)
    {
        DataPcrSangerSubmitScheduleContext nextTaskF = gotoNextSchedules.get(temporaryId);
        nextTaskF.setFlag(flag);
    }

    public Map<String, DataPcrSangerSubmitNextTaskContext> getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(Map<String, DataPcrSangerSubmitNextTaskContext> nextTasks) {
        this.nextTasks = nextTasks;
    }

    public Map<String, DataPcrSangerSubmitScheduleContext> getGotoNextSchedules() {
        return gotoNextSchedules;
    }

    public void setGotoNextSchedules(Map<String, DataPcrSangerSubmitScheduleContext> gotoNextSchedules) {
        this.gotoNextSchedules = gotoNextSchedules;
    }

    public Map<String, TestingScheduleActive> getAbnormalCancerTasks() {
        return abnormalCancerTasks;
    }

    public void setAbnormalCancerTasks(Map<String, TestingScheduleActive> abnormalCancerTasks) {
        this.abnormalCancerTasks = abnormalCancerTasks;
    }
}
