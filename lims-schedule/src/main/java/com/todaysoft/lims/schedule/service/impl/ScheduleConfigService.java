package com.todaysoft.lims.schedule.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.entity.OrderPlanTaskDependency;
import com.todaysoft.lims.schedule.entity.ScheduleGlobalConfig;
import com.todaysoft.lims.schedule.entity.ScheduleTestingNodeConfig;
import com.todaysoft.lims.schedule.service.IScheduleConfigService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class ScheduleConfigService implements IScheduleConfigService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public List<ScheduleGlobalConfig> getOrderLevelGlobalConfigs()
    {
        NamedQueryer queryer = NamedQueryer.builder()
            .hql("FROM ScheduleGlobalConfig c WHERE c.disabled = false AND c.eventLevel = :orderLevel")
            .names(Collections.singletonList("orderLevel"))
            .values(Collections.singletonList(ScheduleGlobalConfig.LEVEL_ORDER))
            .build();
        List<ScheduleGlobalConfig> configs = baseDaoSupport.find(queryer, ScheduleGlobalConfig.class);
        return configs;
    }
    
    @Override
    public List<ScheduleGlobalConfig> getProductLevelGlobalConfigs()
    {
        NamedQueryer queryer = NamedQueryer.builder()
            .hql("FROM ScheduleGlobalConfig c WHERE c.disabled = false AND c.eventLevel = :productLevel")
            .names(Collections.singletonList("productLevel"))
            .values(Collections.singletonList(ScheduleGlobalConfig.LEVEL_PRODUCT))
            .build();
        List<ScheduleGlobalConfig> configs = baseDaoSupport.find(queryer, ScheduleGlobalConfig.class);
        return configs;
    }
    
    @Override
    public Map<String, ScheduleTestingNodeConfig> getScheduleTestingNodeConfig(String configId)
    {
        NamedQueryer queryer = NamedQueryer.builder()
            .hql("FROM ScheduleTestingNodeConfig c WHERE c.configId = :configId")
            .names(Collections.singletonList("configId"))
            .values(Collections.singletonList(configId))
            .build();
        List<ScheduleTestingNodeConfig> records = baseDaoSupport.find(queryer, ScheduleTestingNodeConfig.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyMap();
        }
        
        Map<String, ScheduleTestingNodeConfig> configs = new HashMap<String, ScheduleTestingNodeConfig>();
        
        for (ScheduleTestingNodeConfig record : records)
        {
            configs.put(record.getSemantic(), record);
        }
        
        return configs;
    }
    
    @Override
    public ScheduleGlobalConfig getGlobalConfig(String eventKey)
    {
        NamedQueryer queryer = NamedQueryer.builder()
            .hql("FROM ScheduleGlobalConfig c WHERE c.disabled = false AND c.eventKey = :eventKey")
            .names(Collections.singletonList("eventKey"))
            .values(Collections.singletonList(eventKey))
            .build();
        List<ScheduleGlobalConfig> configs = baseDaoSupport.find(queryer, ScheduleGlobalConfig.class);
        return Collections3.getFirst(configs);
    }
    
    @Override
    public OrderPlanTask getPlanTask(String orderId, String semantic, String testingId)
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM OrderPlanTask t WHERE 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        if (StringUtils.isNotEmpty(orderId))
        {
            hql.append(" AND t.orderId = :orderId");
            paramNames.add("orderId");
            parameters.add(orderId);
        }
        if (StringUtils.isNotEmpty(semantic))
        {
            hql.append(" AND t.taskSemantic = :semantic ");
            paramNames.add("semantic");
            parameters.add(semantic);
        }
        if (StringUtils.isNotEmpty(testingId))
        {
            hql.append(" AND t.testingId = :testingId ");
            paramNames.add("testingId");
            parameters.add(testingId);
        }
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        return Collections3.getFirst(records);
    }
    
    @Override
    public OrderPlanTask getPlanTaskById(String taskId)
    {
        return baseDaoSupport.get(OrderPlanTask.class, taskId);
    }
    
    @Override
    public List<OrderPlanTaskDependency> getDependencyByDependencyId(String dependencyTaskId)
    {
        String hql = "FROM OrderPlanTaskDependency t WHERE t.dependencyTaskId = :dependencyTaskId ";
        List<OrderPlanTaskDependency> orderPlanTaskDependencyList =
            baseDaoSupport.findByNamedParam(OrderPlanTaskDependency.class, hql, new String[] {"dependencyTaskId"}, new String[] {dependencyTaskId});
        return orderPlanTaskDependencyList;
    }
    
    @Override
    public List<OrderPlanTaskDependency> getDependencyByTaskId(String taskId)
    {
        String hql = "FROM OrderPlanTaskDependency t WHERE t.taskId = :taskId ";
        List<OrderPlanTaskDependency> orderPlanTaskDependencyList =
            baseDaoSupport.findByNamedParam(OrderPlanTaskDependency.class, hql, new String[] {"taskId"}, new String[] {taskId});
        return orderPlanTaskDependencyList;
    }
    
}
