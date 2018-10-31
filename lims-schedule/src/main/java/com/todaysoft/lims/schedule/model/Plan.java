package com.todaysoft.lims.schedule.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.schedule.service.impl.PlanGenerateContext;

public class Plan
{
    private static final String PLAN_START = "ORDER_SUBMIT";
    
    private List<PlanTask> plannedTasks;
    
    private List<PlanTask> isolatedTasks;
    
    public List<PlanTask> getTasks()
    {
        List<PlanTask> tasks = new ArrayList<PlanTask>();
        
        if (!CollectionUtils.isEmpty(plannedTasks))
        {
            tasks.addAll(plannedTasks);
        }
        
        if (!CollectionUtils.isEmpty(isolatedTasks))
        {
            tasks.addAll(isolatedTasks);
        }
        
        return tasks;
    }
    
    public void planDate(Date startDate)
    {
        if (CollectionUtils.isEmpty(plannedTasks))
        {
            return;
        }
        
        startDate = DateUtils.truncate(startDate, Calendar.DATE);
        startDate = DateUtils.addDays(startDate, 1);
        
        PlanGenerateContext context = generateContext();
        plan(PLAN_START, context, startDate);
    }
    
    private void plan(String semantic, PlanGenerateContext context, Date startDate)
    {
        List<PlanTask> dependencyTasks = context.getDependencyTasks(semantic);
        
        // 没有被依赖的任务，直接返回
        if (CollectionUtils.isEmpty(dependencyTasks))
        {
            return;
        }
        
        // 处理被依赖的任务
        for (PlanTask dependencyTask : dependencyTasks)
        {
            if (dependencyTask.isStartTask())
            {
                dependencyTask.setPlannedStartDate(startDate);
                dependencyTask.setPlannedFinishDate(DateUtils.addDays(dependencyTask.getPlannedStartDate(), dependencyTask.getDuration() - 1));
                plan(dependencyTask.getSemantic(), context, startDate);
            }
            else
            {
                Date dependencyTasksFinishDate = getDependencyTasksFinishDate(dependencyTask);
                
                if (null != dependencyTasksFinishDate)
                {
                    dependencyTask.setPlannedStartDate(DateUtils.addDays(dependencyTasksFinishDate, 1));
                    
                    if (dependencyTask.getDuration() > 0)
                    {
                        dependencyTask.setPlannedFinishDate(DateUtils.addDays(dependencyTask.getPlannedStartDate(), dependencyTask.getDuration() - 1));
                        plan(dependencyTask.getSemantic(), context, startDate);
                    }
                }
            }
        }
    }
    
    private Date getDependencyTasksFinishDate(PlanTask task)
    {
        List<PlanTask> dependencyTasks = task.getDependencyTasks();
        
        if (CollectionUtils.isEmpty(dependencyTasks))
        {
            return null;
        }
        
        Date finishDate = null;
        
        for (PlanTask dependencyTask : dependencyTasks)
        {
            if (null == dependencyTask.getPlannedFinishDate())
            {
                return null;
            }
            
            if (null == finishDate || finishDate.before(dependencyTask.getPlannedFinishDate()))
            {
                finishDate = dependencyTask.getPlannedFinishDate();
            }
        }
        
        return finishDate;
    }
    
    private PlanGenerateContext generateContext()
    {
        PlanGenerateContext context = new PlanGenerateContext();
        
        List<String> dependencies;
        
        for (PlanTask task : plannedTasks)
        {
            context.setTask(task.getSemantic(), task);
            
            dependencies = task.getDependencies();
            
            if (CollectionUtils.isEmpty(dependencies))
            {
                continue;
            }
            
            for (String dependency : dependencies)
            {
                context.setDependencyTask(dependency, task);
            }
        }
        
        return context;
    }
    
    public List<PlanTask> getPlannedTasks()
    {
        return plannedTasks;
    }
    
    public void setPlannedTasks(List<PlanTask> plannedTasks)
    {
        this.plannedTasks = plannedTasks;
    }
    
    public List<PlanTask> getIsolatedTasks()
    {
        return isolatedTasks;
    }
    
    public void setIsolatedTasks(List<PlanTask> isolatedTasks)
    {
        this.isolatedTasks = isolatedTasks;
    }
}
