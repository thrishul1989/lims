package com.todaysoft.lims.schedule.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.todaysoft.lims.schedule.entity.ScheduleGlobalConfig;

public class PlanTask
{
    private String id;
    
    private String parentId;
    
    private String orderId;
    
    private String semantic;
    
    private String name;
    
    private List<String> dependencies;
    
    private Integer duration;
    
    private boolean actived;
    
    private boolean isStartTask;
    
    private List<PlanTask> dependencyTasks;
    
    private Date plannedStartDate;
    
    private Date plannedFinishDate;
    
    private Date actualStartDate;
    
    private boolean grouped;
    
    public static PlanTask fromScheduleGlobalConfig(ScheduleGlobalConfig config)
    {
        PlanTask task = new PlanTask();
        task.setSemantic(config.getEventKey());
        task.setName(config.getEventName());
        task.setDuration(config.getThreshold());
        
        if (StringUtils.isEmpty(config.getDependencies()))
        {
            task.setDependencies(Collections.emptyList());
        }
        else
        {
            task.setDependencies(Arrays.asList(config.getDependencies().split(",")));
        }
        
        task.setGrouped(false);
        return task;
    }
    
    public static PlanTask fromProductScheduleGlobalConfig(String productId, ScheduleGlobalConfig config)
    {
        TestingPlanTask task = new TestingPlanTask();
        task.setSemantic(config.getEventKey() + "#" + productId);
        task.setName(config.getEventName());
        task.setDuration(config.getThreshold());
        task.setProductId(productId);
        
        if (StringUtils.isEmpty(config.getDependencies()))
        {
            task.setDependencies(Collections.emptyList());
        }
        else
        {
            List<String> dependencies = Arrays.asList(config.getDependencies().split(","));
            List<String> productDependencies = new ArrayList<String>();
            
            for (String dependency : dependencies)
            {
                productDependencies.add(dependency + "#" + productId);
            }
            
            task.setDependencies(productDependencies);
        }
        
        task.setGrouped(false);
        return task;
    }
    
    public static PlanTask fromOrderProductTestings(OrderProductTestings testings)
    {
        TestingPlanTask task = new TestingPlanTask();
        task.setSemantic("GROUP_PRODUCT#" + testings.getProductId());
        task.setName(testings.getProductName());
        task.setDuration(testings.getDuration());
        task.setProductId(testings.getProductId());
        task.setDependencies(Arrays.asList("SAMPLE_STORAGE", "PAYMENT_CONFIRM"));
        task.setGrouped(true);
        return task;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public List<String> getDependencies()
    {
        return dependencies;
    }
    
    public void setDependencies(List<String> dependencies)
    {
        this.dependencies = dependencies;
    }
    
    public Integer getDuration()
    {
        return duration;
    }
    
    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }
    
    public boolean isActived()
    {
        return actived;
    }
    
    public void setActived(boolean actived)
    {
        this.actived = actived;
    }
    
    public boolean isStartTask()
    {
        return isStartTask;
    }
    
    public void setStartTask(boolean isStartTask)
    {
        this.isStartTask = isStartTask;
    }
    
    public List<PlanTask> getDependencyTasks()
    {
        return dependencyTasks;
    }
    
    public void setDependencyTasks(List<PlanTask> dependencyTasks)
    {
        this.dependencyTasks = dependencyTasks;
    }
    
    public Date getPlannedStartDate()
    {
        return plannedStartDate;
    }
    
    public void setPlannedStartDate(Date plannedStartDate)
    {
        this.plannedStartDate = plannedStartDate;
    }
    
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    public Date getActualStartDate()
    {
        return actualStartDate;
    }
    
    public void setActualStartDate(Date actualStartDate)
    {
        this.actualStartDate = actualStartDate;
    }
    
    public boolean isGrouped()
    {
        return grouped;
    }
    
    public void setGrouped(boolean grouped)
    {
        this.grouped = grouped;
    }
}
