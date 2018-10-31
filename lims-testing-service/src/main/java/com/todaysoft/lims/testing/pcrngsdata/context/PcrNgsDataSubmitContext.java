package com.todaysoft.lims.testing.pcrngsdata.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class PcrNgsDataSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private Map<String, PcrNgsDataSubmitTaskContext> tasks = new HashMap<String, PcrNgsDataSubmitTaskContext>();

    private Map<String, PcrNgsDataSubmitNextTaskContext> nextTasks = new HashMap<String, PcrNgsDataSubmitNextTaskContext>();

    private Map<String, PcrNgsDataSubmitNextTaskContext> nextTasksByIds = new HashMap<String, PcrNgsDataSubmitNextTaskContext>();

    private Map<String, PcrNgsDataSubmitTaskArgs> submitTaskResults = new HashMap<String, PcrNgsDataSubmitTaskArgs>();

    private Map<String, PcrNgsDataSubmitScheduleContext> gotoNextSchedules = new HashMap<String, PcrNgsDataSubmitScheduleContext>();

    private Map<String, PcrNgsDataSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, PcrNgsDataSubmitScheduleContext>();

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

    public Set<PcrNgsDataSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<PcrNgsDataSubmitTaskContext>(tasks.values());
    }

    public Set<PcrNgsDataSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<PcrNgsDataSubmitScheduleContext>(gotoErrorSchedules.values());
    }

    public Set<PcrNgsDataSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<PcrNgsDataSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<PcrNgsDataSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<PcrNgsDataSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        PcrNgsDataSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
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

    public void setContextForSubmitRequest(List<PcrNgsDataSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }

        for (PcrNgsDataSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }

    public void setContextForTestingTask(TestingTask task)
    {
        PcrNgsDataSubmitTaskContext context = new PcrNgsDataSubmitTaskContext();
        PcrNgsDataSubmitTaskArgs result = submitTaskResults.get(task.getId());

        if (null == result)
        {
            throw new IllegalStateException();
        }

        context.setResult(result.getResult());
        context.setRemark(result.getRemark());
        context.setDispose(result.getDispose());
        context.setCombineType(result.getCombineType());
        context.setMutationSource(result.getMutationSource());
        context.setPcrNgsDataSubmitTaskArgs(result);
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive)
    {
        PcrNgsDataSubmitTaskContext taskContext = tasks.get(task.getId());
        PcrNgsDataSubmitScheduleContext scheduleContext = new PcrNgsDataSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);
        if(taskContext.getResult().intValue()==0){
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId(), scheduleContext);

            PcrNgsDataSubmitNextTaskContext nextTaskContext = new PcrNgsDataSubmitNextTaskContext();
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
            scheduleContext.setMutationSource(taskContext.getMutationSource());
            scheduleContext.setTestingTask(task);
            gotoErrorSchedules.put(schedule.getId(), scheduleContext);
        }

    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        PcrNgsDataSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
