package com.todaysoft.lims.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.schedule.model.Plan;
import com.todaysoft.lims.schedule.model.PlanTask;

public class PlanGenerator
{
    private String startSemantic;
    
    private List<PlanTask> tasks;
    
    public PlanGenerator(String startSemantic, List<PlanTask> tasks)
    {
        this.tasks = tasks;
        this.startSemantic = startSemantic;
    }
    
    public Plan generate()
    {
        Plan plan = new Plan();
        plan.setPlannedTasks(new ArrayList<PlanTask>());
        plan.setIsolatedTasks(new ArrayList<PlanTask>());
        
        if (CollectionUtils.isEmpty(tasks))
        {
            return plan;
        }
        
        PlanGenerateContext context = generateContext();
        plan(startSemantic, null, context, plan);
        
        List<PlanTask> temp = new ArrayList<PlanTask>(tasks);
        
        if (!CollectionUtils.isEmpty(plan.getPlannedTasks()))
        {
            for (PlanTask task : plan.getPlannedTasks())
            {
                temp.remove(task);
            }
        }
        
        plan.getIsolatedTasks().addAll(temp);
        return plan;
    }
    
    private void plan(String semantic, PlanTask task, PlanGenerateContext context, Plan plan)
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
            if (isStartTask(dependencyTask))
            {
                dependencyTask.setStartTask(true);
                plan.getPlannedTasks().add(dependencyTask);
                plan(dependencyTask.getSemantic(), dependencyTask, context, plan);
            }
            else
            {
                if (CollectionUtils.isEmpty(dependencyTask.getDependencyTasks()))
                {
                    dependencyTask.setDependencyTasks(new ArrayList<PlanTask>());
                }
                
                if (null != task)
                {
                    dependencyTask.getDependencyTasks().add(task);
                }
                
                if (isPlannedTask(dependencyTask, context))
                {
                    plan.getPlannedTasks().add(dependencyTask);
                    plan(dependencyTask.getSemantic(), dependencyTask, context, plan);
                }
            }
        }
    }
    
    private boolean isStartTask(PlanTask task)
    {
        if (CollectionUtils.isEmpty(task.getDependencies()) || task.getDependencies().size() != 1)
        {
            return false;
        }
        
        String dependency = task.getDependencies().get(0);
        return startSemantic.equals(dependency);
    }
    
    private boolean isPlannedTask(PlanTask task, PlanGenerateContext context)
    {
        if (task.isStartTask())
        {
            return true;
        }
        
        // 没有依赖的任务节点，无法安排计划
        if (CollectionUtils.isEmpty(task.getDependencies()))
        {
            return false;
        }
        
        int dependencyCount = 0;
        
        for (String dependencySemantic : task.getDependencies())
        {
            dependencyCount += context.getTasks(dependencySemantic).size();
        }
        
        int contactedCount = null == task.getDependencyTasks() ? 0 : task.getDependencyTasks().size();
        return dependencyCount == contactedCount;
    }
    
    private PlanGenerateContext generateContext()
    {
        PlanGenerateContext context = new PlanGenerateContext();
        
        List<String> dependencies;
        
        for (PlanTask task : tasks)
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
}
