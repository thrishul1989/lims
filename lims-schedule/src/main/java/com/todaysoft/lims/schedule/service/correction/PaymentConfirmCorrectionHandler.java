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
import com.todaysoft.lims.schedule.service.PaymentConfirmType;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class PaymentConfirmCorrectionHandler extends AbstractCorrectionHandler
{
    private static Logger log = LoggerFactory.getLogger(PaymentConfirmCorrectionHandler.class);
    
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
        
        OrderPlanTask task = orderPlanTaskQueryService.queryForOrderSemanticRecord(orderId, OrderPlanTask.SEMANTIC_PAYMENT_CONFIRM);
        
        if (null == task)
        {
            log.error("Cannot found the PAYMENT_CONFIRM plan task for order {}.", orderId);
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
        
        ScheduleGlobalConfig config = configService.getGlobalConfig(OrderPlanTask.SEMANTIC_PAYMENT_CONFIRM);
        
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
        boolean isOrderPaymentConfirmed = isOrderPaymentConfirmed(order);
        PaymentConfirmType paymentConfirmType = orderService.getOrderPaymentConfirmType(order.getId());
        
        if (null == paymentConfirmType)
        {
            log.error("Cannot found the order payment confirm type for order id {}.", order.getId());
            throw new IllegalStateException();
        }
        
        // 
        if (PaymentConfirmType.AUTO_CONFIRMED_CENTRAL_SETTLEMENT.equals(paymentConfirmType))
        {
            if (!isOrderPaymentConfirmed)
            {
                log.warn("Order payment confirm status is not matched for auto confirmed, order id {}.", order.getId());
            }
            
            actualCorrectForAutoConfirmedCentralSettlement(order, task);
        }
        // 集中支付-人工确认
        else if (PaymentConfirmType.MANUAL_CONFIRMED_CENTRAL_SETTLEMENT.equals(paymentConfirmType))
        {
            if (!isOrderPaymentConfirmed)
            {
                actualCorrectForManualConfirmedCentralSettlementAsUnconfirmedStatus(order, task);
            }
            else
            {
                actualCorrectForManualConfirmedCentralSettlementAsConfirmedStatus(order, task);
            }
        }
        // 一单一结-人工确认
        else
        {
            if (!isOrderPaymentConfirmed)
            {
                actualCorrectForManualConfirmedSingleSettlementAsUnconfirmedStatus(order, task);
            }
            else
            {
                actualCorrectForManualConfirmedSingleSettlementAsConfirmedStatus(order, task);
            }
        }
    }
    
    // 集中支付-自动确认-监控进度设置
    private void actualCorrectForAutoConfirmedCentralSettlement(Order order, OrderPlanTask task)
    {
        task.setActived(false);
        task.setStarted(true);
        task.setFinished(true);
        task.setCanceled(false);
        task.setPostponed(false);
        task.setPostponedDays(0);
        task.setActualStartDate(order.getSubmitTime());
        task.setActualFinishDate(order.getSubmitTime());
        baseDaoSupport.update(task);
    }
    
    // 集中支付-人工确认-未确认-监控进度设置
    private void actualCorrectForManualConfirmedCentralSettlementAsUnconfirmedStatus(Order order, OrderPlanTask task)
    {
        task.setStarted(true);
        task.setActualStartDate(order.getSubmitTime());
        
        boolean canceled = isOrderCanceled(order);
        
        if (canceled)
        {
            task.setActived(false);
            task.setFinished(false);
            task.setCanceled(true);
            task.setActualFinishDate(null);
            task.setPostponed(false);
            task.setPostponedDays(0);
        }
        else
        {
            task.setActived(true);
            task.setFinished(false);
            task.setCanceled(false);
            
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
        }
        
        baseDaoSupport.update(task);
    }
    
    // 集中支付-人工确认-已确认-监控进度设置
    private void actualCorrectForManualConfirmedCentralSettlementAsConfirmedStatus(Order order, OrderPlanTask task)
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
            task.setActualFinishDate(DateUtils.addDays(order.getSubmitTime(), 2));
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
    
    // 一单一结-未下放-监控进度设置
    private void actualCorrectForManualConfirmedSingleSettlementAsUnconfirmedStatus(Order order, OrderPlanTask task)
    {
        task.setStarted(true);
        task.setActualStartDate(order.getSubmitTime());
        
        boolean canceled = isOrderCanceled(order);
        
        if (canceled)
        {
            task.setActived(false);
            task.setFinished(false);
            task.setCanceled(true);
            task.setActualFinishDate(null);
            task.setPostponed(false);
            task.setPostponedDays(0);
        }
        else
        {
            task.setActived(true);
            task.setFinished(false);
            task.setCanceled(false);
            
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
        }
        
        baseDaoSupport.update(task);
    }
    
    // 一单一结-已下放-监控进度设置
    private void actualCorrectForManualConfirmedSingleSettlementAsConfirmedStatus(Order order, OrderPlanTask task)
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
            task.setActualFinishDate(getSingleSettlementManualConfirmedTime(order));
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
    
    private Date getSingleSettlementManualConfirmedTime(Order order)
    {
        Date confirmedTime = orderService.getFirstPaymentAmountConfirmedTime(order.getId());
        
        // 延迟付款终审通过时间存在且早于付款确认时间，则设置确认时间为延迟付款申请终审通过时间
        Date delayPaymentAgreedTime = orderService.getDelayPaymentAgreedTime(order.getId());
        
        if (null != delayPaymentAgreedTime && (null == confirmedTime || delayPaymentAgreedTime.before(confirmedTime)))
        {
            confirmedTime = delayPaymentAgreedTime;
        }
        
        if (null != confirmedTime)
        {
            return confirmedTime;
        }
        
        int productAmount = null == order.getProductAmount() ? 0 : order.getProductAmount().intValue();
        int subsidiarySampleAmount = null == order.getSubsidiarySampleAmount() ? 0 : order.getSubsidiarySampleAmount().intValue();
        
        int discount = null == order.getDiscountAmount() ? 0 : order.getDiscountAmount();
        int reduce = null == order.getReduceAmount() ? 0 : order.getReduceAmount();
        
        // 没有付款确认时间、没有延迟申请终审通过时间，且满足减免通过的条件，则设置减免时间为确认时间
        if (discount + reduce >= productAmount + subsidiarySampleAmount)
        {
            confirmedTime = orderService.getReduceAgreedTime(order.getId());
        }
        
        if (null != confirmedTime)
        {
            return confirmedTime;
        }
        
        // 根据付款确认、延迟申请终审通过、全免终审通过找不到确认时间，则尝试根据流程启动时间和入库时间比较来计算
        Date testingStartTime = orderService.getTestingStartTime(order.getId());
        
        if (null != testingStartTime)
        {
            Date lastSampleStoragedTime = orderService.getLastSampleStoragedTime(order.getId(), testingStartTime);
            
            if (null != lastSampleStoragedTime)
            {
                long tst = testingStartTime.getTime();
                long sst = lastSampleStoragedTime.getTime();
                
                // 流程启动时间大于样本入库时间，则认为是先入库后财务下放，设置财务下放时间为流程启动时间
                if (tst - sst > 10 * 60 * 1000)
                {
                    confirmedTime = testingStartTime;
                }
            }
        }
        
        // 最终找不到财务确认时间时，默认设置为提单提交两天后
        if (null == confirmedTime)
        {
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
    
    private boolean isOrderPaymentConfirmed(Order order)
    {
        return Order.PAYMENT_STATUS_CONFIRMED.equals(order.getPaymentConfirmStatus());
    }
}
