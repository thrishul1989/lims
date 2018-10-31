package com.todaysoft.lims.testing.dpcr.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.dpcr.model.DataPcrSubmitTaskArgs;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class DataPcrSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private Map<String, DataPcrSubmitTaskContext> tasks = new HashMap<String, DataPcrSubmitTaskContext>();

    private Map<String, DataPcrSubmitNextTaskContext> nextTasks = new HashMap<String, DataPcrSubmitNextTaskContext>();

    private Map<String, DataPcrSubmitNextTaskContext> nextTasksByIds = new HashMap<String, DataPcrSubmitNextTaskContext>();

    private Map<String, DataPcrSubmitTaskArgs> submitTaskResults = new HashMap<String, DataPcrSubmitTaskArgs>();

    private Map<String, DataPcrSubmitScheduleContext> gotoNextSchedules = new HashMap<String, DataPcrSubmitScheduleContext>();

    private Map<String, DataPcrSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, DataPcrSubmitScheduleContext>();

    private Map<String, TestingTask> primerTasks = new HashMap<String, TestingTask>();

    public Map<String, TestingTask> getPrimerTasks() {
        return primerTasks;
    }

    public void setPrimerTasks(Map<String, TestingTask> primerTasks) {
        this.primerTasks = primerTasks;
    }

    public void setContextForCreateSangerPrimerPrepareTask(TestingTechnicalAnalyRecord record, TestingTask task)
    {
        String key = record.getChromosome() + "_" + record.getBeginLocus() + "_" + record.getVerifyMethod();
        primerTasks.put(key, task);
    }

    public TestingTask getPrimerTask(TestingTechnicalAnalyRecord record)
    {
        String key = record.getChromosome() + "_" + record.getBeginLocus() + "_" + record.getVerifyMethod();
        return primerTasks.get(key);
    }

    public Set<DataPcrSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<DataPcrSubmitTaskContext>(tasks.values());
    }

    public Set<DataPcrSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<DataPcrSubmitScheduleContext>(gotoErrorSchedules.values());
    }

    public Set<DataPcrSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<DataPcrSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<DataPcrSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<DataPcrSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        DataPcrSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
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

    public void setContextForSubmitRequest(List<DataPcrSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }

        for (DataPcrSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }

    public void setContextForTestingTask(TestingTask task)
    {
        DataPcrSubmitTaskContext context = new DataPcrSubmitTaskContext();
        DataPcrSubmitTaskArgs result = submitTaskResults.get(task.getId());

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
        context.setDataPcrSubmitTaskArgs(result);
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive)
    {
        DataPcrSubmitTaskContext taskContext = tasks.get(task.getId());
        DataPcrSubmitScheduleContext scheduleContext = new DataPcrSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);
        if(taskContext.getResult().intValue()==0){
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId(), scheduleContext);

            DataPcrSubmitNextTaskContext nextTaskContext = new DataPcrSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId);
            nextTaskContext.setScheduleId(schedule.getId());
            nextTaskContext.setInputSampleId(nextNodeInputSampleId);
            nextTaskContext.setTestingSample(task.getInputSample());
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }else{
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setRemark(taskContext.getRemark());
            scheduleContext.setCombineType(taskContext.getCombineType());
            scheduleContext.setDispose(taskContext.getDispose());
            scheduleContext.setTestingTask(task);
            gotoErrorSchedules.put(schedule.getId(), scheduleContext);
        }

    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        DataPcrSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
