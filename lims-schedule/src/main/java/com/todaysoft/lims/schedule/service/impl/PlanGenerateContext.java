package com.todaysoft.lims.schedule.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.todaysoft.lims.schedule.model.PlanTask;

public class PlanGenerateContext
{
    private Map<String, List<PlanTask>> semanticMapping = new HashMap<String, List<PlanTask>>();
    
    private Map<String, List<PlanTask>> dependencySemanticMapping = new HashMap<String, List<PlanTask>>();
    
    public List<PlanTask> getTasks(String semantic)
    {
        List<PlanTask> tasks = semanticMapping.get(semantic);
        return null == tasks ? Collections.emptyList() : tasks;
    }
    
    public List<PlanTask> getDependencyTasks(String semantic)
    {
        List<PlanTask> tasks = dependencySemanticMapping.get(semantic);
        return null == tasks ? Collections.emptyList() : tasks;
    }
    
    public void setTask(String semantic, PlanTask task)
    {
        List<PlanTask> tasks = semanticMapping.get(semantic);
        
        if (null == tasks)
        {
            tasks = new ArrayList<PlanTask>();
            semanticMapping.put(semantic, tasks);
        }
        
        tasks.add(task);
    }
    
    public void setDependencyTask(String semantic, PlanTask task)
    {
        List<PlanTask> dependencyTasks = dependencySemanticMapping.get(semantic);
        
        if (null == dependencyTasks)
        {
            dependencyTasks = new ArrayList<PlanTask>();
            dependencySemanticMapping.put(semantic, dependencyTasks);
        }
        
        dependencyTasks.add(task);
    }
}
