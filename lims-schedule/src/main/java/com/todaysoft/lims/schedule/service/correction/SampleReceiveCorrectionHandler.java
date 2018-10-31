package com.todaysoft.lims.schedule.service.correction;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.schedule.entity.Order;
import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.entity.ScheduleGlobalConfig;
import com.todaysoft.lims.schedule.service.IOrderPlanTaskQueryService;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.IScheduleConfigService;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class SampleReceiveCorrectionHandler extends AbstractCorrectionHandler
{
    private static Logger log = LoggerFactory.getLogger(SampleReceiveCorrectionHandler.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IScheduleConfigService configService;
    
    @Autowired
    private IOrderPlanTaskQueryService orderPlanTaskQueryService;
    
    @Override
    protected boolean isCorrectRequired(ScheduleKeyHolder keyHolder)
    {
        if (StringUtils.isEmpty(keyHolder.getOrderId()))
        {
            log.error("Schedule key holder is invalid, order id is empty, ignore the operation.");
            return false;
        }
        
        return orderService.isOrderPlanned(keyHolder.getOrderId());
    }
    
    @Override
    protected void doCorrect(ScheduleKeyHolder keyHolder)
    {
        String orderId = keyHolder.getOrderId();
        
        OrderPlanTask task = orderPlanTaskQueryService.queryForOrderSemanticRecord(orderId, OrderPlanTask.SEMANTIC_SAMPLE_RECEIVE);
        
        if (null == task)
        {
            log.error("Cannot found the SAMPLE_RECEIVE plan task for order {}.", orderId);
            return;
        }
        
        Order order = orderService.getOrder(orderId);
        
        if (null == order || null == order.getSubmitTime())
        {
            log.error("Cannot found the order by id {}.", orderId);
            throw new IllegalStateException();
        }
        
        planCorrect(order, task);
        actualCorrect(order, task);
    }
    
    // 数据校正-计划开始时间和结束时间的
    private void planCorrect(Order order, OrderPlanTask task)
    {
        if (null != task.getPlannedStartDate() && null != task.getPlannedFinishDate())
        {
            if (log.isDebugEnabled())
            {
                log.debug("Planned start date and finish date is exists, order id {}, planned start date {}, planned finish date {}.",
                    order.getId(),
                    task.getPlannedStartDate(),
                    task.getPlannedFinishDate());
            }
            
            return;
        }
        
        ScheduleGlobalConfig config = configService.getGlobalConfig(OrderPlanTask.SEMANTIC_SAMPLE_RECEIVE);
        
        if (null == config || null == config.getThreshold())
        {
            throw new IllegalStateException();
        }
        
        Date submitTime = order.getSubmitTime();
        Date plannedStartDate = DateUtils.addDays(DateUtils.truncate(submitTime, Calendar.DATE), 1);
        Date plannedFinishDate = DateUtils.addDays(plannedStartDate, config.getThreshold() - 1);
        task.setPlannedStartDate(plannedStartDate);
        task.setPlannedFinishDate(plannedFinishDate);
        baseDaoSupport.update(task);
    }
    
    // 数据校正-实际监控相关属性
    private void actualCorrect(Order order, OrderPlanTask task)
    {
        boolean isOrderSampleConfirmed = isOrderSampleConfirmed(order);
        
        if (isOrderSampleConfirmed)
        {
            actualCorrectForSampleConfirmed(order, task);
        }
        else
        {
            boolean canceled = isOrderCanceled(order);
            
            if (canceled)
            {
                // 订单已经取消，无法判断是否全部收样完成，如果存在收样时间，则认为完成，否则未完成
                Date lastSampleReceivedTime = orderService.getLastSampleReceivedTime(order.getId(), null);
                
                if (null == lastSampleReceivedTime)
                {
                    actualCorrectForSampleUnconfirmedCanceledUnfinished(order, task);
                }
                else
                {
                    actualCorrectForSampleUnconfirmedCanceledFinished(order, task, lastSampleReceivedTime);
                }
            }
            else
            {
                boolean isSampleReceiveFinished = orderService.isSampleReceiveFinished(order.getId());
                
                if (isSampleReceiveFinished)
                {
                    actualCorrectForSampleUnconfirmedAsFinished(order, task);
                }
                else
                {
                    actualCorrectForSampleUnconfirmedAsUnfinished(order, task);
                }
            }
        }
    }
    
    private void actualCorrectForSampleUnconfirmedCanceledUnfinished(Order order, OrderPlanTask task)
    {
        task.setStarted(true);
        task.setActived(false);
        task.setFinished(false);
        task.setCanceled(true);
        task.setActualStartDate(order.getSubmitTime());
        task.setActualFinishDate(null);
        task.setPostponed(false);
        task.setPostponedDays(0);
        baseDaoSupport.update(task);
    }
    
    private void actualCorrectForSampleUnconfirmedCanceledFinished(Order order, OrderPlanTask task, Date finishDate)
    {
        task.setStarted(true);
        task.setActived(false);
        task.setFinished(true);
        task.setCanceled(false);
        task.setActualStartDate(order.getSubmitTime());
        task.setActualFinishDate(finishDate);
        
        int postponedDays = getPostponedDays(task, task.getActualFinishDate());
        
        if (postponedDays > 0)
        {
            task.setPostponed(true);
            task.setPostponedDays(postponedDays);
        }
        else
        {
            task.setPostponed(false);
            task.setPostponedDays(0);
        }
        
        baseDaoSupport.update(task);
    }
    
    private void actualCorrectForSampleConfirmed(Order order, OrderPlanTask task)
    {
        task.setActived(false);
        task.setStarted(true);
        task.setFinished(true);
        task.setCanceled(false);
        
        if (task.getActualStartDate() == null)
        {
            task.setActualStartDate(order.getSubmitTime());
        }
        
        if (task.getActualFinishDate() == null)
        {
            task.setActualFinishDate(getLastSampleReceivedTimeAsConfirmed(order));
        }
        
        int postponedDays = getPostponedDays(task, task.getActualFinishDate());
        
        if (postponedDays > 0)
        {
            task.setPostponed(true);
            task.setPostponedDays(postponedDays);
        }
        else
        {
            task.setPostponed(false);
            task.setPostponedDays(0);
        }
        
        baseDaoSupport.update(task);
    }
    
    private void actualCorrectForSampleUnconfirmedAsFinished(Order order, OrderPlanTask task)
    {
        task.setActived(false);
        task.setStarted(true);
        task.setFinished(true);
        task.setCanceled(false);
        
        if (task.getActualStartDate() == null)
        {
            task.setActualStartDate(order.getSubmitTime());
        }
        
        if (task.getActualFinishDate() == null)
        {
            task.setActualFinishDate(getLastSampleReceivedTimeAsUnconfirmed(order));
        }
        
        int postponedDays = getPostponedDays(task, task.getActualFinishDate());
        
        if (postponedDays > 0)
        {
            task.setPostponed(true);
            task.setPostponedDays(postponedDays);
        }
        else
        {
            task.setPostponed(false);
            task.setPostponedDays(0);
        }
        
        baseDaoSupport.update(task);
    }
    
    private void actualCorrectForSampleUnconfirmedAsUnfinished(Order order, OrderPlanTask task)
    {
        task.setStarted(true);
        task.setActived(true);
        task.setFinished(false);
        task.setCanceled(false);
        task.setActualStartDate(order.getSubmitTime());
        
        int postponedDays = getPostponedDays(task, new Date());
        
        if (postponedDays > 0)
        {
            task.setPostponed(true);
            task.setPostponedDays(postponedDays);
        }
        else
        {
            task.setPostponed(false);
            task.setPostponedDays(0);
        }
        
        baseDaoSupport.update(task);
    }
    
    private Date getLastSampleReceivedTimeAsConfirmed(Order order)
    {
        Date testingStartTime = orderService.getTestingStartTime(order.getId());
        Date confirmedTime = orderService.getLastSampleReceivedTime(order.getId(), testingStartTime);
        
        if (null == confirmedTime)
        {
            log.warn("Illegal data, order sample is confirmed, but cannot found the sample received time, order id {}.", order.getId());
            confirmedTime = DateUtils.addDays(order.getSubmitTime(), 2);
        }
        
        return confirmedTime;
    }
    
    private Date getLastSampleReceivedTimeAsUnconfirmed(Order order)
    {
        Date confirmedTime = orderService.getLastSampleReceivedTime(order.getId(), null);
        
        if (null == confirmedTime)
        {
            log.warn("Illegal data, order sample receive is finished, but cannot found the sample received time, order id {}.", order.getId());
            confirmedTime = DateUtils.addDays(order.getSubmitTime(), 2);
        }
        
        return confirmedTime;
    }
    
    private int getPostponedDays(OrderPlanTask task, Date refer)
    {
        Date monitorDate = task.isStarted() ? task.getPlannedFinishDate() : task.getPlannedStartDate();
        return getDays(monitorDate, refer);
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
    
    private boolean isOrderCanceled(Order order)
    {
        return Order.TESTING_STATUS_CANCELED.equals(order.getTestingStatus());
    }
    
    private boolean isOrderSampleConfirmed(Order order)
    {
        return Order.SAMPLE_STATUS_CONFIRMED.equals(order.getSampleConfirmStatus());
    }
}
