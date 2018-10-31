package com.todaysoft.lims.schedule.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.schedule.entity.Order;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.entity.OrderScheduleMonitor;
import com.todaysoft.lims.schedule.entity.TestingSchedule;
import com.todaysoft.lims.schedule.entity.TestingScheduleHistory;
import com.todaysoft.lims.schedule.entity.TestingTask;
import com.todaysoft.lims.schedule.response.OrderPostponedDetails;
import com.todaysoft.lims.schedule.response.OrderPostponedNode;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorPlanTask;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorResponse;
import com.todaysoft.lims.schedule.service.IOrderScheduleMonitorService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class OrderScheduleMonitorService implements IOrderScheduleMonitorService
{
    private static Logger log = LoggerFactory.getLogger(OrderScheduleMonitorService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public OrderScheduleMonitorResponse getOrderScheduleMonitorDetails(String orderId)
    {
        Order order = baseDaoSupport.get(Order.class, orderId);
        
        if (null == order)
        {
            log.error("Can not found order for schedule monitor details by id {}", orderId);
            return null;
        }
        
        // 订单基本信息
        OrderScheduleMonitorResponse response = new OrderScheduleMonitorResponse();
        response.setId(order.getId());
        response.setOrderCode(order.getCode());
        response.setSubmitTime(order.getSubmitTime());
        response.setStatus(Integer.valueOf(order.getTestingStatus()));
        
        // 订单监控信息
        OrderScheduleMonitor monitor = baseDaoSupport.get(OrderScheduleMonitor.class, orderId);
        
        if (null != monitor)
        {
            response.setPlannedDuration(monitor.getPlannedDuration());
            response.setPlannedFinishDate(monitor.getPlannedFinishDate());
            response.setActualFinishDate(monitor.getActualFinishDate());
        }
        
        // 订单执行计划-未细化的
        List<OrderPlanTask> records = getRootPlanTasks(orderId);
        
        if (!CollectionUtils.isEmpty(records))
        {
            List<OrderScheduleMonitorPlanTask> tasks = new ArrayList<OrderScheduleMonitorPlanTask>();
            
            for (OrderPlanTask record : records)
            {
                tasks.add(wrap(record));
            }
            
            response.setTasks(tasks);
        }
        
        return response;
    }
    
    @Override
    public List<OrderScheduleMonitorPlanTask> getGroupPlanTasks(String groupId)
    {
        NamedQueryer queryer = NamedQueryer.builder()
            .hql("FROM OrderPlanTask opt WHERE opt.parentId = :parentId ORDER BY opt.plannedStartDate, opt.plannedFinishDate")
            .names(Arrays.asList("parentId"))
            .values(Arrays.asList(groupId))
            .build();
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        if (records.size() == 1)
        {
            OrderPlanTask record = records.get(0);
            
            if (record.isGrouped())
            {
                return getGroupPlanTasks(record.getId());
            }
            else
            {
                return Collections.singletonList(wrap(record));
            }
        }
        else
        {
            List<OrderScheduleMonitorPlanTask> tasks = new ArrayList<OrderScheduleMonitorPlanTask>();
            
            if (!CollectionUtils.isEmpty(records))
            {
                for (OrderPlanTask record : records)
                {
                    tasks.add(wrap(record));
                }
            }
            
            return tasks;
        }
    }
    
    private List<OrderPlanTask> getRootPlanTasks(String orderId)
    {
        NamedQueryer queryer = NamedQueryer.builder()
            .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.parentId IS NULL ORDER BY opt.productId, opt.plannedStartDate, opt.plannedFinishDate")
            .names(Arrays.asList("orderId"))
            .values(Arrays.asList(orderId))
            .build();
        return baseDaoSupport.find(queryer, OrderPlanTask.class);
    }
    
    private OrderScheduleMonitorPlanTask wrap(OrderPlanTask record)
    {
        if (null == record)
        {
            return null;
        }
        
        OrderScheduleMonitorPlanTask task = new OrderScheduleMonitorPlanTask();
        task.setId(record.getId());
        task.setName(record.getTaskName());
        task.setPlannedStartDate(record.getPlannedStartDate());
        task.setPlannedFinishDate(record.getPlannedFinishDate());
        task.setActualStartDate(record.getActualStartDate());
        task.setActualFinishDate(record.getActualFinishDate());
        task.setActived(record.isActived());
        task.setStarted(record.isStarted());
        task.setFinished(record.isFinished());
        task.setCanceled(record.isCanceled());
        task.setGrouped(record.isGrouped());
        task.setPostponed(record.isPostponed());
        task.setPostponedDays(record.getPostponedDays());
        // 异常次数
        String hql = "FROM TestingSchedule t where t.orderId= :orderId ";
        List<String> names = Lists.newArrayList();
        List<Object> values = Lists.newArrayList();
        names.add("orderId");
        values.add(record.getOrderId());
        if("GROUP_PRODUCT_SAMPLE_METHOD_VERIFY".equals(record.getTaskSemantic()))
        {
            hql += " AND t.productId = :productId AND t.methodId = :methodId AND t.sampleId = :sampleId AND t.verifyKey = :verifyKey ";
            names.addAll(Arrays.asList("productId", "methodId", "sampleId","verifyKey"));
            values.addAll(Arrays.asList(record.getProductId(), record.getTestingMethodId(), record.getSampleId(), record.getVerifyId()));
            NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(names).values(values).build();
            List<TestingSchedule> verifyScheduleList = baseDaoSupport.find(queryer, TestingSchedule.class);
            task.setAbnormalCount(sumAbnormalCount(verifyScheduleList));
        }
        else if ("GROUP_PRODUCT".equals(record.getTaskSemantic()))
        {
            hql += " AND t.productId=:productId";
            names.add("productId");
            values.add(record.getProductId());
            NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(names).values(values).build();
            List<TestingSchedule> scheduleList = baseDaoSupport.find(queryer, TestingSchedule.class);
            task.setAbnormalCount(sumAbnormalCount(scheduleList));
        }
        else if ("GROUP_PRODUCT_SAMPLE".equals(record.getTaskSemantic()))
        {
            hql += " AND t.productId = :productId AND t.sampleId = :sampleId";
            names.addAll(Arrays.asList("productId", "sampleId"));
            values.addAll(Arrays.asList(record.getProductId(), record.getSampleId()));
            NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(names).values(values).build();
            List<TestingSchedule> scheduleList = baseDaoSupport.find(queryer, TestingSchedule.class);
            int sum = sumAbnormalCount(scheduleList);
            List<TestingSchedule> verifyList = getScheduleForSampleAndVerify(scheduleList);
            int verifySum = sumAbnormalCount(verifyList);
            task.setAbnormalCount(sum+verifySum);
        }
        else if ("GROUP_PRODUCT_SAMPLE_METHOD".equals(record.getTaskSemantic()))
        {
            hql += " AND t.productId = :productId AND t.methodId = :methodId AND t.sampleId = :sampleId ";
            names.addAll(Arrays.asList("productId", "methodId", "sampleId"));
            values.addAll(Arrays.asList(record.getProductId(), record.getTestingMethodId(), record.getSampleId()));
            NamedQueryer queryer = NamedQueryer.builder().hql(hql).names(names).values(values).build();
            List<TestingSchedule> scheduleList = baseDaoSupport.find(queryer, TestingSchedule.class);
            int sum = sumAbnormalCount(scheduleList);
            List<TestingSchedule> verifyList = getScheduleForSampleAndVerify(scheduleList);
            int verifySum = sumAbnormalCount(verifyList);
            task.setAbnormalCount(sum+verifySum);
        }
        else
        {
            hql += " AND t.productId = :productId AND t.methodId = :methodId AND t.sampleId = :sampleId ";
            names.addAll(Arrays.asList("productId", "methodId", "sampleId"));
            values.addAll(Arrays.asList(record.getProductId(), record.getTestingMethodId(), record.getSampleId()));
            if(StringUtils.isNotEmpty(record.getVerifyId()))
            {
                hql += " AND t.verifyKey = :verifyKey ";
                names.add("verifyKey");
                values.add(record.getVerifyId());
            }
            NamedQueryer query = NamedQueryer.builder().hql(hql).names(names).values(values).build();
            List<TestingSchedule> scheduleList = baseDaoSupport.find(query,TestingSchedule.class);
            if (Collections3.isNotEmpty(scheduleList))
            {
                List<String> paramNames = new ArrayList<String>();
                List<Object> parameters = new ArrayList<Object>();
                String queryHql = "SELECT COUNT (*) FROM TestingScheduleHistory t WHERE EXISTS("
                    + " select tt.id from TestingTask tt where tt.id = t.taskId and tt.status = 3 and tt.endType = 0"
                    + " and tt.semantic = :semantic ) AND t.scheduleId = :scheduleId";
                paramNames.addAll(Arrays.asList("semantic","scheduleId"));
                parameters.addAll(Arrays.asList(record.getTaskSemantic(),scheduleList.get(0).getId()));
                NamedQueryer queryer = new NamedQueryer();
                queryer.setHql(queryHql.toString());
                queryer.setNames(paramNames);
                queryer.setValues(parameters);
                int count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
                task.setAbnormalCount(count);
            }
        }
        
        return task;
    }
    
    // 获取总异常次数
    private int sumAbnormalCount(List<TestingSchedule> scheduleList)
    {
        int sum = 0;
        if (Collections3.isNotEmpty(scheduleList))
        {
            for (TestingSchedule schedule : scheduleList)
            {
                NamedQueryer queryer = NamedQueryer.builder()
                    .hql(
                        "SELECT COUNT (*) FROM TestingScheduleHistory t WHERE EXISTS(select tt.id from TestingTask tt where tt.id = t.taskId and tt.status = 3 and tt.endType = 0) AND t.scheduleId = :scheduleId")
                    .names(Arrays.asList("scheduleId"))
                    .values(Arrays.asList(schedule.getId()))
                    .build();
                int count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
                sum += count;
            }
        }
        return sum;
    }
    
    //获取验证schedule
    private List<TestingSchedule> getScheduleForSampleAndVerify(List<TestingSchedule> scheduleList)
    {
        List<TestingSchedule> list = Lists.newArrayList();
        if (Collections3.isNotEmpty(scheduleList))
        {
            for (TestingSchedule schedule : scheduleList)
            {
                NamedQueryer queryer = NamedQueryer.builder()
                    .hql("from TestingSchedule t where t.verifyTarget=:verifyTarget")
                    .names(Arrays.asList("verifyTarget"))
                    .values(Arrays.asList(schedule.getId()))
                    .build();
                List<TestingSchedule> verifyList  = baseDaoSupport.find(queryer, TestingSchedule.class);
                list.addAll(verifyList);
            }
        }
        return list;
    }
    
    @Override
    public List<OrderPostponedDetails> getOrderPostponedDetails(Set<String> orderIds)
    {
        if (CollectionUtils.isEmpty(orderIds))
        {
            return Collections.emptyList();
        }
        
        NamedQueryer queryer;
        
        if (orderIds.size() == 1)
        {
            queryer = NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId = :orderId AND opt.actived = true AND opt.postponed = true AND opt.grouped = false")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderIds.iterator().next()))
                .build();
        }
        else
        {
            queryer = NamedQueryer.builder()
                .hql("FROM OrderPlanTask opt WHERE opt.orderId IN (:orderIds) AND opt.actived = true AND opt.postponed = true AND opt.grouped = false")
                .names(Arrays.asList("orderIds"))
                .values(Arrays.asList(orderIds))
                .build();
        }
        
        List<OrderPlanTask> records = baseDaoSupport.find(queryer, OrderPlanTask.class);
        
        OrderPostponedNode node;
        OrderPostponedDetails details;
        Map<String, OrderPostponedDetails> context = new HashMap<String, OrderPostponedDetails>();
        
        for (OrderPlanTask record : records)
        {
            details = context.get(record.getOrderId());
            
            if (null == details)
            {
                details = new OrderPostponedDetails();
                details.setOrderId(record.getOrderId());
                details.setPostponedNodes(new ArrayList<OrderPostponedNode>());
                context.put(record.getOrderId(), details);
            }
            
            node = new OrderPostponedNode();
            node.setName(record.getTaskName());
            node.setPostponedDays(null == record.getPostponedDays() ? 0 : record.getPostponedDays());
            details.getPostponedNodes().add(node);
        }
        
        if (CollectionUtils.isEmpty(context))
        {
            return Collections.emptyList();
        }
        
        for (OrderPostponedDetails entryValue : context.values())
        {
            Collections.sort(entryValue.getPostponedNodes(), new Comparator<OrderPostponedNode>()
            {
                @Override
                public int compare(OrderPostponedNode o1, OrderPostponedNode o2)
                {
                    if (o1.getPostponedDays() == o2.getPostponedDays())
                    {
                        return 0;
                    }
                    else
                    {
                        return (o1.getPostponedDays() > o2.getPostponedDays()) ? -1 : 1;
                    }
                }
            });
            
            if (!CollectionUtils.isEmpty(entryValue.getPostponedNodes()))
            {
                entryValue.setPostponedWorstNode(entryValue.getPostponedNodes().get(0));
            }
        }
        
        return new ArrayList<OrderPostponedDetails>(context.values());
    }
    
    @Override
    @Transactional
    public void updateOrderUrgent(String orderId, Integer ifUrgent, String urgentName)
    {
        Order order = baseDaoSupport.get(Order.class, orderId);
        if (0 == ifUrgent)
        {
            ifUrgent = null;
        }
        order.setIfUrgent(ifUrgent);
        order.setUrgentUpdateTime(new Date());
        order.setUrgentName(urgentName);
        baseDaoSupport.update(order);
        
        NamedQueryer queryer = NamedQueryer.builder().hql("FROM TestingSchedule s WHERE s.orderId = :orderId").names(Arrays.asList("orderId")).values(Arrays.asList(orderId)).build();
        List<TestingSchedule> scheduleList = baseDaoSupport.find(queryer, TestingSchedule.class);
        
        if (Collections3.isNotEmpty(scheduleList))
        {
            for (TestingSchedule schedule : scheduleList)
            {
                NamedQueryer queryer2 =
                    NamedQueryer.builder().hql("FROM TestingScheduleHistory s WHERE s.scheduleId = :scheduleId").names(Arrays.asList("scheduleId")).values(Arrays.asList(schedule.getId())).build();
                List<TestingScheduleHistory> historyList = baseDaoSupport.find(queryer2, TestingScheduleHistory.class);
                if (Collections3.isNotEmpty(historyList))
                {
                    for (TestingScheduleHistory scheduleHistory : historyList)
                    {
                        TestingTask task = baseDaoSupport.get(TestingTask.class, scheduleHistory.getTaskId());
                        if(null != task)
                        {
                            task.setIfUrgent(ifUrgent);
                            task.setUrgentUpdateTime(new Date());
                            task.setUrgentName(urgentName);
                            baseDaoSupport.update(task);
                        }
                    }
                }
            }
        }
        
    }
}
