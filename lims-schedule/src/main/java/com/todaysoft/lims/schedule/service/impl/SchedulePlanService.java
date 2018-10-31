package com.todaysoft.lims.schedule.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.schedule.entity.MetadataSample;
import com.todaysoft.lims.schedule.entity.MlpaVerifyRecord;
import com.todaysoft.lims.schedule.entity.Order;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.entity.OrderPlanTaskDependency;
import com.todaysoft.lims.schedule.entity.OrderSampleView;
import com.todaysoft.lims.schedule.entity.OrderScheduleMonitor;
import com.todaysoft.lims.schedule.entity.Product;
import com.todaysoft.lims.schedule.entity.ProductTestingMethod;
import com.todaysoft.lims.schedule.entity.QpcrVerifyRecord;
import com.todaysoft.lims.schedule.entity.ReceivedSample;
import com.todaysoft.lims.schedule.entity.SangerVerifyRecord;
import com.todaysoft.lims.schedule.entity.ScheduleGlobalConfig;
import com.todaysoft.lims.schedule.entity.ScheduleTestingConfig;
import com.todaysoft.lims.schedule.entity.ScheduleTestingNodeConfig;
import com.todaysoft.lims.schedule.entity.TaskConfig;
import com.todaysoft.lims.schedule.entity.TestingMethod;
import com.todaysoft.lims.schedule.entity.TestingSample;
import com.todaysoft.lims.schedule.entity.TestingSchedule;
import com.todaysoft.lims.schedule.entity.TestingScheduleActive;
import com.todaysoft.lims.schedule.entity.TestingScheduleHistory;
import com.todaysoft.lims.schedule.entity.TestingTask;
import com.todaysoft.lims.schedule.entity.TestingVerifyRecord;
import com.todaysoft.lims.schedule.model.OrderProductTestings;
import com.todaysoft.lims.schedule.model.OrderSchedules;
import com.todaysoft.lims.schedule.model.OrderTestings;
import com.todaysoft.lims.schedule.model.Plan;
import com.todaysoft.lims.schedule.model.PlanSearchModel;
import com.todaysoft.lims.schedule.model.PlanTask;
import com.todaysoft.lims.schedule.model.TaskSemantic;
import com.todaysoft.lims.schedule.model.TestingPlanTask;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.IScheduleConfigService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;
import com.todaysoft.lims.schedule.service.ITaskConfigService;
import com.todaysoft.lims.schedule.service.ITestingScheduleService;
import com.todaysoft.lims.schedule.service.plan.OrderProductGroupPlanHandler;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SchedulePlanService implements ISchedulePlanService
{
    private static Logger log = LoggerFactory.getLogger(SchedulePlanService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IScheduleConfigService configService;
    
    @Autowired
    private ITaskConfigService taskConfigService;
    
    @Autowired
    private OrderProductGroupPlanHandler orderProductGroupPlanHandler;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    @Transactional
    public void generateOrderSchedulePlan(String id)
    {
        List<ScheduleGlobalConfig> orderGlobalConfigs = configService.getOrderLevelGlobalConfigs();
        OrderTestings orderTestings = orderService.getOrderTestings(id);
        
        List<PlanTask> tasks = new ArrayList<PlanTask>();
        
        if (!CollectionUtils.isEmpty(orderGlobalConfigs))
        {
            for (ScheduleGlobalConfig orderGlobalConfig : orderGlobalConfigs)
            {
                tasks.add(PlanTask.fromScheduleGlobalConfig(orderGlobalConfig));
            }
        }
        
        if (null != orderTestings && !CollectionUtils.isEmpty(orderTestings.getProductTestings()))
        {
            List<ScheduleGlobalConfig> productGlobalConfigs = Lists.newArrayList();
            if (orderService.canWriteReportNode(id))
            {
                //报告三节点
                productGlobalConfigs = configService.getProductLevelGlobalConfigs();
            }
            for (OrderProductTestings productTestings : orderTestings.getProductTestings())
            {
                tasks.add(PlanTask.fromOrderProductTestings(productTestings));
                
                if (!CollectionUtils.isEmpty(productGlobalConfigs))
                {
                    for (ScheduleGlobalConfig productGlobalConfig : productGlobalConfigs)
                    {
                        tasks.add(PlanTask.fromProductScheduleGlobalConfig(productTestings.getProductId(), productGlobalConfig));
                    }
                }
            }
        }
        
        PlanGenerator generator = new PlanGenerator("ORDER_SUBMIT", tasks);
        Plan plan = generator.generate();
        plan.planDate(orderTestings.getSubmitTime());
        
        List<PlanTask> planTasks = plan.getTasks();
        
        OrderPlanTask entity;
        Date plannedFinishDate = null;
        
        for (PlanTask task : planTasks)
        {
            entity = toOrderPlanTask(id, task);
            baseDaoSupport.insert(entity);
            task.setId(entity.getId());
            
            if (entity.getPlannedFinishDate() != null)
            {
                if (null == plannedFinishDate || plannedFinishDate.before(entity.getPlannedFinishDate()))
                {
                    plannedFinishDate = entity.getPlannedFinishDate();
                }
            }
        }
        
        // 设置任务依赖关系
        List<PlanTask> dependencyTasks;
        OrderPlanTaskDependency dependency;
        
        for (PlanTask task : planTasks)
        {
            dependencyTasks = task.getDependencyTasks();
            
            if (!CollectionUtils.isEmpty(dependencyTasks))
            {
                for (PlanTask dependencyTask : dependencyTasks)
                {
                    dependency = new OrderPlanTaskDependency();
                    dependency.setTaskId(task.getId());
                    dependency.setDependencyTaskId(dependencyTask.getId());
                    baseDaoSupport.insert(dependency);
                }
            }
        }
        
        // 设置订单监控记录
        OrderScheduleMonitor monitor = new OrderScheduleMonitor();
        monitor.setOrderId(id);
        monitor.setPostponed(false);
        monitor.setPostponedDays(0);
        
        if (null != plannedFinishDate)
        {
            Date submitDate = orderTestings.getSubmitTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(submitDate);
            int submitDay = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(plannedFinishDate);
            int plannedFinishDay = calendar.get(Calendar.DAY_OF_YEAR);
            monitor.setPlannedDuration(plannedFinishDay - submitDay);
            monitor.setPlannedFinishDate(plannedFinishDate);
        }
        
        baseDaoSupport.insert(monitor);
    }
    
    @Override
    @Transactional
    public void refineOrderSchedulePlan(String id, List<String> scheduleIds)
    {
        if (!orderService.isOrderPlanned(id))
        {
            log.warn("Order plan is not generated, start to generate the plan first, order id {}", id);
            generateOrderSchedulePlan(id);
            
            if (log.isDebugEnabled())
            {
                log.debug("Generate the order schedule plan successful, order id {}", id);
            }
        }
        
        // 细化订单-产品计划
        OrderSchedules orderSchedules = orderService.getOrderSchedules(id, scheduleIds);
        List<String[]> productSamples = orderSchedules.groupAsProductSample();
        
        if (!CollectionUtils.isEmpty(productSamples))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Order grouped as {} product sample pairs.", productSamples.size());
            }
            
            // 细化至订单-产品-样本
            for (String[] productSample : productSamples)
            {
                generateOrderProductSampleSchedulePlan(id, productSample[0], productSample[1], orderSchedules);
                
                if (log.isDebugEnabled())
                {
                    log.debug("Generate the product sample schedule plan successful,order id {}, product id {}, sample id {}.",
                        id,
                        productSample[0],
                        productSample[1]);
                }
            }
        }
    }
    
    @Override
    @Transactional
    public void refineVerifySchedulePlan(List<String> scheduleIds)
    {
        for (String scheduleId : scheduleIds)
        {
            log.info("********scheduleId******" + scheduleId);
            TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
            if (StringUtils.isNotEmpty(schedule))
            {
                TestingSchedule parentSchedule = baseDaoSupport.get(TestingSchedule.class, schedule.getVerifyTarget());
                OrderPlanTask parentPlanTask =
                    getOrderProductSampleMethodPlanTask(parentSchedule.getOrderId(),
                        parentSchedule.getProductId(),
                        parentSchedule.getSampleId(),
                        parentSchedule.getMethodId());
                generateVerifySampleMethodSchedulePlan(schedule, parentPlanTask);
            }
            else
            {
                log.error("according scheduleId" + scheduleId + "can not find shedule");
            }
            
        }
    }
    
    @Override
    @Transactional
    public void updatePlanForOrderSubmit(String id)
    {
        // 查询初始任务
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.parentId IS NULL AND NOT EXISTS (SELECT optd.id FROM OrderPlanTaskDependency optd WHERE optd.taskId = opt.id)")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(id))
                .build();
        List<OrderPlanTask> tasks = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(tasks))
        {
            throw new IllegalStateException();
        }
        
        for (OrderPlanTask task : tasks)
        {
            task.setActived(true);
            task.setStarted(true);
            task.setActualStartDate(new Date());
            baseDaoSupport.update(task);
        }
    }
    
    @Override
    @Transactional
    public void updatePlanForOrderModify(String id)
    {
        OrderTestings orderTestings = orderService.getOrderTestings(id);
        
        Set<String> products = new HashSet<String>();
        
        if (null != orderTestings && !CollectionUtils.isEmpty(orderTestings.getProductTestings()))
        {
            for (OrderProductTestings productTestings : orderTestings.getProductTestings())
            {
                orderProductGroupPlanHandler.append(productTestings);
                products.add(productTestings.getProductId());
            }
        }
        
        NamedQueryer queryer;
        
        if (CollectionUtils.isEmpty(products))
        {
            queryer =
                NamedQueryer.builder()
                    .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId")
                    .names(Arrays.asList("orderId"))
                    .values(Arrays.asList(id))
                    .build();
        }
        else
        {
            queryer =
                NamedQueryer.builder()
                    .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId NOT IN (:products)")
                    .names(Arrays.asList("orderId", "products"))
                    .values(Arrays.asList(id, products))
                    .build();
        }
        
        List<OrderPlanTask> tasks = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (!CollectionUtils.isEmpty(tasks))
        {
            for (OrderPlanTask task : tasks)
            {
                deleteOrderPlanTask(task);
            }
        }
        
        // 更新订单计划
        OrderPlanTaskQueryBuilder builder = new OrderPlanTaskQueryBuilder();
        builder.setOrderId(id);
        builder.setMaxPlannedFinishDateQuery(true);
        Date maxPlannedFinishDate = baseDaoSupport.find(builder.build(), Date.class).get(0);
        
        if (null == maxPlannedFinishDate)
        {
            return;
        }
        
        OrderScheduleMonitor monitor = baseDaoSupport.get(OrderScheduleMonitor.class, id);
        
        if (null == monitor)
        {
            return;
        }
        
        Order order = baseDaoSupport.get(Order.class, id);
        Date submitDate = order.getSubmitTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(submitDate);
        int submitDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(maxPlannedFinishDate);
        int plannedFinishDay = calendar.get(Calendar.DAY_OF_YEAR);
        monitor.setPlannedDuration(plannedFinishDay - submitDay);
        monitor.setPlannedFinishDate(maxPlannedFinishDate);
        baseDaoSupport.update(monitor);
    }
    
    private void deleteOrderPlanTask(OrderPlanTask task)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTaskDependency optd WHERE optd.taskId = :taskId OR optd.dependencyTaskId = :taskId")
                .names(Arrays.asList("taskId"))
                .values(Arrays.asList(task.getId()))
                .build();
        
        List<OrderPlanTaskDependency> dependencies = baseDaoSupport.find(queryer, OrderPlanTaskDependency.class);
        
        if (!CollectionUtils.isEmpty(dependencies))
        {
            for (OrderPlanTaskDependency dependency : dependencies)
            {
                baseDaoSupport.delete(dependency);
            }
        }
        
        baseDaoSupport.delete(task);
    }
    
    @Override
    @Transactional
    public void updatePlanForOrderCancel(String id)
    {
        //查找当前进行中的节点(包含了组节点)
        List<OrderPlanTask> activedTasks = baseDaoSupport.find(OrderPlanTask.class, "FROM OrderPlanTask o WHERE o.orderId = '" + id + "' AND o.actived = true");
        if (Collections3.isNotEmpty(activedTasks))
        {
            for (OrderPlanTask task : activedTasks)
            {
                task.setActived(false);
                task.setCanceled(true);//已取消
                task.setFinished(false);//已完成置为未完成
                task.setActualFinishDate(new Date());
                baseDaoSupport.update(task);
            }
        }
        /*NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.finished = false ")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(id))
                .build();
        List<OrderPlanTask> tasks = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (!CollectionUtils.isEmpty(tasks))
        {
            for (OrderPlanTask task : tasks)
            {
                task.setActived(false);
                task.setCanceled(true);
                baseDaoSupport.update(task);
            }
        }*/
    }
    
    @Override
    @Transactional
    public void updatePlanForOrderTestingStart(String id)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.taskSemantic = :productGroupSemantic")
                .names(Arrays.asList("orderId", "productGroupSemantic"))
                .values(Arrays.asList(id, OrderPlanTask.SEMANTIC_PRODUCT_GROUP))
                .build();
        
        List<OrderPlanTask> tasks = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(tasks))
        {
            throw new IllegalStateException();
        }
        
        for (OrderPlanTask task : tasks)
        {
            startTask(task);
        }
    }
    
    @Override
    @Transactional
    public void updatePlanForSampleReceiveFinished(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.taskSemantic = :sampleReceiveSemantic")
                .names(Arrays.asList("orderId", "sampleReceiveSemantic"))
                .values(Arrays.asList(orderId, OrderPlanTask.SEMANTIC_SAMPLE_RECEIVE))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            log.error("Can not found the sample receive plan task for order {}.", orderId);
            return;
        }
        
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        OrderPlanTask task = records.get(0);
        updatePlanForTaskFinished(task.getId());
    }
    
    @Override
    @Transactional
    public void updatePlanForSampleStorageFinished(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.taskSemantic = :sampleStorageSemantic")
                .names(Arrays.asList("orderId", "sampleStorageSemantic"))
                .values(Arrays.asList(orderId, OrderPlanTask.SEMANTIC_SAMPLE_STORAGE))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            log.error("Can not found the sample sotrage plan task for order {}.", orderId);
            return;
        }
        
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        OrderPlanTask task = records.get(0);
        updatePlanForTaskFinished(task.getId(), false);
    }
    
    @Override
    @Transactional
    public void updatePlanForPaymentConfirmFinished(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.taskSemantic = :paymentConfirmSemantic")
                .names(Arrays.asList("orderId", "paymentConfirmSemantic"))
                .values(Arrays.asList(orderId, OrderPlanTask.SEMANTIC_PAYMENT_CONFIRM))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            log.error("Can not found the payment confirm plan task for order {}.", orderId);
            return;
        }
        
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        OrderPlanTask task = records.get(0);
        updatePlanForTaskFinished(task.getId(), false);
    }
    
    @Override
    @Transactional
    public void updatePlanForReportGenerateFinished(String orderId, String productId)
    {
        
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId AND opt.taskSemantic = :reportGenerateSemantic")
                .names(Arrays.asList("orderId", "productId", "reportGenerateSemantic"))
                .values(Arrays.asList(orderId, productId, OrderPlanTask.SEMANTIC_REPORT_GENERATE))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            log.error("Can not found the report generate plan task for order {}.", orderId);
            return;
        }
        
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        OrderPlanTask task = records.get(0);
        updatePlanForTaskFinished(task.getId());
    }
    
    @Override
    @Transactional
    public void updatePlanForReportVerifyFinished(String orderId, String productId, String reviewResult)
    {
        
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId AND opt.taskSemantic = :reportVerifySemantic")
                .names(Arrays.asList("orderId", "productId", "reportVerifySemantic"))
                .values(Arrays.asList(orderId, productId, OrderPlanTask.SEMANTIC_REPORT_VERIFY))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            log.error("Can not found the report verify plan task for order {}.", orderId);
            return;
        }
        
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        OrderPlanTask task = records.get(0);
        //不通过，完成当前，激活上个节点
        if ("2".equals(reviewResult))
        {
            task.setActived(false);
            task.setFinished(true);
            task.setActualFinishDate(new Date());
            baseDaoSupport.update(task);
            String hql = "FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId AND opt.taskSemantic = :reportVerifySemantic";
            List<OrderPlanTask> list =
                baseDaoSupport.findByNamedParam(OrderPlanTask.class, hql, new String[] {"orderId", "productId", "reportVerifySemantic"}, new Object[] {orderId,
                    productId, OrderPlanTask.SEMANTIC_REPORT_GENERATE});
            if (Collections3.isNotEmpty(list))
            {
                OrderPlanTask planTask = list.get(0);
                if (null != planTask)
                {
                    planTask.setActived(true);
                    planTask.setActualStartDate(new Date());
                    planTask.setActualFinishDate(null);
                    baseDaoSupport.update(planTask);
                }
            }
            
        }
        else if ("1".equals(reviewResult))
        {
            updatePlanForTaskFinished(task.getId());
        }
        
    }
    
    @Override
    @Transactional
    public void updatePlanForReportDeliverFinished(String orderId, String productId)
    {
        
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId AND opt.taskSemantic = :reportDeliverSemantic")
                .names(Arrays.asList("orderId", "productId", "reportDeliverSemantic"))
                .values(Arrays.asList(orderId, productId, OrderPlanTask.SEMANTIC_REPORT_DELIVER))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            log.error("Can not found the report deliver plan task for order {}.", orderId);
            return;
        }
        
        if (records.size() > 1)
        {
            throw new IllegalStateException();
        }
        
        OrderPlanTask task = records.get(0);
        updatePlanForTaskFinished(task.getId());
        
        //提前出报告，应该把实验流程里面进行中的置为已取消  2017.10.31 
        String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId AND t.endType is null AND t.status = 2 order by t.startTime DESC ";
        List<TestingSchedule> schedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId"}, new Object[] {orderId});
        if (Collections3.isNotEmpty(schedules))
        {
            for (TestingSchedule schedule : schedules)
            {
                testingScheduleService.updateStatusSearchCancel(schedule.getId());
            }
        }
        
    }
    
    @Override
    @Transactional
    public void updatePlanForTaskFinished(String taskId)
    {
        updatePlanForTaskFinished(taskId, true);
    }
    
    @Override
    @Transactional
    public void updatePlanForTaskFinished(String taskId, boolean dependencyAutoStart)
    {
        OrderPlanTask task = baseDaoSupport.get(OrderPlanTask.class, taskId);
        
        if (null == task)
        {
            throw new IllegalStateException();
        }
        
        // 1、更新计划任务信息
        task.setActived(false);
        if (task.isCanceled())
        {
            task.setCanceled(false);
        }
        task.setFinished(true);
        task.setActualFinishDate(new Date());
        System.out.println("=========" + task.getId() + "。。。。。" + task.isStarted());
        
        baseDaoSupport.update(task);
        
        List<OrderPlanTask> postDependencyTasks = getPostDependencyTasks(task.getId());
        
        // 如果没有后续任务，假设计划完成处理
        if (CollectionUtils.isEmpty(postDependencyTasks))
        {
            //没有验证流程(如技术分析下一步没有验证)  ww
            if (!isHasYanZhen(task))
            {
                // 一级计划，更新订单完成时间
                if (task.getParentId() == null)
                {
                    OrderScheduleMonitor monitor = baseDaoSupport.get(OrderScheduleMonitor.class, task.getOrderId());
                    
                    if (null != monitor)
                    {
                        monitor.setActualFinishDate(new Date());
                        baseDaoSupport.update(monitor);
                    }
                }
                else
                {
                    if (task.getTaskSemantic().contains("GROUP_PRODUCT"))
                    {
                        if (!isGroupCompleteSchedulePlannedOrCancel(task.getParentId()))
                        {
                            updatePlanForTaskFinished(task.getParentId(), dependencyAutoStart);
                        }
                    }
                    else
                    {
                        // 该任务属于计划组，如果改组依赖的任务都完成了就更新改组状态，则更新计划组的完成时间
                        // 查询parentid 是这个组的任务 是否都完成了
                        if (!isGroupCompleteSchedulePlanned(task.getParentId()))
                        {// true 说明有未完成的 不能完成当前组,反之完成当前组任务
                            updatePlanForTaskFinished(task.getParentId(), dependencyAutoStart);
                        }
                    }
                }
            }
            else
            //向上递归，解决到“GROUP_PRODUCT_SAMPLE_METHOD”无法向上
            {
                if (task.getTaskSemantic().contains("GROUP_PRODUCT"))
                {
                    // 一级计划，更新订单完成时间
                    if (task.getParentId() == null)
                    {
                        OrderScheduleMonitor monitor = baseDaoSupport.get(OrderScheduleMonitor.class, task.getOrderId());
                        
                        if (null != monitor)
                        {
                            monitor.setActualFinishDate(new Date());
                            baseDaoSupport.update(monitor);
                        }
                    }
                    else
                    {
                        if (task.getTaskSemantic().contains("GROUP_PRODUCT"))
                        {
                            if (!isGroupCompleteSchedulePlannedOrCancel(task.getParentId()))
                            {
                                updatePlanForTaskFinished(task.getParentId(), dependencyAutoStart);
                            }
                        }
                        else
                        {
                            // 该任务属于计划组，如果改组依赖的任务都完成了就更新改组状态，则更新计划组的完成时间
                            // 查询parentid 是这个组的任务 是否都完成了
                            if (!isGroupCompleteSchedulePlanned(task.getParentId()))
                            {// true 说明有未完成的 不能完成当前组,反之完成当前组任务
                                updatePlanForTaskFinished(task.getParentId(), dependencyAutoStart);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            for (OrderPlanTask postDependencyTask : postDependencyTasks)
            {
                updateTaskForPredependencyFinished(postDependencyTask, dependencyAutoStart);
            }
        }
    }
    
    //判断是否有验证流程
    private boolean isHasYanZhen(OrderPlanTask task)
    {
        String hql =
            "FROM TestingSchedule lts WHERE EXISTS(select ts.id from TestingSchedule ts where ts.verifyTarget = lts.id)"
                + " AND lts.orderId = :orderId AND lts.productId = :productId AND lts.sampleId = :sampleId AND lts.methodId = :methodId AND lts.status != 2";
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql(hql)
                .names(Arrays.asList("orderId", "productId", "sampleId", "methodId"))
                .values(Arrays.asList(task.getOrderId(), task.getProductId(), task.getSampleId(), task.getTestingMethodId()))
                .build();
        List<TestingSchedule> list = baseDaoSupport.find(queryer, TestingSchedule.class);
        if (Collections3.isNotEmpty(list))
        {
            return true;
        }
        return false;
        
    }
    
    private void updateTaskForPredependencyFinished(OrderPlanTask task, boolean dependencyAutoStart)
    {
        boolean dependencyTasksAllFinished = true;
        List<OrderPlanTask> dependencyTasks = getPredependencyTasks(task.getId());
        
        if (!CollectionUtils.isEmpty(dependencyTasks))
        {
            for (OrderPlanTask dependencyTask : dependencyTasks)
            {
                if (!dependencyTask.isFinished() && !dependencyTask.isCanceled())
                {
                    dependencyTasksAllFinished = false;
                    break;
                }
            }
        }
        
        if (!dependencyTasksAllFinished)
        {
            return;
        }
        
        updateTaskForPredependencyAllFinished(task, dependencyAutoStart);
    }
    
    private void updateTaskForPredependencyAllFinished(OrderPlanTask task, boolean dependencyAutoStart)
    {
        
        if (!OrderPlanTask.SEMANTIC_REPORT_GENERATE.equals(task.getTaskSemantic()))
        {
            boolean isUpdateCurrentPlan = true; //是否更改当前计划，还是更改下一个计划
            Date date = new Date();
            if ((task.getTaskSemantic().equals(TaskSemantic.DNA_EXTRACT)))
            { //此处最好加入isCanceled()==true
              //lifukang,如果是DNA提取，需要判断重新送样的样本类型，看从哪个节点开始，     ？？？如果一个样本做了多个流程，都重新送样了，可能存在问题
              //判断是否存在异常重新送样的样本
                if (StringUtils.isNotBlank(task.getSampleId()))
                {
                    TestingSample testingSample = baseDaoSupport.get(TestingSample.class, task.getSampleId());
                    List<OrderSampleView> orderSampleViews =
                        baseDaoSupport.find(OrderSampleView.class, "FROM OrderSampleView o where o.sampleCode = '" + testingSample.getSampleCode() + "'");
                    if (Collections3.isNotEmpty(orderSampleViews))
                    {
                        if (orderSampleViews.size() > 1)
                        {
                            log.error("sampleCode:" + testingSample.getSampleCode() + ",找到两个样本");
                        }
                        else
                        {
                            if (StringUtils.isNotBlank(orderSampleViews.get(0).getSampleErrorNewNo()))
                            {
                                List<OrderSampleView> reAddOrderSampleViews =
                                    baseDaoSupport.find(OrderSampleView.class, "FROM OrderSampleView o where o.sampleCode = '"
                                        + orderSampleViews.get(0).getSampleErrorNewNo() + "'");
                                if (Collections3.isNotEmpty(reAddOrderSampleViews))
                                {
                                    MetadataSample metadataSample = baseDaoSupport.get(MetadataSample.class, reAddOrderSampleViews.get(0).getSampleType());
                                    if (metadataSample.getName().contains("DNA"))
                                    {
                                        task.setFinished(true);
                                        baseDaoSupport.update(task);
                                        PlanSearchModel model = new PlanSearchModel();
                                        model.setSemantic(TaskSemantic.DNA_QC);
                                        model.setOrderId(task.getOrderId());
                                        model.setProductId(task.getProductId());
                                        model.setMethodId(task.getTestingMethodId());
                                        model.setSampleId(task.getSampleId());
                                        model.setVerifyKey(task.getVerifyId());
                                        OrderPlanTask nextOrderPlanTask = testingScheduleService.getOrderPlanTaskByTaskId(model);
                                        date = nextOrderPlanTask.getPlannedFinishDate();
                                        nextOrderPlanTask.setActived(true);
                                        nextOrderPlanTask.setStarted(true);
                                        nextOrderPlanTask.setActualStartDate(new Date());
                                        baseDaoSupport.update(nextOrderPlanTask);
                                        isUpdateCurrentPlan = false;
                                    }
                                }
                                else
                                {
                                    log.error("sampleCode:" + orderSampleViews.get(0).getSampleErrorNewNo() + ",未找到异常样本");
                                }
                            }
                        }
                    }
                    else
                    {
                        log.error("sampleCode:" + testingSample.getSampleCode() + ",未找到样本");
                    }
                }
            }
            //向下打开过滤掉报告整合节点，在写入报告的时候就监控进行中  2017.10.31
            if (isUpdateCurrentPlan)
            {
                date = savePlan(task, dependencyAutoStart);
            }
            //openOrCloseActived(task,false);
            
            // 下一步任务启动监控后先计算是否延期
            if (null != date)
            {
                Date today = new Date();
                int days = getDays(date, today);
                
                if (days > 0)
                {
                    setPostponed(task, days);
                }
                else
                {
                    //当天完成，如果天数等于0不更新，还是上次延期的天数，延期天数就不对了
                    setPostponedZero(task, 0);
                }
            }
            
            // 如果是组任务，更新组里无依赖的任务
            if (task.isGrouped())
            {
                List<OrderPlanTask> independentSubtasks = getIndependentSubtasks(task.getId());
                
                if (!CollectionUtils.isEmpty(independentSubtasks))
                {
                    for (OrderPlanTask independentSubtask : independentSubtasks)
                    {
                        updateTaskForPredependencyAllFinished(independentSubtask, dependencyAutoStart);
                    }
                }
            }
        }
    }
    
    private Date savePlan(OrderPlanTask task, boolean dependencyAutoStart)
    {
        Date date;
        task.setActived(true);
        task.setActualStartDate(new Date());
        
        if (dependencyAutoStart)
        {
            task.setStarted(true);
            date = task.getPlannedFinishDate();
        }
        else
        {
            date = task.getPlannedStartDate();
        }
        
        //2017.10.26 （依赖节点）如果是已完成的，说明之前完成过，清空实际完成时间
        if (task.isFinished())
        {
            task.setActualFinishDate(null);
        }
        
        baseDaoSupport.update(task);
        return date;
    }
    
    //判断下个监控任务实际开始时间不为空、是已完成的，不监控(除非强制打开监控)
    private void openOrCloseActived(OrderPlanTask task, boolean mustFlag)
    {
        if (null != task)
        {
            if (null != task.getActualStartDate() && task.isFinished())
            {
                if (!mustFlag)
                {
                    task.setActived(false);
                    baseDaoSupport.update(task);
                }
            }
        }
    }
    
    private List<OrderPlanTask> getPostDependencyTasks(String taskId)
    {
        //2017.12.6  由于查询超时 拆开查询
        /*NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE EXISTS (SELECT optd.id FROM OrderPlanTaskDependency optd WHERE optd.taskId = opt.id AND optd.dependencyTaskId = :taskId)")
                .names(Arrays.asList("taskId"))
                .values(Arrays.asList(taskId))
                .build();
        return baseDaoSupport.find(queryer, OrderPlanTask.class);*/
        List<OrderPlanTask> list = new ArrayList<OrderPlanTask>();
        List<OrderPlanTaskDependency> depList =
            baseDaoSupport.find(OrderPlanTaskDependency.class, "FROM OrderPlanTaskDependency o where o.dependencyTaskId = '" + taskId + "'");
        for (OrderPlanTaskDependency opd : depList)
        {
            list.add(baseDaoSupport.get(OrderPlanTask.class, opd.getTaskId()));
        }
        return list;
    }
    
    private List<OrderPlanTask> getPredependencyTasks(String taskId)
    {
        //2017.12.6  由于查询超时 拆开查询
        /*NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE EXISTS (SELECT optd.id FROM OrderPlanTaskDependency optd WHERE optd.dependencyTaskId = opt.id AND optd.taskId = :taskId)")
                .names(Arrays.asList("taskId"))
                .values(Arrays.asList(taskId))
                .build();
        return baseDaoSupport.find(queryer, OrderPlanTask.class);*/
        List<OrderPlanTask> list = new ArrayList<OrderPlanTask>();
        List<OrderPlanTaskDependency> depList =
            baseDaoSupport.find(OrderPlanTaskDependency.class, "FROM OrderPlanTaskDependency o where o.taskId = '" + taskId + "'");
        for (OrderPlanTaskDependency opd : depList)
        {
            list.add(baseDaoSupport.get(OrderPlanTask.class, opd.getDependencyTaskId()));
        }
        return list;
    }
    
    private void startTask(OrderPlanTask task)
    {
        log.info("OrderPlanTask==========" + task.getId());
        task.setActived(true);
        task.setStarted(true);
        task.setActualStartDate(new Date());
        baseDaoSupport.update(task);
        
        log.info("OrderPlanTask==========" + task.getId() + ",," + task.isStarted());
        
        // 判断是否有延期
        Date date = task.getPlannedFinishDate();
        
        if (null != date)
        {
            Date today = new Date();
            int days = getDays(date, today);
            
            if (days > 0)
            {
                setPostponed(task, days);
            }
            else
            {
                setPostponedZero(task, 0);
            }
        }
        
        if (task.isGrouped())
        {
            List<OrderPlanTask> subtasks = getIndependentSubtasks(task.getId());
            
            if (!CollectionUtils.isEmpty(subtasks))
            {
                for (OrderPlanTask subtask : subtasks)
                {
                    startTask(subtask);
                }
            }
        }
    }
    
    private void setPostponed(OrderPlanTask task, int postponedDays)
    {
        task.setPostponed(true);
        task.setPostponedDays(postponedDays);
        baseDaoSupport.update(task);
        
        //2017.11.1 实时更新LIMS_ORDER_SCHEDULE_MONITOR，使项目监控排序同步
        OrderScheduleMonitor monitor = baseDaoSupport.get(OrderScheduleMonitor.class, task.getOrderId());
        if (null != monitor)
        {
            monitor.setPostponed(true);
            monitor.setPostponedDays(postponedDays);
            baseDaoSupport.update(monitor);
        }
        
        // 查找并设置上级任务延迟时间
        String parentId = task.getParentId();
        if (StringUtils.isEmpty(parentId))
        {
            return;
        }
        
        OrderPlanTask parentTask = baseDaoSupport.get(OrderPlanTask.class, parentId);
        int parentTaskPostponedDays = getGroupTaskPostponedDays(parentId);
        setPostponed(parentTask, parentTaskPostponedDays);
    }
    
    //ww 2017.8.7 没有延期也更新父节点
    private void setPostponedZero(OrderPlanTask task, int zero)
    {
        task.setPostponed(false);
        task.setPostponedDays(zero);
        baseDaoSupport.update(task);
        
        // 查找并设置上级任务延迟时间
        String parentId = task.getParentId();
        
        if (StringUtils.isEmpty(parentId))
        {
            return;
        }
        
        OrderPlanTask parentTask = baseDaoSupport.get(OrderPlanTask.class, parentId);
        setPostponedZero(parentTask, zero);
    }
    
    private int getGroupTaskPostponedDays(String taskId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT MAX(opt.postponedDays) FROM OrderPlanTask opt WHERE opt.parentId = :parentId")
                .names(Arrays.asList("parentId"))
                .values(Arrays.asList(taskId))
                .build();
        if (Collections3.isNotEmpty(baseDaoSupport.find(queryer, Number.class)))
        {
            if (null != baseDaoSupport.find(queryer, Number.class).get(0))
            {
                return baseDaoSupport.find(queryer, Number.class).get(0).intValue();
            }
        }
        return 0;
    }
    
    private List<OrderPlanTask> getIndependentSubtasks(String taskId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.parentId = :parentId AND NOT EXISTS (SELECT optd.id FROM OrderPlanTaskDependency optd WHERE optd.taskId = opt.id)")
                .names(Arrays.asList("parentId"))
                .values(Arrays.asList(taskId))
                .build();
        return baseDaoSupport.find(queryer, OrderPlanTask.class);
    }
    
    private void generateOrderProductSampleSchedulePlan(String orderId, String productId, String sampleId, OrderSchedules orderSchedules)
    {
        if (isOrderProductSampleSchedulePlanned(orderId, productId, sampleId))
        {
            return;
        }
        
        OrderPlanTask productGroup = getOrderProductGroupPlanTask(orderId, productId);
        
        if (null == productGroup)
        {
            throw new IllegalStateException();
        }
        
        Product product = baseDaoSupport.get(Product.class, productId);
        TestingSample sample = baseDaoSupport.get(TestingSample.class, sampleId);
        
        String productName = null == product ? "未知产品" : product.getName();
        String sampleCode;
        if (null != sample)
        {
            sampleCode = sample.getSampleCode();
        }
        else
        {
            ReceivedSample receivedSample = baseDaoSupport.get(ReceivedSample.class, sampleId);
            sampleCode = null == receivedSample ? "未知样本" : receivedSample.getSampleCode();
        }
        
        OrderPlanTask entity = new OrderPlanTask();
        entity.setParentId(productGroup.getId());
        entity.setTaskSemantic("GROUP_PRODUCT_SAMPLE");
        entity.setTaskName(productName + "-" + sampleCode);
        entity.setOrderId(orderId);
        entity.setProductId(productId);
        entity.setSampleId(sampleId);
        entity.setPlannedStartDate(productGroup.getPlannedStartDate());
        entity.setActived(false);
        entity.setFinished(false);
        entity.setCanceled(false);
        entity.setGrouped(true);
        entity.setPostponed(false);
        entity.setPostponedDays(0);
        baseDaoSupport.insert(entity);
        
        // 细化至订单-产品-样本-检测方法
        List<String[]> productSampleMethods = orderSchedules.groupAsProductSampleMethod(productId, sampleId);
        
        Date plannedFinishDate = null;
        
        if (!CollectionUtils.isEmpty(productSampleMethods))
        {
            OrderPlanTask task;
            
            for (String[] productSampleMethod : productSampleMethods)
            {
                task =
                    generateOrderProductSampleMethodSchedulePlan(orderId,
                        productSampleMethod[0],
                        productSampleMethod[1],
                        productSampleMethod[2],
                        entity,
                        orderSchedules);
                
                if (null != task.getPlannedFinishDate())
                {
                    if (null == plannedFinishDate || plannedFinishDate.before(task.getPlannedFinishDate()))
                    {
                        plannedFinishDate = task.getPlannedFinishDate();
                    }
                }
            }
        }
        
        // 设置最长时间为结束时间
        if (null != plannedFinishDate)
        {
            entity.setPlannedFinishDate(plannedFinishDate);
        }
        baseDaoSupport.update(entity);
        //ww
        if (null != productGroup.getPlannedFinishDate())
        {
            if (!StringUtils.isNotEmpty(plannedFinishDate))
            {
                log.error("上一个节点计划完成时间为空");
            }
            if (productGroup.getPlannedFinishDate().before(plannedFinishDate))
            {
                productGroup.setPlannedFinishDate(plannedFinishDate);
                baseDaoSupport.update(productGroup);
            }
        }
        
    }
    
    private OrderPlanTask generateOrderProductSampleMethodSchedulePlan(String orderId, String productId, String sampleId, String testingMethodId, OrderPlanTask productSampleGroupPlanTask, OrderSchedules orderSchedules)
    {
        Product product = baseDaoSupport.get(Product.class, productId);
        TestingSample sample = baseDaoSupport.get(TestingSample.class, sampleId);
        TestingMethod method = baseDaoSupport.get(TestingMethod.class, testingMethodId);
        
        String productName = null == product ? "未知产品" : product.getName();
        String sampleCode = null == sample ? "未知样本" : sample.getSampleCode();
        String methodName = null == method ? "未知检测方法" : method.getName();
        
        OrderPlanTask entity = new OrderPlanTask();
        entity.setParentId(productSampleGroupPlanTask.getId());
        entity.setTaskSemantic("GROUP_PRODUCT_SAMPLE_METHOD");
        entity.setTaskName(productName + "-" + sampleCode + "-" + methodName);
        entity.setOrderId(orderId);
        entity.setProductId(productId);
        entity.setSampleId(sampleId);
        entity.setTestingMethodId(testingMethodId);
        entity.setPlannedStartDate(productSampleGroupPlanTask.getPlannedStartDate());
        entity.setActived(false);
        entity.setFinished(false);
        entity.setCanceled(false);
        entity.setGrouped(true);
        entity.setPostponed(false);
        entity.setPostponedDays(0);
        baseDaoSupport.insert(entity);
        
        // 细化至订单-产品-样本-检测方法-检测节点
        TestingSchedule schedule = orderSchedules.getProductSampleMethodTestingSchedule(productId, sampleId, testingMethodId);
        
        if (null == schedule)
        {
            throw new IllegalStateException();
        }
        
        List<String> nodes = getScheduleNodes(schedule);
        Map<String, ScheduleTestingNodeConfig> configs = getProductMethodNodeConfigs(productId, testingMethodId);
        
        TaskConfig config;
        OrderPlanTask nodePlanTask;
        OrderPlanTask lastNodePlanTask = null;
        OrderPlanTaskDependency dependency;
        ScheduleTestingNodeConfig scheduleTestingNodeConfig;
        
        for (String node : nodes)
        {
            config = taskConfigService.getTaskConfig(node);
            nodePlanTask = new OrderPlanTask();
            nodePlanTask.setParentId(entity.getId());
            nodePlanTask.setTaskSemantic(node);
            nodePlanTask.setTaskName(null == config ? "未知任务节点" : config.getName());
            nodePlanTask.setOrderId(orderId);
            nodePlanTask.setProductId(productId);
            nodePlanTask.setSampleId(sampleId);
            nodePlanTask.setTestingMethodId(testingMethodId);
            nodePlanTask.setActived(false);
            nodePlanTask.setFinished(false);
            nodePlanTask.setCanceled(false);
            nodePlanTask.setGrouped(false);
            nodePlanTask.setPostponed(false);
            nodePlanTask.setPostponedDays(0);
            baseDaoSupport.insert(nodePlanTask);
            
            Date taskPlannedFinishDate = null;
            // 设置依赖
            if (null != lastNodePlanTask)
            {
                dependency = new OrderPlanTaskDependency();
                dependency.setTaskId(nodePlanTask.getId());
                dependency.setDependencyTaskId(lastNodePlanTask.getId());
                baseDaoSupport.insert(dependency);
                
                if (null != lastNodePlanTask.getPlannedFinishDate())
                {
                    nodePlanTask.setPlannedStartDate(DateUtils.addDays(lastNodePlanTask.getPlannedFinishDate(), 1));
                    scheduleTestingNodeConfig = configs.get(node);
                    
                    if (null != scheduleTestingNodeConfig && null != scheduleTestingNodeConfig.getThreshold())
                    {
                        Date plannedFinishDate = DateUtils.addDays(nodePlanTask.getPlannedStartDate(), scheduleTestingNodeConfig.getThreshold() - 1);
                        nodePlanTask.setPlannedFinishDate(plannedFinishDate);
                        taskPlannedFinishDate = plannedFinishDate;
                    }
                }
            }
            else
            {
                nodePlanTask.setPlannedStartDate(entity.getPlannedStartDate());
                scheduleTestingNodeConfig = configs.get(node);
                
                if (null != scheduleTestingNodeConfig && null != scheduleTestingNodeConfig.getThreshold())
                {
                    Date plannedFinishDate = DateUtils.addDays(nodePlanTask.getPlannedStartDate(), scheduleTestingNodeConfig.getThreshold() - 1);
                    nodePlanTask.setPlannedFinishDate(plannedFinishDate);
                    taskPlannedFinishDate = plannedFinishDate;
                }
            }
            
            lastNodePlanTask = nodePlanTask;
            //dna提取、dna质检、cdna提取 反填task应完成时间
            if ("DNA-EXT".equals(node) || "DNA-QC".equals(node) || "CDNA-EXT".equals(node))
            {
                List<TestingTask> taskList = getActiveTaskIds(node, orderId, productId, testingMethodId, sampleId);
                if (Collections3.isNotEmpty(taskList))
                {
                    for (TestingTask task : taskList)
                    {
                        task.setPlannedFinishDate(taskPlannedFinishDate);
                        baseDaoSupport.update(task);
                    }
                }
            }
            
        }
        
        // 取最后一个节点的完成时间为计划组的完成时间
        if (null != lastNodePlanTask && null != lastNodePlanTask.getPlannedFinishDate())
        {
            entity.setPlannedFinishDate(lastNodePlanTask.getPlannedFinishDate());
            baseDaoSupport.update(entity);
        }
        
        return entity;
    }
    
    private OrderPlanTask generateVerifySampleMethodSchedulePlan(TestingSchedule schedule, OrderPlanTask parentPlanTask)
    {
        
        if (null == schedule)
        {
            throw new IllegalStateException();
        }
        String sampleCode = null;
        Product product = baseDaoSupport.get(Product.class, schedule.getProductId());
        ReceivedSample sample = baseDaoSupport.get(ReceivedSample.class, schedule.getSampleId());
        if (null == sample)
        {// 已经做过DNA的样本
            TestingSample sam = baseDaoSupport.get(TestingSample.class, schedule.getSampleId());
            if (null != sam && null != sam.getReceivedSample())
            {
                sampleCode = sam.getReceivedSample().getSampleCode();
            }
        }
        else
        {
            sampleCode = sample.getSampleCode();
        }
        TestingMethod method = baseDaoSupport.get(TestingMethod.class, schedule.getMethodId());
        
        String productName = null == product ? "未知产品" : product.getName();
        sampleCode = null == sampleCode ? "未知样本" : sampleCode;
        String methodName = null == method ? "未知检测方法" : method.getName();
        
        OrderPlanTask entity = new OrderPlanTask();
        entity.setParentId(parentPlanTask.getId());
        TestingVerifyRecord verifyRecord = getVerifyRecordByVerifyKey(schedule.getVerifyKey());
        if (null == verifyRecord)
        {
            throw new IllegalStateException();
        }
        String chromosomalLocation = "";
        if (StringUtils.isNotEmpty(verifyRecord.getAnalyRecord().getChromosomalLocation()))
        {
            chromosomalLocation = verifyRecord.getAnalyRecord().getChromosomalLocation();
        }
        entity.setTaskSemantic("GROUP_PRODUCT_SAMPLE_METHOD_VERIFY");
        entity.setTaskName(productName + "-" + sampleCode + "-" + methodName + "-" + chromosomalLocation + "-" + verifyRecord.getAnalyRecord().getGeneSymbol());
        entity.setOrderId(schedule.getOrderId());
        entity.setProductId(schedule.getProductId());
        entity.setSampleId(schedule.getSampleId());
        entity.setTestingMethodId(schedule.getMethodId());
        
        entity.setVerifyId(schedule.getVerifyKey());
        // 去父节点最后一个实验节点作为计划开始时间
        List<OrderPlanTask> nodePlanTasks =
            baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                "from OrderPlanTask o where o.parentId=:parentId and id in (select op.taskId from OrderPlanTaskDependency op ) order by o.plannedFinishDate desc",
                new String[] {"parentId"},
                new Object[] {parentPlanTask.getId()});
        
        entity.setPlannedStartDate(DateUtils.addDays(Collections3.getFirst(nodePlanTasks).getPlannedFinishDate(), 1));
        entity.setActived(false);
        entity.setFinished(false);
        entity.setCanceled(false);
        entity.setGrouped(true);
        baseDaoSupport.insert(entity);
        
        // 细化至订单-产品-样本-检测方法-检测节点
        
        List<String> nodes = getScheduleNodes(schedule);
        Map<String, ScheduleTestingNodeConfig> configs = getVerifyNodeConfigs(schedule.getMethodId());
        
        TaskConfig config;
        OrderPlanTask nodePlanTask;
        OrderPlanTask lastNodePlanTask = null;
        OrderPlanTaskDependency dependency;
        ScheduleTestingNodeConfig scheduleTestingNodeConfig;
        
        // 验证流程过滤掉默认做完的DNA任务
        List<String> newNodes = new ArrayList<>();
        List<TestingScheduleActive> actives =
            baseDaoSupport.findByNamedParam(TestingScheduleActive.class,
                "from TestingScheduleActive t where t.schedule.id=:scheduleId",
                new String[] {"scheduleId"},
                new Object[] {schedule.getId()});
        for (TestingScheduleActive active : actives)
        {
            TestingTask task = baseDaoSupport.get(TestingTask.class, active.getTaskId());
            //2017.12.5 引物设计没存在流程节点中
            if (!"PRIMER-DESIGN".equals(task.getSemantic()) && !"PCR-NGS-PRIMER-DESIGN".equals(task.getSemantic()))
            {
                if (nodes.contains(task.getSemantic()))
                {
                    int index = nodes.indexOf(task.getSemantic());
                    newNodes = nodes.subList(index, nodes.size());
                }
            }
            else
            {
                //如果是验证样本，不去掉dna提取或dna质检；其他去掉
                OrderSampleView sampleView = getOrderSampleViewById(schedule.getSampleId());
                if (null != sampleView.getPurpose())
                {
                    //验证
                    if (1 == sampleView.getPurpose())
                    {
                        newNodes = nodes;
                    }
                    else
                    {
                        if (nodes.contains("DNA-QC"))
                        {
                            int index = nodes.indexOf("DNA-QC");
                            newNodes = nodes.subList(index + 1, nodes.size());
                        }
                        else if (nodes.contains("CDNA-QC"))
                        {
                            int index = nodes.indexOf("CDNA-QC");
                            newNodes = nodes.subList(index + 1, nodes.size());
                        }
                        else
                        {
                            newNodes = nodes;
                        }
                    }
                }
                else
                {
                    if (nodes.contains("DNA-QC"))
                    {
                        int index = nodes.indexOf("DNA-QC");
                        newNodes = nodes.subList(index + 1, nodes.size());
                    }
                    else if (nodes.contains("CDNA-QC"))
                    {
                        int index = nodes.indexOf("CDNA-QC");
                        newNodes = nodes.subList(index + 1, nodes.size());
                    }
                    else
                    {
                        newNodes = nodes;
                    }
                }
            }
        }
        
        for (String node : newNodes)
        {
            config = taskConfigService.getTaskConfig(node);
            nodePlanTask = new OrderPlanTask();
            nodePlanTask.setParentId(entity.getId());
            nodePlanTask.setTaskSemantic(node);
            nodePlanTask.setTaskName(null == config ? "未知任务节点" : config.getName());
            nodePlanTask.setOrderId(schedule.getOrderId());
            nodePlanTask.setProductId(schedule.getProductId());
            nodePlanTask.setSampleId(schedule.getSampleId());
            nodePlanTask.setVerifyId(schedule.getVerifyKey());
            nodePlanTask.setTestingMethodId(schedule.getMethodId());
            nodePlanTask.setActived(false);
            nodePlanTask.setFinished(false);
            nodePlanTask.setCanceled(false);
            nodePlanTask.setGrouped(false);
            baseDaoSupport.insert(nodePlanTask);
            
            Date taskPlannedFinishDate = null;
            // 设置依赖
            if (null != lastNodePlanTask)
            {
                dependency = new OrderPlanTaskDependency();
                dependency.setTaskId(nodePlanTask.getId());
                dependency.setDependencyTaskId(lastNodePlanTask.getId());
                baseDaoSupport.insert(dependency);
                
                if (null != lastNodePlanTask.getPlannedFinishDate())
                {
                    nodePlanTask.setPlannedStartDate(DateUtils.addDays(lastNodePlanTask.getPlannedFinishDate(), 1));
                    scheduleTestingNodeConfig = configs.get(node);
                    
                    if (null != scheduleTestingNodeConfig && null != scheduleTestingNodeConfig.getThreshold())
                    {
                        Date plannedFinishDate = DateUtils.addDays(nodePlanTask.getPlannedStartDate(), scheduleTestingNodeConfig.getThreshold() - 1);
                        nodePlanTask.setPlannedFinishDate(plannedFinishDate);
                        taskPlannedFinishDate = plannedFinishDate;
                    }
                }
            }
            else
            {
                nodePlanTask.setPlannedStartDate(entity.getPlannedStartDate());
                scheduleTestingNodeConfig = configs.get(node);
                
                if (null != scheduleTestingNodeConfig && null != scheduleTestingNodeConfig.getThreshold())
                {
                    Date plannedFinishDate = DateUtils.addDays(nodePlanTask.getPlannedStartDate(), scheduleTestingNodeConfig.getThreshold() - 1);
                    nodePlanTask.setPlannedFinishDate(plannedFinishDate);
                    taskPlannedFinishDate = plannedFinishDate;
                }
            }
            
            lastNodePlanTask = nodePlanTask;
            //4个验证的第一步
            if ("QPCR".equals(node) || "PCR-ONE".equals(node) || "PCR-NGS".equals(node) || "MLPA".equals(node))
            {
                List<TestingTask> taskList = getActiveTaskForVerifyIds(node, schedule.getId());
                if (Collections3.isNotEmpty(taskList))
                {
                    for (TestingTask task : taskList)
                    {
                        task.setPlannedFinishDate(taskPlannedFinishDate);
                        baseDaoSupport.update(task);
                    }
                }
            }
        }
        
        entity.setPlannedFinishDate(lastNodePlanTask.getPlannedFinishDate());
        baseDaoSupport.update(entity);
        // 比较最后一个节点完成时间和组完成时间
        if (entity.getPlannedFinishDate().after(parentPlanTask.getPlannedFinishDate()))
        {
            
            // 更新所有父节点的计划完成时间
            updateParentPlanFinishDate(entity);
        }
        
        return entity;
    }
    
    private OrderSampleView getOrderSampleViewById(String sampleId)
    {
        ReceivedSample rSample = baseDaoSupport.get(ReceivedSample.class, sampleId);
        if (null == rSample)
        {
            TestingSample tSample = baseDaoSupport.get(TestingSample.class, sampleId);
            if (null != tSample)
            {
                rSample = tSample.getReceivedSample();
            }
        }
        List<OrderSampleView> list = baseDaoSupport.find(OrderSampleView.class, "FROM OrderSampleView o where o.sampleId = '" + rSample.getId() + "'");
        if (Collections3.isNotEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }
    
    private void updateParentPlanFinishDate(OrderPlanTask entity)
    {
        if (StringUtils.isNotEmpty(entity.getParentId()))
        {
            OrderPlanTask parentTask = baseDaoSupport.get(OrderPlanTask.class, entity.getParentId());
            if (entity.getPlannedFinishDate().after(parentTask.getPlannedFinishDate()))
            {
                parentTask.setPlannedFinishDate(entity.getPlannedFinishDate());
                baseDaoSupport.update(parentTask);
                updateParentPlanFinishDate(parentTask);
            }
        }
        else
        {// 没有父节点，更新依赖报告计划完成时间
            updateDependencyPlanTask(entity);
            
        }
        
    }
    
    private void updateDependencyPlanTask(OrderPlanTask entity)
    {
        log.info(" ***updateDependencyPlanTask dependencyTaskId*** :" + entity.getId());
        List<OrderPlanTask> list =
            baseDaoSupport.find(OrderPlanTask.class,
                "from OrderPlanTask o where o.id = (select op.taskId from OrderPlanTaskDependency op where op.dependencyTaskId='" + entity.getId() + "') ");
        log.info("updateDependencyPlanTask list:" + list);
        if (Collections3.isNotEmpty(list))
        {
            OrderPlanTask depTask = Collections3.getFirst(list);
            if (!entity.getPlannedFinishDate().before(depTask.getPlannedStartDate()))
            {
                int days = (int)((depTask.getPlannedFinishDate().getTime() - depTask.getPlannedStartDate().getTime()) / (1000 * 60 * 60 * 24));
                depTask.setPlannedStartDate(DateUtils.addDays(entity.getPlannedFinishDate(), 1));
                depTask.setPlannedFinishDate(DateUtils.addDays(depTask.getPlannedStartDate(), days));
                baseDaoSupport.update(depTask);
                updateDependencyPlanTask(depTask);
            }
        }
        else
        {
            // 更新订单计划完成时间   ww 没有依赖说明是最后节点（报告寄送）
            List<OrderScheduleMonitor> monitors =
                baseDaoSupport.find(OrderScheduleMonitor.class, "from OrderScheduleMonitor o where o.orderId='" + entity.getOrderId() + "'");
            if (Collections3.isNotEmpty(monitors))
            {
                Order order = baseDaoSupport.get(Order.class, entity.getOrderId());
                int days = (int)((entity.getPlannedFinishDate().getTime() - order.getSubmitTime().getTime()) / (1000 * 60 * 60 * 24));
                OrderScheduleMonitor monitor = Collections3.getFirst(monitors);
                monitor.setPlannedFinishDate(entity.getPlannedFinishDate());
                monitor.setPlannedDuration(days);
                baseDaoSupport.update(monitor);
            }
        }
    }
    
    private List<String> getScheduleNodes(TestingSchedule schedule)
    {
        List<String> nodes = new ArrayList<String>();
        
        if (!StringUtils.isEmpty(schedule.getScheduleNodes()))
        {
            nodes.addAll(Arrays.asList(schedule.getScheduleNodes().split("\\/")));
        }
        
        return nodes;
    }
    
    private Map<String, ScheduleTestingNodeConfig> getProductMethodNodeConfigs(String productId, String methodId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM ProductTestingMethod ptm WHERE ptm.productId = :productId AND ptm.methodId = :methodId")
                .names(Arrays.asList("productId", "methodId"))
                .values(Arrays.asList(productId, methodId))
                .build();
        
        List<ProductTestingMethod> records = baseDaoSupport.find(queryer, ProductTestingMethod.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            //PS:家属验证样本无产品配置-方法,获取家属DNA-EXT/QC定义的周期
            return getFamilyVerityMethodConfig();
            // return Collections.emptyMap();
        }
        else
        {
            ProductTestingMethod record = records.get(0);
            
            if (null == record.getScheduleConfigId())
            {
                return Collections.emptyMap();
            }
            else
            {
                return configService.getScheduleTestingNodeConfig(record.getScheduleConfigId());
            }
        }
    }
    
    private Map<String, ScheduleTestingNodeConfig> getFamilyVerityMethodConfig()
    {
        NamedQueryer queryer =
            NamedQueryer.builder().hql("FROM TestingMethod m WHERE m.type =:type  ").names(Arrays.asList("type")).values(Arrays.asList("3")).build();
        List<TestingMethod> records = baseDaoSupport.find(queryer, TestingMethod.class);
        if (Collections3.isNotEmpty(records))
        {
            String familyVerityMethodId = Collections3.getFirst(records).getId();
            return getVerifyNodeConfigs(familyVerityMethodId);
        }
        return Collections.emptyMap();
    }
    
    private Map<String, ScheduleTestingNodeConfig> getVerifyNodeConfigs(String methodId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM ScheduleTestingConfig STC WHERE STC.testingMethodId=:methodId")
                .names(Arrays.asList("methodId"))
                .values(Arrays.asList(methodId))
                .build();
        
        List<ScheduleTestingConfig> records = baseDaoSupport.find(queryer, ScheduleTestingConfig.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyMap();
        }
        else
        {
            ScheduleTestingConfig record = records.get(0);
            
            if (null == record)
            {
                return Collections.emptyMap();
            }
            else
            {
                return configService.getScheduleTestingNodeConfig(record.getId());
            }
        }
    }
    
    private OrderPlanTask getOrderProductGroupPlanTask(String orderId, String productId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.taskSemantic = :productGroupSemantic AND opt.orderId = :orderId AND opt.productId = :productId AND opt.grouped = true ")
                .names(Arrays.asList("orderId", "productId", "productGroupSemantic"))
                .values(Arrays.asList(orderId, productId, OrderPlanTask.SEMANTIC_PRODUCT_GROUP))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    private OrderPlanTask getOrderProductSampleMethodPlanTask(String orderId, String productId, String sampleId, String methodId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.taskSemantic = 'GROUP_PRODUCT_SAMPLE_METHOD' AND opt.orderId = :orderId AND opt.productId = :productId AND opt.testingMethodId =:methodId and "
                    + " opt.sampleId=:sampleId ")
                .names(Arrays.asList("orderId", "productId", "methodId", "sampleId"))
                .values(Arrays.asList(orderId, productId, methodId, sampleId))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    private boolean isOrderProductSampleSchedulePlanned(String orderId, String productId, String sampleId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT (*) FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.productId = :productId AND opt.sampleId = :sampleId")
                .names(Arrays.asList("orderId", "productId", "sampleId"))
                .values(Arrays.asList(orderId, productId, sampleId))
                .build();
        return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
    }
    
    private boolean isGroupCompleteSchedulePlanned(String parentId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT (*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.finished=false ")
                .names(Arrays.asList("parentId"))
                .values(Arrays.asList(parentId))
                .build();
        return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
    }
    
    //组任务是已完成或者已取消   2017.10.29
    private boolean isGroupCompleteSchedulePlannedOrCancel(String parentId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT (*) FROM OrderPlanTask opt WHERE opt.parentId=:parentId AND opt.finished=false and opt.canceled=false")
                .names(Arrays.asList("parentId"))
                .values(Arrays.asList(parentId))
                .build();
        return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
    }
    
    private OrderPlanTask toOrderPlanTask(String orderId, PlanTask task)
    {
        if (null == task)
        {
            return null;
        }
        
        String semantic = task.getSemantic();
        
        if (semantic.contains("#"))
        {
            semantic = semantic.substring(0, semantic.indexOf("#"));
        }
        
        OrderPlanTask entity = new OrderPlanTask();
        entity.setOrderId(orderId);
        entity.setTaskSemantic(semantic);
        entity.setTaskName(task.getName());
        entity.setPlannedStartDate(task.getPlannedStartDate());
        entity.setPlannedFinishDate(task.getPlannedFinishDate());
        entity.setActualStartDate(task.getActualStartDate());
        entity.setActived(task.isActived());
        entity.setFinished(false);
        entity.setCanceled(false);
        entity.setGrouped(task.isGrouped());
        entity.setPostponed(false);
        entity.setPostponedDays(0);
        
        if (task instanceof TestingPlanTask)
        {
            TestingPlanTask testingPlanTask = (TestingPlanTask)task;
            entity.setProductId(testingPlanTask.getProductId());
        }
        
        return entity;
    }
    
    @Override
    @Transactional
    public void updatePlanForMonitorJob()
    {
        // 1、查询所有监控中、未开始/已延期的非组任务
        List<OrderPlanTask> tasks = getUngroupedPostponedTasks();
        
        Date today = new Date();
        
        if (CollectionUtils.isEmpty(tasks))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Found no postponed plan tasks, ignore the monitor job for {}", today);
            }
            
            return;
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Found {} postponed plan tasks, start to setup the postponed details.", tasks.size());
        }
        
        Set<String> orders = updatePlanForPostponedTasks(tasks);
        
        if (orders.isEmpty())
        {
            return;
        }
        
        if (log.isDebugEnabled())
        {
            log.debug("Found {} orders from order plan task, start to update order monitors.", orders.size());
        }
        // 更新订单延迟状态
        for (String orderId : orders)
        {
            updateOrderScheduleMonitorDetails(orderId);
        }
        log.debug("Finished {} order schedule moniters, end update for the order schedule moniter.", orders.size());
    }
    
    private void updateOrderScheduleMonitorDetails(String orderId)
    {
        OrderScheduleMonitor monitor = baseDaoSupport.get(OrderScheduleMonitor.class, orderId);
        
        if (null == monitor)
        {
            return;
        }
        
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT MAX(opt.postponedDays) FROM OrderPlanTask opt WHERE opt.orderId = :orderId")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderId))
                .build();
        
        int maxPostponedDays = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        
        if (maxPostponedDays > 0)
        {
            monitor.setPostponed(true);
            monitor.setPostponedDays(maxPostponedDays);
            baseDaoSupport.update(monitor);
        }
    }
    
    private Set<String> updatePlanForPostponedTasks(List<OrderPlanTask> tasks)
    {
        Date today = new Date();
        Set<String> parents = new HashSet<>();
        Set<String> orders = new HashSet<String>();
        
        for (OrderPlanTask task : tasks)
        {
            task.setPostponed(true);
            
            if (!task.isStarted())
            {
                task.setPostponedDays(getDays(task.getPlannedStartDate(), today));
            }
            else
            {
                task.setPostponedDays(getDays(task.getPlannedFinishDate(), today));
            }
            
            baseDaoSupport.update(task);
            
            if (null != task.getParentId())
            {
                parents.add(task.getParentId());
            }
            
            orders.add(task.getOrderId());
        }
        
        if (CollectionUtils.isEmpty(parents))
        {
            if (log.isDebugEnabled())
            {
                log.debug("No parent plan task postponed for this batch, ignore update parent tasks.");
            }
            
            return orders;
        }
        
        List<OrderPlanTask> groupPostponedTasks = getTasksByKeys(parents);
        updatePlanForPostponedGroupTasks(groupPostponedTasks);
        
        return orders;
    }
    
    private void updatePlanForPostponedGroupTasks(List<OrderPlanTask> tasks)
    {
        Set<String> parents = new HashSet<>();
        
        for (OrderPlanTask task : tasks)
        {
            updatePlanForPostponedGroupTask(task);
            
            if (null != task.getParentId())
            {
                parents.add(task.getParentId());
            }
        }
        
        if (CollectionUtils.isEmpty(parents))
        {
            if (log.isDebugEnabled())
            {
                log.debug("No parent plan task postponed for this batch, ignore update parent tasks.");
            }
            
            return;
        }
        
        List<OrderPlanTask> groupPostponedTasks = getTasksByKeys(parents);
        updatePlanForPostponedGroupTasks(groupPostponedTasks);
    }
    
    private void updatePlanForPostponedGroupTask(OrderPlanTask task)
    {
        /*List<OrderPlanTask> subtasks = getPostponedTasksForGroupTask(task.getId());
        
        if (CollectionUtils.isEmpty(subtasks))
        {
            return;
        }
        
        int postponedDays = 0;
        
        for (OrderPlanTask subtask : subtasks)
        {
            postponedDays = Math.max(postponedDays, subtask.getPostponedDays());
        }*/
        
        task.setPostponed(true);
        task.setPostponedDays(getPostponedTasksForGroupTask(task.getId()));
        baseDaoSupport.update(task);
    }
    
    private List<OrderPlanTask> getUngroupedPostponedTasks()
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.actived = true AND opt.grouped = false AND (opt.started = false OR opt.plannedFinishDate < :today)")
                .names(Arrays.asList("today"))
                .values(Arrays.asList(DateUtils.truncate(new Date(), Calendar.DATE)))
                .build();
        return baseDaoSupport.find(queryer, OrderPlanTask.class);
    }
    
    private List<OrderPlanTask> getTasksByKeys(Set<String> keys)
    {
        /*NamedQueryer queryer =
            NamedQueryer.builder().hql("FROM OrderPlanTask opt WHERE opt.id IN :keys").names(Arrays.asList("keys")).values(Arrays.asList(keys)).build();
        return baseDaoSupport.find(queryer, OrderPlanTask.class);*/
        List<OrderPlanTask> list = new ArrayList<OrderPlanTask>();
        for (String s : keys)
        {
            list.add(baseDaoSupport.get(OrderPlanTask.class, s));
        }
        return list;
        
    }
    
    /*private List<OrderPlanTask> getPostponedTasksForGroupTask(String taskId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.actived = true AND opt.postponed = true AND opt.parentId = :parentId")
                .names(Arrays.asList("parentId"))
                .values(Arrays.asList(taskId))
                .build();
        return baseDaoSupport.find(queryer, OrderPlanTask.class);
    }*/
    
    private int getPostponedTasksForGroupTask(String taskId)
    {
        String hql = "SELECT max(opt.postponedDays) FROM OrderPlanTask opt WHERE opt.parentId = :parentId AND opt.actived = true AND opt.postponed = true";
        List<Number> list = baseDaoSupport.findByNamedParam(Number.class, hql, new String[] {"parentId"}, new Object[] {taskId});
        if (null != list.get(0))
        {
            return list.get(0).intValue();
        }
        return 0;
    }
    
    @Override
    @Transactional
    public void updatePlanFinish(String id, String semantic, String testingId)
    {
        // 完成当前
        OrderPlanTask orderPlanTask = configService.getPlanTask(id, semantic, testingId);
        orderPlanTask.setActualFinishDate(new Date());
        orderPlanTask.setActived(false);
        orderPlanTask.setFinished(true);
        baseDaoSupport.update(orderPlanTask);
        // 判断下个是否激活
        List<OrderPlanTaskDependency> depList = configService.getDependencyByDependencyId(orderPlanTask.getId());
        if (Collections3.isNotEmpty(depList))
        {
            for (OrderPlanTaskDependency optd : depList)
            {
                List<OrderPlanTaskDependency> depListTwo = configService.getDependencyByTaskId(optd.getTaskId());
                if (Collections3.isNotEmpty(depListTwo))
                {
                    if (isFinshed(depListTwo))// 依赖都完成 激活
                    {
                        OrderPlanTask planTask = configService.getPlanTaskById(optd.getTaskId());
                        planTask.setActived(true);
                        planTask.setPlannedStartDate(orderPlanTask.getPlannedFinishDate());
                        ScheduleGlobalConfig globalConfig = configService.getGlobalConfig(planTask.getTaskSemantic());
                        if (null != globalConfig)
                        {
                            int threshold = globalConfig.getThreshold().intValue();
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(planTask.getPlannedStartDate());
                            calendar.add(Calendar.DATE, threshold);// 把日期往后增加
                            planTask.setPlannedFinishDate(calendar.getTime());
                        }
                        baseDaoSupport.update(planTask);
                    }
                }
            }
        }
    }
    
    private boolean isFinshed(List<OrderPlanTaskDependency> depListTwo)
    {
        for (OrderPlanTaskDependency optd2 : depListTwo)
        {
            OrderPlanTask planTask = configService.getPlanTaskById(optd2.getDependencyTaskId());
            if (!planTask.isFinished())
            {
                return false;
            }
            
        }
        return true;
    }
    
    public TestingVerifyRecord getVerifyRecordByVerifyKey(String verifyKey)
    {
        
        TestingVerifyRecord record;
        SangerVerifyRecord sangerverifyrecord = baseDaoSupport.get(SangerVerifyRecord.class, verifyKey);
        if (null != sangerverifyrecord)
        {
            record = sangerverifyrecord.getVerifyRecord();
            
            return record;
        }
        MlpaVerifyRecord mlpaverifyrecord = baseDaoSupport.get(MlpaVerifyRecord.class, verifyKey);
        if (null != mlpaverifyrecord)
        {
            record = mlpaverifyrecord.getVerifyRecord();
            
            return record;
        }
        QpcrVerifyRecord qpcrverifyrecord = baseDaoSupport.get(QpcrVerifyRecord.class, verifyKey);
        if (null != qpcrverifyrecord)
        {
            record = qpcrverifyrecord.getVerifyRecord();
            
            return record;
        }
        return null;
    }
    
    private int getDays(Date date, Date base)
    {
        if (null == date || null == base)
        {
            return 0;
        }
        
        date = DateUtils.truncate(date, Calendar.DATE);
        base = DateUtils.truncate(base, Calendar.DATE);
        int days = (int)((base.getTime() - date.getTime()) / (1000 * 3600 * 24));
        return days;
    }
    
    @Override
    @Transactional
    public void updateVerifyTestingStart(String scheduleId)
    {
        
        TestingSchedule schedule = baseDaoSupport.get(TestingSchedule.class, scheduleId);
        if (StringUtils.isNotEmpty(schedule) && StringUtils.isNotEmpty(schedule.getVerifyKey()))
        
        {
            List<OrderPlanTask> planTasks =
                baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                    "from OrderPlanTask t where t.verifyId=:verifyId and t.grouped = 1",
                    new String[] {"verifyId"},
                    new Object[] {schedule.getVerifyKey()});
            if (Collections3.isNotEmpty(planTasks))
            {
                // 激活改验证计划
                startTask(Collections3.getFirst(planTasks));
                // 同时激活父节点
                startVerifyTask(Collections3.getFirst(planTasks));
            }
            
        }
        else
        {
            log.error("updateVerifyTestingStart method according scheduleId" + scheduleId + "not find object!");
        }
        
    }
    
    private void startVerifyTask(OrderPlanTask task)
    {
        
        OrderPlanTask parentTask = null;
        if (null != task.getParentId())
        {
            parentTask = baseDaoSupport.get(OrderPlanTask.class, task.getParentId());
        }
        
        if (null != parentTask && parentTask.isGrouped())
        {
            parentTask.setActived(true);
            parentTask.setStarted(true);
            
            baseDaoSupport.update(parentTask);
            
            startVerifyTask(parentTask);
            
        }
        
    }
    
    @Override
    public boolean getIsFinished(String orderId, String semantic)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.taskSemantic = :semantic")
                .names(Arrays.asList("orderId", "semantic"))
                .values(Arrays.asList(orderId, semantic))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        return Collections3.isNotEmpty(records) ? records.get(0).isFinished() : false;
    }
    
    private List<TestingTask> getActiveTaskIds(String semantic, String orderId, String productId, String methodId, String sampleId)
    {
        String hql =
            " FROM TestingTask t WHERE t.semantic=:semantic AND EXISTS (select sa.taskId FROM TestingScheduleActive sa WHERE sa.taskId=t.id AND sa.schedule.orderId=:orderId "
                + " and sa.schedule.productId=:productId and sa.schedule.methodId=:methodId and sa.schedule.sampleId=:sampleId ) ";
        //        String hql =
        //            "select sa.task from TestingScheduleActive sa where sa.task.semantic=:semantic and sa.schedule.orderId=:orderId "
        //                + " and sa.schedule.productId=:productId and sa.schedule.methodId=:methodId and sa.schedule.sampleId=:sampleId";
        List<TestingTask> taskList =
            baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[] {"semantic", "orderId", "productId", "methodId", "sampleId"}, new Object[] {
                semantic, orderId, productId, methodId, sampleId});
        return taskList;
    }
    
    private List<TestingTask> getActiveTaskForVerifyIds(String semantic, String scheduleId)
    {
        String hql =
            " FROM TestingTask t WHERE t.semantic=:semantic AND EXISTS (select sa.taskId FROM TestingScheduleActive sa WHERE sa.taskId=t.id AND sa.schedule.id=:scheduleId )";
        //        String hql = "select sa.task from TestingScheduleActive sa where sa.task.semantic=:semantic and sa.schedule.id=:scheduleId ";
        List<TestingTask> taskList =
            baseDaoSupport.findByNamedParam(TestingTask.class, hql, new String[] {"semantic", "scheduleId"}, new Object[] {semantic, scheduleId});
        return taskList;
    }
    
    @Override
    //更新任务已经创建但是consumer还没来得及创建计划，导致任务的计划完成时间未空的情况
    /*
             根据订单id查询schedule得到id
            根据schedule的id查询schedule_history（根据schedule_id）得到所有的task_id
            根据task_id查询task(根据id)
            判断task的计划完成时间是否有值，如果没有，将schedule的orderId，productId，SampleId，MethodId，verifyId，sematic作为key，task_id作为value，放到map中
            
            根据订单id和sample_id==null得到所有的plan
            根据plan的orderId，productId，SampleId，MethodId，verifyId，sematic查找map看值是否存在，
            如果存在，拿到task_id，根据task_id更新task的计划完成时间
     */
    @Transactional
    public void updateTaskPlannedFinishDate(String orderId)
    {
        String hql = "from TestingSchedule t where t.orderId=:orderId";
        List<TestingSchedule> testingSchedules = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId"}, new Object[] {orderId});
        processSchedules(orderId, testingSchedules);
    }
    
    private void processSchedules(String orderId, List<TestingSchedule> testingSchedules)
    {
        Map<String, TestingTask> scheduleSemanticToTestingTask = new HashMap<String, TestingTask>();
        if (Collections3.isNotEmpty(testingSchedules))
        {
            testingSchedules.stream().forEach(x -> {
                String hql2 = "from TestingScheduleHistory t where t.scheduleId=:scheduleId";
                List<TestingScheduleHistory> scheduleHistorys =
                    baseDaoSupport.findByNamedParam(TestingScheduleHistory.class, hql2, new String[] {"scheduleId"}, new Object[] {x.getId()});
                scheduleHistorys.stream().forEach(y -> {
                    TestingTask task = baseDaoSupport.get(TestingTask.class, y.getTaskId());
                    if (task != null && task.getPlannedFinishDate() == null)
                    {
                        scheduleSemanticToTestingTask.put(x.getOrderId() + "_" + x.getSampleId() + "_" + x.getProductId() + "_" + x.getMethodId() + "_"
                            + x.getVerifyKey() + "_" + task.getSemantic(),
                            task);
                    }
                });
            });
        }
        
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.sampleId is not null")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderId))
                .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        records.stream().forEach(x -> {
            TestingTask task =
                scheduleSemanticToTestingTask.get(x.getOrderId() + "_" + x.getSampleId() + "_" + x.getProductId() + "_" + x.getTestingMethodId() + "_"
                    + x.getVerifyId() + "_" + x.getTaskSemantic());
            if (task != null)
            {
                log.info("更新计划完成时间,TestingTask.id：" + task.getId());
                task.setPlannedFinishDate(x.getPlannedFinishDate());
                baseDaoSupport.update(task);
            }
        });
    }
    
    @Override
    @Transactional
    public void updateTaskPlannedFinishDate(List<String> scheduleIds)
    {
        String hql = "from TestingSchedule t where t.id in (:scheduleIds)";
        List<TestingSchedule> testingSchedules =
            baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"scheduleIds"}, new Object[] {scheduleIds});
        log.info("testingSchedulesL" + testingSchedules);
        if (Collections3.isNotEmpty(testingSchedules))
        {
            String orderId = testingSchedules.get(0).getOrderId();
            processSchedules(orderId, testingSchedules);
        }
    }
    
}
