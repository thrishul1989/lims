package com.todaysoft.lims.testing.dnaqc.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskSemantic;
import com.todaysoft.lims.testing.base.service.adapter.smm.UserMinimalModel;
import com.todaysoft.lims.testing.dnaqc.model.DNAQcSubmitTaskModel;
import com.todaysoft.lims.utils.Collections3;

public class DNAQcSubmitContext
{
    private UserMinimalModel submiter;
    
    private TestingSheet sheetEntity;
    
    private Map<String, DNAQcSubmitTaskContext> tasks = new HashMap<String, DNAQcSubmitTaskContext>();
    
    private Map<String, DNAQcSubmitNextTaskContext> nextTasks = new HashMap<String, DNAQcSubmitNextTaskContext>();
    
    private Map<String, DNAQcSubmitNextTaskContext> nextTasksByIds = new HashMap<String, DNAQcSubmitNextTaskContext>();
    
    private Map<String, DNAQcSubmitSolveTaskContext> solveTasks = new HashMap<String, DNAQcSubmitSolveTaskContext>();
    
    private Map<String, DNAQcSubmitTaskModel> submitTaskResults = new HashMap<String, DNAQcSubmitTaskModel>();
    
    private Map<String, DNAQcSubmitScheduleContext> gotoNextSchedules = new HashMap<String, DNAQcSubmitScheduleContext>();
    
    private Map<String, DNAQcSubmitScheduleContext> gotoErrorSchedules = new HashMap<String, DNAQcSubmitScheduleContext>();
    
    private Map<String, DNAQcSubmitScheduleContext> gotoSolveSchedules = new HashMap<String, DNAQcSubmitScheduleContext>();
    
    private Map<String, DNAQcSubmitScheduleContext> waitConcurrentNodeShedules = new HashMap<String, DNAQcSubmitScheduleContext>();
    
    public Set<DNAQcSubmitTaskContext> getRelatedTasks()
    {
        return new HashSet<DNAQcSubmitTaskContext>(tasks.values());
    }
    
    public Set<DNAQcSubmitNextTaskContext> getNextNodeTasks()
    {
        return new HashSet<DNAQcSubmitNextTaskContext>(nextTasks.values());
    }
    
    public List<TestingTask> getNextTasks()
    {
        List<TestingTask> records = Lists.newArrayList();
        if (Collections3.isEmpty(nextTasksByIds.values()))
        {
            return records;
        }
        List<DNAQcSubmitNextTaskContext> lists = new ArrayList<DNAQcSubmitNextTaskContext>(nextTasksByIds.values());
        for (DNAQcSubmitNextTaskContext dnaContent : lists)
        {
            records.add(dnaContent.getTestingTaskEntity());
        }
        return records;
    }
    
    public Set<DNAQcSubmitSolveTaskContext> getSolveUnqualifiedTasks()
    {
        return new HashSet<DNAQcSubmitSolveTaskContext>(solveTasks.values());
    }
    
    public Set<DNAQcSubmitScheduleContext> getGotoNextSchedules()
    {
        return new HashSet<DNAQcSubmitScheduleContext>(gotoNextSchedules.values());
    }
    
    public Set<DNAQcSubmitScheduleContext> getWaitConcurrentNodeShedules()
    {
        return new HashSet<DNAQcSubmitScheduleContext>(waitConcurrentNodeShedules.values());
    }
    
    public Set<DNAQcSubmitScheduleContext> getGotoErrorSchedules()
    {
        return new HashSet<DNAQcSubmitScheduleContext>(gotoErrorSchedules.values());
    }
    
    public TestingTask getNextTask(String scheduleId, String inputSampleId)
    {
        DNAQcSubmitNextTaskContext nextTaskContext = nextTasks.get(scheduleId + "_" + inputSampleId);
        return !StringUtils.isEmpty(nextTaskContext) ? nextTaskContext.getTestingTaskEntity() : null;
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
    
    public void setContextForSubmitRequest(List<DNAQcSubmitTaskModel> tasks)
    {
        if (CollectionUtils.isEmpty(tasks))
        {
            return;
        }
        
        for (DNAQcSubmitTaskModel task : tasks)
        {
            submitTaskResults.put(task.getId(), task);
        }
    }
    
    public void setContextForTestingTask(TestingTask task)
    {
        DNAQcSubmitTaskModel result = submitTaskResults.get(task.getId());
        
        if (null == result)
        {
            throw new IllegalStateException();
        }
        
        DNAQcSubmitTaskContext context = new DNAQcSubmitTaskContext();
        context.setConcn(result.getConcn());
        context.setVolume(result.getVolume());
        context.setRatio2628(result.getRatio2628());
        context.setRatio2623(result.getRatio2623());
        context.setQualityLevel(result.getQualityLevel());
        context.setQualified(result.isQualified());
        context.setUnqualifiedRemark(result.getUnqualifiedRemark());
        context.setUnqualifiedStrategy(result.getUnqualifiedStrategy());
        context.setTestingTaskEntity(task);
        tasks.put(task.getId(), context);
    }
    
    public void setContextForTestingTaskRelatedSchedule(TestingTask task, TestingSchedule schedule, TaskConfig nextNodeConfig, String scheduleConcurrentNode, String productId, Primer primer)
    {
        DNAQcSubmitTaskContext taskContext = tasks.get(task.getId());
        
        DNAQcSubmitScheduleContext scheduleContext = new DNAQcSubmitScheduleContext();
        scheduleContext.setId(schedule.getId());
        scheduleContext.setTestingScheduleEntity(schedule);
        
        if (taskContext.isQualified())
        {
            // 存在并行未完全的任务节点，DNA质检通过后不能进入下一个检测流程
            if (null != scheduleConcurrentNode)
            {
                scheduleContext.setNextNodeInputSampleId(task.getInputSample().getId());
                waitConcurrentNodeShedules.put(schedule.getId(), scheduleContext);
            }
            else
            {
                String nextNodeInputSampleId = task.getInputSample().getId();
                scheduleContext.setQcQualified(true);
                scheduleContext.setNextNodeConfig(nextNodeConfig);
                scheduleContext.setNextNodeInputSampleId(nextNodeInputSampleId);
                gotoNextSchedules.put(schedule.getId(), scheduleContext);
                
                if (!StringUtils.isEmpty(nextNodeConfig))
                {
                    DNAQcSubmitNextTaskContext nextTaskContext = new DNAQcSubmitNextTaskContext();
                    if (TaskSemantic.SANGER_PCR_ONE.equals(nextNodeConfig.getSemantic()))
                    {
                        nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId + "_" + productId + "_" + primer.getForwardPrimerName());
                        nextTaskContext.setPrimerVar(primer);
                    }
                    else
                    {
                        nextTaskContext.setTemporaryId(schedule.getId() + "_" + nextNodeInputSampleId);
                    }
                    
                    nextTaskContext.setInputSampleId(nextNodeInputSampleId);
                    nextTaskContext.setTaskSemantic(nextNodeConfig.getSemantic());
                    nextTaskContext.setTaskName(nextNodeConfig.getName());
                    nextTasks.put(nextTaskContext.getTemporaryId(), nextTaskContext);
                    
                }
                
            }
        }
        else
        {
            scheduleContext.setQcQualified(false);
            scheduleContext.setQcUnqualifiedRemark(taskContext.getUnqualifiedRemark());
            scheduleContext.setQcUnqualifiedStrategy(taskContext.getUnqualifiedStrategy());
            
            if ("1".equals(taskContext.getUnqualifiedStrategy()))
            {
                gotoErrorSchedules.put(schedule.getId(), scheduleContext);
            }
            else
            {
                DNAQcSubmitSolveTaskContext solveTaskContext = solveTasks.get(task.getId());
                
                if (null == solveTaskContext)
                {
                    solveTaskContext = new DNAQcSubmitSolveTaskContext();
                    solveTaskContext.setUnqualifiedTask(task);
                    solveTaskContext.setStrategy(taskContext.getUnqualifiedStrategy());
                    solveTaskContext.setRelatedSchedules(new HashMap<String, DNAQcSubmitScheduleContext>());
                    solveTasks.put(task.getId(), solveTaskContext);
                }
                
                if (!solveTaskContext.getRelatedSchedules().containsKey(schedule.getId()))
                {
                    solveTaskContext.getRelatedSchedules().put(schedule.getId(), scheduleContext);
                }
                
                gotoSolveSchedules.put(schedule.getId(), scheduleContext);
            }
        }
    }
    
    public void setContextForCreateNextNodeTask(String temporaryId, TestingTask task)
    {
        DNAQcSubmitNextTaskContext nextTask = nextTasks.get(temporaryId);
        nextTask.setTestingTaskEntity(task);
        nextTasksByIds.put(task.getId(), nextTask);
    }
}
