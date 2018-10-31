package com.todaysoft.lims.schedule.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.schedule.entity.TaskConfig;
import com.todaysoft.lims.schedule.service.ITaskConfigService;

@Service
public class TaskConfigService implements ITaskConfigService
{
    private Map<String, TaskConfig> configs;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public TaskConfig getTaskConfig(String semantic)
    {
        return getConfigs().get(semantic);
    }
    
    private Map<String, TaskConfig> getConfigs()
    {
        if (null != configs)
        {
            return configs;
        }
        
        NamedQueryer queryer = NamedQueryer.builder().hql("FROM TaskConfig").names(Collections.emptyList()).values(Collections.emptyList()).build();
        List<TaskConfig> records = baseDaoSupport.find(queryer, TaskConfig.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            configs = Collections.emptyMap();
        }
        else
        {
            configs = new HashMap<String, TaskConfig>();
            
            for (TaskConfig record : records)
            {
                configs.put(record.getSemantic(), record);
            }
        }
        
        return configs;
    }
}
