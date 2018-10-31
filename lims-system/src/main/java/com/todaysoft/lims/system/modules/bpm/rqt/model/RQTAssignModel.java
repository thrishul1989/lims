package com.todaysoft.lims.system.modules.bpm.rqt.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class RQTAssignModel
{
    private List<RQTTask> tasks;
    
    public BigDecimal getAssignDatasize()
    {
        BigDecimal datasize = BigDecimal.ZERO;
        
        if (CollectionUtils.isEmpty(tasks))
        {
            return datasize;
        }
        
        for (RQTTask task : tasks)
        {
            datasize = datasize.add(null == task.getTotalDatasize() ? BigDecimal.ZERO : task.getTotalDatasize());
        }
        
        return datasize;
    }
    
    public List<RQTTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<RQTTask> tasks)
    {
        this.tasks = tasks;
    }
}
