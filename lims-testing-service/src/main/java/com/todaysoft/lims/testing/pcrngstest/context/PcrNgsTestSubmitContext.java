package com.todaysoft.lims.testing.pcrngstest.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.utils.JsonUtils;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestSheetVariables;
import com.todaysoft.lims.testing.pcrngstest.model.PcrNgsTestSubmitTaskArgs;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;

public class PcrNgsTestSubmitContext
{
    private UserMinimalModel submiter;

    private TestingSheet sheetEntity;

    private PcrNgsTestSheetVariables firstPcrSheetVariables;

    private Map<String,TestingSample> rqtTestingSample = new HashMap<String, TestingSample>();

    private Map<String, PcrNgsTestSubmitTaskContext> tasks = new HashMap<String, PcrNgsTestSubmitTaskContext>();

    private Map<String, TestingTask> nextRqtTasks = new HashMap<String, TestingTask>();

    private Map<String,TestingTaskRunVariable> nextRqtTaskVars = new HashMap<String,TestingTaskRunVariable>();

    private Map<String, PcrNgsTestSubmitNextTaskContext> nextTasks = new HashMap<String, PcrNgsTestSubmitNextTaskContext>();

    private Map<String, PcrNgsTestSubmitNextTaskContext> nextTasksByIds = new HashMap<String, PcrNgsTestSubmitNextTaskContext>();

    private Map<String, PcrNgsTestSubmitTaskArgs> submitTaskResults = new HashMap<String, PcrNgsTestSubmitTaskArgs>();

    private Map<String, PcrNgsTestSubmitScheduleContext> gotoNextSchedules = new HashMap<String, PcrNgsTestSubmitScheduleContext>();

    private Map<String, PcrNgsTestSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, PcrNgsTestSubmitScheduleContext>();

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

    public Set<PcrNgsTestSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<PcrNgsTestSubmitTaskContext>(tasks.values());
    }

    public TestingSample getRqtTestingSample(String sheetId)
    {
        return rqtTestingSample.get(sheetId);
    }

    public Set<TestingTask> getNextRqtTask()
    {
        return new HashSet<TestingTask>(nextRqtTasks.values());
    }

    public Set<PcrNgsTestSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<PcrNgsTestSubmitNextTaskContext>(nextTasks.values());
    }

    public Set<PcrNgsTestSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<PcrNgsTestSubmitScheduleContext>(gotoNextSchedules.values());
    }

    public Set<PcrNgsTestSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<PcrNgsTestSubmitScheduleContext>(gotoErrorSchedules.values());
    }

    public String getNextTaskCreatedId(String scheduleId, String inputSampleId)
    {
        PcrNgsTestSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
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
        firstPcrSheetVariables = JsonUtils.asObject(sheet.getVariables(), PcrNgsTestSheetVariables.class);
    }

    public void setContextForSubmitRequest(List<PcrNgsTestSubmitTaskArgs> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }

        for (PcrNgsTestSubmitTaskArgs task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }

    public void setContextForTestingTask(TestingTask task)
    {
        PcrNgsTestSubmitTaskArgs result = submitTaskResults.get(task.getId());

        if (null == result)
        {
            throw new IllegalStateException();
        }

        PcrNgsTestSubmitTaskContext context = new PcrNgsTestSubmitTaskContext();
        context.setResult(result.getResult());
        context.setRemark(result.getRemark());
        context.setDispose(result.getDispose());
        context.setTestingTaskEntity(task);
        context.setPcrTaskCode(result.getPcrTaskCode());
        context.setPcrTestCode(result.getPcrTestCode());
        context.setForwardPrimerLocationTemp(result.getForwardPrimerLocationTemp());
        context.setCombineCode(result.getCombineCode());
        context.setVolume(result.getVolume());
        context.setLibraryIndex(result.getLibraryIndex());
        tasks.put(task.getId(), context);
    }

    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TestingScheduleActive scheduleActive, TaskConfig nextNodeConfig)
    {
        PcrNgsTestSubmitTaskContext taskContext = tasks.get(task.getId());

        PcrNgsTestSubmitScheduleContext scheduleContext = new PcrNgsTestSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        scheduleContext.setTestingScheduleActiveEntity(scheduleActive);

        if (taskContext.getResult().intValue()==0)
        {
            String nextNodeInputSampleId = task.getInputSample().getId();
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setNextNodeConfig(nextNodeConfig);
            scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
            gotoNextSchedules.put(schedule.getId(), scheduleContext);

            PcrNgsTestSubmitNextTaskContext nextTaskContext = new PcrNgsTestSubmitNextTaskContext();
            nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId);
            nextTaskContext.setScheduleId(schedule.getId());
            nextTaskContext.setInputSampleId(nextNodeInputSampleId);
            nextTaskContext.setTestingSample(task.getInputSample());
            nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
            nextTaskContext.setTaskName(nextNodeConfig.getName());
            nextTaskContext.setPcrTaskCode(taskContext.getPcrTaskCode());
            nextTaskContext.setPcrTestCode(taskContext.getPcrTestCode());
            nextTaskContext.setForwardPrimerLocationTemp(taskContext.getForwardPrimerLocationTemp());
            nextTaskContext.setCombineCode(taskContext.getCombineCode());
            nextTaskContext.setLibraryIndex(taskContext.getLibraryIndex());
            nextTaskContext.setVolume(taskContext.getVolume());
            nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
        }
        else
        {
            scheduleContext.setResult(taskContext.getResult());
            scheduleContext.setDispose(taskContext.getDispose());
            scheduleContext.setRemark(taskContext.getRemark());
            scheduleContext.setTestingTask(task);
            gotoErrorSchedules.put(schedule.getId(), scheduleContext);
        }
    }

    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task, TestingTaskRunVariable nextTaskVariables )
    {
        PcrNgsTestSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTask.setTestingTaskVariablesEntity(nextTaskVariables);
        nextTasksByIds.put(task.getId(), nextTask);
        if(!nextRqtTasks.containsKey(task.getId()))
        {
            nextRqtTasks.put(task.getId(),task);
        }
        if(!nextRqtTaskVars.containsKey(task.getId()))
        {
            nextRqtTaskVars.put(task.getId(),nextTaskVariables);
        }

    }


    public PcrNgsTestSubmitNextTaskContext getNextTaskConextById(String id)
    {
        return nextTasksByIds.get(id);
    }

    public TestingTaskRunVariable getNextRqtVar(String taskId){return nextRqtTaskVars.get(taskId);}

    public PcrNgsTestSheetVariables getFirstPcrSheetVariables() {
        return firstPcrSheetVariables;
    }

    public void setRqtTestingSample(String sheetId,TestingSample testingSample) {
        rqtTestingSample.put(sheetId,testingSample);
    }
}
