package com.todaysoft.lims.schedule.service.plan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.entity.OrderPlanTaskDependency;
import com.todaysoft.lims.schedule.entity.ScheduleGlobalConfig;
import com.todaysoft.lims.schedule.model.OrderProductTestings;
import com.todaysoft.lims.schedule.model.Plan;
import com.todaysoft.lims.schedule.model.PlanTask;
import com.todaysoft.lims.schedule.service.IScheduleConfigService;
import com.todaysoft.lims.schedule.service.impl.OrderPlanTaskQueryBuilder;
import com.todaysoft.lims.schedule.service.impl.PlanGenerator;

/**
 * 订单产品组计划处理类
 */
@Service
public class OrderProductGroupPlanHandler
{
    private static Logger log = LoggerFactory.getLogger(OrderProductGroupPlanHandler.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IScheduleConfigService configService;
    
    /**
     * 追加产品计划至相应订单
     */
    public void append(OrderProductTestings testing)
    {
        if (null == testing)
        {
            throw new IllegalArgumentException();
        }
        
        // 订单产品计划已存在，则直接忽略追加操作
        if (isOrderProductGoupPlanExists(testing))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Order product goup plan is exists, ignore the append operation, order {}, product {}", testing.getOrderId(), testing.getProductId());
            }
            
            return;
        }
        
        // 生成产品计划
        generateOrderProductGroupPlan(testing);
    }
    
    private boolean isOrderProductGoupPlanExists(OrderProductTestings testing)
    {
        OrderPlanTaskQueryBuilder builder = new OrderPlanTaskQueryBuilder();
        builder.setOrderId(testing.getOrderId());
        builder.setProductId(testing.getProductId());
        builder.setSemantics(Collections.singleton(OrderPlanTask.SEMANTIC_PRODUCT_GROUP));
        builder.setCountQuery(true);
        
        int count = baseDaoSupport.find(builder.build(), Number.class).get(0).intValue();
        return count > 0;
    }
    
    private void generateOrderProductGroupPlan(OrderProductTestings testing)
    {
        List<PlanTask> tasks = new ArrayList<PlanTask>();
        tasks.add(PlanTask.fromOrderProductTestings(testing));
        
        List<ScheduleGlobalConfig> configs = configService.getProductLevelGlobalConfigs();
        
        if (!CollectionUtils.isEmpty(configs))
        {
            for (ScheduleGlobalConfig config : configs)
            {
                tasks.add(PlanTask.fromProductScheduleGlobalConfig(testing.getProductId(), config));
            }
        }
        
        PlanGenerator generator = new PlanGenerator(OrderPlanTask.SEMANTIC_SAMPLE_STORAGE, tasks);
        Plan plan = generator.generate();
        
        List<PlanTask> plantasks = plan.getTasks();
        
        if (CollectionUtils.isEmpty(plantasks))
        {
            return;
        }
        
        Map<String, PlanTask> context = new HashMap<String, PlanTask>();
        
        // 生成任务
        for (PlanTask plantask : plantasks)
        {
            persist(testing, plantask);
            context.put(plantask.getId(), plantask);
        }
        
        // 设置依赖
        for (PlanTask plantask : plantasks)
        {
            dependency(testing, plantask);
        }
        
        // 更新计划
        plan(testing, context);
    }
    
    private void persist(OrderProductTestings testing, PlanTask plantask)
    {
        String semantic = plantask.getSemantic();
        
        if (semantic.contains("#"))
        {
            semantic = semantic.substring(0, semantic.indexOf("#"));
        }
        
        OrderPlanTask entity = getInitialOrderPlanTask();
        entity.setOrderId(testing.getOrderId());
        entity.setProductId(testing.getProductId());
        entity.setGrouped(plantask.isGrouped());
        entity.setTaskName(plantask.getName());
        entity.setTaskSemantic(semantic);
        baseDaoSupport.insert(entity);
        plantask.setId(entity.getId());
    }
    
    private void dependency(OrderProductTestings testing, PlanTask plantask)
    {
        if (isOrderProductStart(plantask))
        {
            List<OrderPlanTask> preplantasks = getOrderProductGroupPlanPreplantasks(testing.getOrderId());
            
            Set<String> preids = new HashSet<String>();
            
            if (!CollectionUtils.isEmpty(preplantasks))
            {
                for (OrderPlanTask preplantask : preplantasks)
                {
                    preids.add(preplantask.getId());
                }
            }
            
            setDependencies(plantask.getId(), preids);
        }
        else
        {
            List<PlanTask> preplantasks = plantask.getDependencyTasks();
            
            Set<String> preids = new HashSet<String>();
            
            if (!CollectionUtils.isEmpty(preplantasks))
            {
                for (PlanTask preplantask : preplantasks)
                {
                    preids.add(preplantask.getId());
                }
            }
            
            setDependencies(plantask.getId(), preids);
        }
    }
    
    private void plan(OrderProductTestings testing, Map<String, PlanTask> context)
    {
        OrderPlanTask start = getOrderProductGroupPlantask(testing);
        
        if (null == start)
        {
            return;
        }
        
        planByDependency(start, context);
    }
    
    private boolean isOrderProductStart(PlanTask plantask)
    {
        String semantic = plantask.getSemantic();
        
        if (semantic.contains("#"))
        {
            semantic = semantic.substring(0, semantic.indexOf("#"));
        }
        
        return OrderPlanTask.SEMANTIC_PRODUCT_GROUP.equals(semantic);
    }
    
    private List<OrderPlanTask> getOrderProductGroupPlanPreplantasks(String orderId)
    {
        Set<String> semantics = new HashSet<String>();
        semantics.add(OrderPlanTask.SEMANTIC_PAYMENT_CONFIRM);
        semantics.add(OrderPlanTask.SEMANTIC_SAMPLE_STORAGE);
        
        OrderPlanTaskQueryBuilder builder = new OrderPlanTaskQueryBuilder();
        builder.setOrderId(orderId);
        builder.setSemantics(semantics);
        return baseDaoSupport.find(builder.build(), OrderPlanTask.class);
    }
    
    private void setDependencies(String task, Set<String> preplantasks)
    {
        if (CollectionUtils.isEmpty(preplantasks))
        {
            return;
        }
        
        OrderPlanTaskDependency dependency;
        
        for (String preplantask : preplantasks)
        {
            dependency = new OrderPlanTaskDependency();
            dependency.setTaskId(task);
            dependency.setDependencyTaskId(preplantask);
            baseDaoSupport.insert(dependency);
        }
    }
    
    private OrderPlanTask getOrderProductGroupPlantask(OrderProductTestings testing)
    {
        OrderPlanTaskQueryBuilder builder = new OrderPlanTaskQueryBuilder();
        builder.setOrderId(testing.getOrderId());
        builder.setProductId(testing.getProductId());
        builder.setSemantics(Collections.singleton(OrderPlanTask.SEMANTIC_PRODUCT_GROUP));
        List<OrderPlanTask> records = baseDaoSupport.find(builder.build(), OrderPlanTask.class);
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    private void planByDependency(OrderPlanTask task, Map<String, PlanTask> context)
    {
        OrderPlanTaskQueryBuilder builder = new OrderPlanTaskQueryBuilder();
        builder.setPostDependencyId(task.getId());
        builder.setMaxPlannedFinishDateQuery(true);
        Date maxPlannedFinishDate = baseDaoSupport.find(builder.build(), Date.class).get(0);
        
        if (null == maxPlannedFinishDate)
        {
            return;
        }
        
        task.setPlannedStartDate(DateUtils.truncate(DateUtils.addDays(maxPlannedFinishDate, 1), Calendar.DATE));
        
        PlanTask planTask = context.get(task.getId());
        
        if (planTask.getDuration() != null && planTask.getDuration() > 0)
        {
            Date plannedFinishDate = DateUtils.addDays(task.getPlannedStartDate(), planTask.getDuration() - 1);
            task.setPlannedFinishDate(plannedFinishDate);
            
            builder = new OrderPlanTaskQueryBuilder();
            builder.setPreDependencyId(task.getId());
            List<OrderPlanTask> postPlantasks = baseDaoSupport.find(builder.build(), OrderPlanTask.class);
            
            if (!CollectionUtils.isEmpty(postPlantasks))
            {
                for (OrderPlanTask postPlanTask : postPlantasks)
                {
                    planByDependency(postPlanTask, context);
                }
            }
        }
    }
    
    private OrderPlanTask getInitialOrderPlanTask()
    {
        OrderPlanTask entity = new OrderPlanTask();
        entity.setActived(false);
        entity.setFinished(false);
        entity.setCanceled(false);
        entity.setStarted(false);
        entity.setPostponed(false);
        entity.setPostponedDays(0);
        return entity;
    }
}
