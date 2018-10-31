package com.todaysoft.lims.testing.listener.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.testing.base.entity.Contract;
import com.todaysoft.lims.testing.base.entity.ContractContent;
import com.todaysoft.lims.testing.base.entity.Order;
import com.todaysoft.lims.testing.base.entity.OrderExtraSampleDetail;
import com.todaysoft.lims.testing.base.entity.OrderExtraSampleHandle;
import com.todaysoft.lims.testing.base.entity.OrderPlanTask;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.base.entity.TestingResamplingRecord;
import com.todaysoft.lims.testing.base.entity.TestingResamplingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.request.StartOrderTestingRequest;
import com.todaysoft.lims.testing.base.request.TestingScheduleQueryRequest;
import com.todaysoft.lims.testing.base.service.ITestingResolveService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.base.service.adapter.bmm.BMMAdapter;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderSampleDetailsResponse;
import com.todaysoft.lims.testing.base.service.adapter.bmm.OrderSimpleModel;
import com.todaysoft.lims.testing.listener.model.SampleAbnormalSolvedEvent;
import com.todaysoft.lims.testing.listener.model.SampleReceivedDetails;
import com.todaysoft.lims.testing.listener.model.SampleReceivedEvent;
import com.todaysoft.lims.testing.listener.model.SampleStoragedEvent;
import com.todaysoft.lims.testing.listener.service.ISampleEventService;
import com.todaysoft.lims.testing.resampling.service.IResamplingService;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class SampleStoragedService implements ISampleEventService
{
    private static final Logger log = LoggerFactory.getLogger(SampleStoragedService.class);
    
    @Autowired
    private BMMAdapter bmmadapter;
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IResamplingService resamplingService;
    
    @Autowired
    private ITestingResolveService testingResolveService;
    
    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    @Transactional
    public void received(SampleReceivedEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Sample received event contains {} samples.", CollectionUtils.isEmpty(event.getDetails()) ? 0 : event.getDetails().size());
        }
        
        if (CollectionUtils.isEmpty(event.getDetails()))
        {
            return;
        }
        
        for (SampleReceivedDetails sample : event.getDetails())
        {
            updateResamplingRecordStatus(sample);
        }
    }
    
    private void updateResamplingRecordStatus(SampleReceivedDetails sample)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Sample {} received status is {}", sample.getSampleId(), sample.isQualified() ? "qualified" : "unqualified");
        }
        
        String hql = "FROM TestingResamplingRecord r WHERE r.resendSampleId = :id";
        List<TestingResamplingRecord> records =
            baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, new String[] {"id"}, new Object[] {sample.getSampleId()});
        
        if (CollectionUtils.isEmpty(records))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Can not found record for resend sample as {}", sample.getSampleId());
            }
            
            return;
        }
        
        String status = sample.isQualified() ? TestingResamplingRecord.RESEND_SAMPLE_RECEIVED : TestingResamplingRecord.RESEND_SAMPLE_ABNORMAL;
        
        if (log.isDebugEnabled())
        {
            log.debug("Found record for resend sample as {}, set status to {}", sample.getSampleId(), status);
        }
        
        TestingResamplingRecord record = records.get(0);
        record.setResendSampleStatus(status);
        baseDaoSupport.update(record);
        
        if (log.isDebugEnabled())
        {
            log.debug("Set status to {} for resend sample {} successful.", status, sample.getSampleId());
        }
    }
    
    @Override
    @Transactional
    public void storaged(SampleStoragedEvent request)
    {
        if ("1".equals(request.getStoragingType()))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Storaged type is temporary, ignore the event.");
            }
            
            return;
        }
        
        OrderExtraSampleHandle orderExtraSampleHandle;
        
        log.info(request.getSampleCode() + " sample code~~~!!!!!!%%%%");
        
        Order order = getOrderByReceivedSampleCode(request.getSampleCode());
        
        if (null == order)
        {
            throw new IllegalStateException();
        }
        
        //处理追加样本的逻辑1.判断该订单是否已经启动流程 未启动或者启动异常不做处理
        if (null != order.getSheduleStatus() && 2 == order.getSheduleStatus().intValue())
        {
            ReceivedSample receivedSample = getSampleIdByCode(request.getSampleCode());
            String sampleId = receivedSample.getSampleId();
            
            //判断是否是追加样本
            if (isExtraSample(sampleId))
            {
                Integer purpose = 0;
                boolean isValify = isOrderValidSchedule(order.getId());
                
                if (StringUtils.isNotEmpty(receivedSample.getPurpose()))
                {
                    purpose = Integer.valueOf(receivedSample.getPurpose());
                }
                
                //2.已经启动-1.对照样本直接存入待处理记录表 2.验证样本 还没进入验证流程的不做处理  否则生成待处理任务
                //1-验证  2-检测 3-对照
                orderExtraSampleHandle = new OrderExtraSampleHandle();
                if (3 == purpose.intValue())
                {
                    orderExtraSampleHandle.setOrderId(order.getId());
                    orderExtraSampleHandle.setSampleId(sampleId);
                    orderExtraSampleHandle.setPurpose(Integer.valueOf(receivedSample.getPurpose()));
                    orderExtraSampleHandle.setStatus(0);
                    orderExtraSampleHandle.setCreateTime(new Date());
                    baseDaoSupport.insert(orderExtraSampleHandle);
                }
                else if (1 == purpose.intValue())
                {
                    if (isValify) //存在验证流程，走追加样本处理启动  技术分析提交后，样本只要提取了默认自动起来了，就不需要操作 || 未提取走提取任务检查有没有启动流程
                    {
                        //判断 是否已经生成 该样本的验证任务 无创建 有说明已经收样和提交技术分析
                        List<TestingSchedule> startedSchedule =
                            testingScheduleService.searcherTestingScheduleByCondition(new TestingScheduleQueryRequest(order.getId(), sampleId));
                        if (Collections3.isEmpty(startedSchedule))
                        {
                            orderExtraSampleHandle.setOrderId(order.getId());
                            orderExtraSampleHandle.setSampleId(sampleId);
                            orderExtraSampleHandle.setPurpose(Integer.valueOf(receivedSample.getPurpose()));
                            orderExtraSampleHandle.setStatus(0);
                            orderExtraSampleHandle.setCreateTime(new Date());
                            baseDaoSupport.insert(orderExtraSampleHandle);
                        }
                        
                    }
                    else
                    {//没走到技术分析,自动给验证样本启动DNA提取/质检流程
                    
                        StartOrderTestingRequest startRequest = new StartOrderTestingRequest();
                        startRequest.setId(order.getId());
                        startRequest.setSampleId(sampleId);
                        List<TestingStartRecord> records = testingResolveService.resolveAsSingleVeritySample(startRequest, 1);
                        
                        if (!CollectionUtils.isEmpty(records))
                        {
                            List<String> scheduleIds = testingScheduleService.startActional(records, 1);
                            //追加样本增加监控信息 sj 2018年10月27日16:38:26
                            testingScheduleService.sendAppendVerityOrderTestingStartForMonitorMessage(order.getId(), scheduleIds);
                        }
                        else
                        {
                            log.warn("Order {} start record is empty.", order.getId());
                        }
                    }
                    
                }
            }
            
            TestingResamplingRecord record = getRecordByReceivedResendSampleId(sampleId);//判断是否是重新送样
            
            if (null != record)
            {
                resamplingService.restart(record);
            }
            else
            {
                log.error("chong xin song yang ji lu wei kong,task is null,new sampleId is" + sampleId);
            }
        }
        else
        {
            boolean isStartable = isOrderStartable(order.getId());
            
            if (isStartable)
            {
                startOrder(order);
            }
        }
    }
    
    @Override
    @Transactional
    public void abnormalSolved(SampleAbnormalSolvedEvent event)
    {
        if (StringUtils.isEmpty(event.getAbnormalSampleId()) || StringUtils.isEmpty(event.getSolvedStrategy()))
        {
            log.warn("Sample abnormal solved event args illegal, abnormal sample {}, strategy {}", event.getAbnormalSampleId(), event.getSolvedStrategy());
            return;
        }
        
        Set<String> strategies = new HashSet<String>();
        strategies.add(SampleAbnormalSolvedEvent.SOLVED_RESEND_SAMPLE);
        strategies.add(SampleAbnormalSolvedEvent.SOLVED_NOSEND_SAMPLE);
        
        if (!strategies.contains(event.getSolvedStrategy()))
        {
            log.warn("Sample abnormal solved event args illegal, strategy {}", event.getSolvedStrategy());
            return;
        }
        
        if (SampleAbnormalSolvedEvent.SOLVED_NOSEND_SAMPLE.equals(event.getSolvedStrategy()))
        {
            abnormalSolvedAsNosendSample(event);
        }
        else
        {
            log.info("Sample resampling....");
            abnormalSolvedAsResendSample(event);
        }
    }
    
    private void abnormalSolvedAsResendSample(SampleAbnormalSolvedEvent event)
    {
        TestingResamplingRecord record = getRecordByAbnormalResendSampleId(event.getAbnormalSampleId());
        
        if (null != record)
        {
            // 实验流程-重新送样，重新送样的样本再次异常，第二次异常又设置为重新送样
            log.info("Found testing resamping record nested abnormal, record id {}, solved as resend.", record.getId());
            record.setStrategy(SampleAbnormalSolvedEvent.SOLVED_RESEND_SAMPLE);
            record.setResendSampleId(event.getResendSampleId());
            record.setResendSampleStatus(TestingResamplingRecord.RESEND_SAMPLE_UNRECEIVED);
            baseDaoSupport.update(record);
        }
        else
        {
            record = getRecordByAbnormalSampleId(event.getAbnormalSampleId());
            
            if (null != record)
            {
                // 实验流程-重新送样，处理策略为重新送样
                log.info("Found testing resamping record, record id {}, solved as resend.", record.getId());
                record.setStrategy(SampleAbnormalSolvedEvent.SOLVED_RESEND_SAMPLE);
                record.setResendSampleId(event.getResendSampleId());
                record.setResendSampleStatus(TestingResamplingRecord.RESEND_SAMPLE_UNRECEIVED);
                baseDaoSupport.update(record);
            }
            else
            {
                log.info("Found testing resamping record, solved as resend .record is null AbnormalSampleId is {}", event.getAbnormalSampleId());
            }
        }
        //原来样本的监控计划 设置已取消  ww 2017.7.13
        String hql = "FROM TestingResamplingSchedule s WHERE s.record.id = :id";
        List<TestingResamplingSchedule> recordSchedules =
            baseDaoSupport.findByNamedParam(TestingResamplingSchedule.class, hql, new String[] {"id"}, new Object[] {record.getId()});
        if (Collections3.isNotEmpty(recordSchedules))
        {
            TestingSchedule testingSchedule = baseDaoSupport.get(TestingSchedule.class, recordSchedules.get(0).getScheduleId());
            if (null != testingSchedule)
            {
                TestingMethod testingMethod = baseDaoSupport.get(TestingMethod.class, testingSchedule.getMethodId());
                if (null != testingMethod)
                {
                    // 1-检测   、2-验证
                    if ("1".equals(testingMethod.getType()))
                    {
                        String hql2 =
                            "FROM OrderPlanTask t WHERE t.orderId = :orderId AND t.productId = :productId AND t.sampleId = :sampleId AND t.testingMethodId = :testingMethodId ";
                        List<OrderPlanTask> planTaskList =
                            baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                                hql2,
                                new String[] {"orderId", "productId", "sampleId", "testingMethodId"},
                                new Object[] {testingSchedule.getOrderId(), testingSchedule.getProductId(), testingSchedule.getSampleId(),
                                    testingSchedule.getMethodId()});
                        if (Collections3.isNotEmpty(planTaskList))
                        {
                            for (OrderPlanTask planTask : planTaskList)
                            {
                                /* if(!planTask.isFinished())
                                 {
                                     planTask.setActived(false);
                                     planTask.setCanceled(true);
                                     baseDaoSupport.update(planTask);
                                 }*/
                                if ("GROUP_PRODUCT_SAMPLE_METHOD".equals(planTask.getTaskSemantic()))
                                {
                                    OrderPlanTask parentPlanTask = baseDaoSupport.get(OrderPlanTask.class, planTask.getParentId());
                                    parentPlanTask.setActived(false);
                                    parentPlanTask.setCanceled(true);
                                    baseDaoSupport.update(parentPlanTask);
                                }
                            }
                        }
                    }
                    else if ("2".equals(testingMethod.getType()))
                    {
                        String hql2 =
                            "FROM OrderPlanTask t WHERE t.orderId = :orderId AND t.productId = :productId AND t.sampleId = :sampleId AND t.testingMethodId = :testingMethodId ";
                        List<OrderPlanTask> planTaskList =
                            baseDaoSupport.findByNamedParam(OrderPlanTask.class,
                                hql2,
                                new String[] {"orderId", "productId", "sampleId", "testingMethodId"},
                                new Object[] {testingSchedule.getOrderId(), testingSchedule.getProductId(), testingSchedule.getSampleId(),
                                    testingSchedule.getMethodId()});
                        if (Collections3.isNotEmpty(planTaskList))
                        {
                            for (OrderPlanTask planTask : planTaskList)
                            {
                                if ("GROUP_PRODUCT_SAMPLE_METHOD_VERIFY".equals(planTask))
                                {
                                    planTask.setActived(false);
                                    planTask.setCanceled(true);
                                    baseDaoSupport.update(planTask);
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    private void abnormalSolvedAsNosendSample(SampleAbnormalSolvedEvent event)
    {
        TestingResamplingRecord record = getRecordByAbnormalResendSampleId(event.getAbnormalSampleId());
        
        if (null != record)
        {
            // 实验流程-重新送样，重新送样的样本再次异常，第二次异常设置为不送样的情况
            log.info("Found testing resamping record nested abnormal, record id {}, solved as nosend.", record.getId());
            record.setStrategy(SampleAbnormalSolvedEvent.SOLVED_NOSEND_SAMPLE);
            record.setResendSampleId(null);
            record.setResendSampleStatus(null);
            baseDaoSupport.update(record);
        }
        else
        {
            record = getRecordByAbnormalSampleId(event.getAbnormalSampleId());
            
            if (null != record)
            {
                // 实验流程-重新送样，处理策略为不送样
                log.info("Found testing resamping record, record id {}, solved as nosend.", record.getId());
                record.setStrategy(SampleAbnormalSolvedEvent.SOLVED_NOSEND_SAMPLE);
                record.setResendSampleId(null);
                record.setResendSampleStatus(null);
                baseDaoSupport.update(record);
            }
            else
            {
                log.info("Not found testing resamping record, try to start order.");
                
                Order order = getOrderBySampleId(event.getAbnormalSampleId());
                
                if (null == order)
                {
                    log.warn("Can not found order by sample {}", event.getAbnormalSampleId());
                    return;
                }
                
                if (null != order.getSheduleStatus() && 2 == order.getSheduleStatus().intValue())
                {
                    log.warn("Order {} has already started, ignore it", order.getId());
                    return;
                }
                
                boolean isStartable = isOrderStartable(order.getId());
                
                if (isStartable)
                {
                    startOrder(order);
                }
            }
        }
    }
    
    private TestingResamplingRecord getRecordByAbnormalResendSampleId(String id)
    {
        String hql = "FROM TestingResamplingRecord r WHERE r.resendSampleId = :id AND r.resendSampleStatus = :abnormalStatus";
        List<TestingResamplingRecord> records =
            baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, new String[] {"id", "abnormalStatus"}, new Object[] {id,
                TestingResamplingRecord.RESEND_SAMPLE_ABNORMAL});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    @Override
    public TestingResamplingRecord getRecordByReceivedResendSampleId(String id)
    {
        String hql = "FROM TestingResamplingRecord r WHERE r.resendSampleId = :id AND r.resendSampleStatus = :receivedStatus";
        List<TestingResamplingRecord> records =
            baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, new String[] {"id", "receivedStatus"}, new Object[] {id,
                TestingResamplingRecord.RESEND_SAMPLE_RECEIVED});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    private TestingResamplingRecord getRecordByAbnormalSampleId(String id)
    {
        String hql = "FROM TestingResamplingRecord r WHERE r.abnormalSampleId = :id";
        List<TestingResamplingRecord> records = baseDaoSupport.findByNamedParam(TestingResamplingRecord.class, hql, new String[] {"id"}, new Object[] {id});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    private boolean isOrderStartable(String orderId)
    {
        OrderSampleDetailsResponse response = bmmadapter.getOrderSampleDetails(orderId);
        Set<String> totalSampleCodes = response.getTotalSampleCodes();
        
        if (CollectionUtils.isEmpty(totalSampleCodes))
        {
            log.warn("Order {} total sample codes is empty.", orderId);
            throw new IllegalStateException();
        }
        
        Set<String> abnormalSolvedSampleCodes = response.getAbnormalSolvedSampleCodes();
        
        if (null == abnormalSolvedSampleCodes)
        {
            abnormalSolvedSampleCodes = Collections.emptySet();
        }
        
        Set<String> storagedSampleCodes = response.getStoragedSampleCodes();
        
        if (null == storagedSampleCodes)
        {
            storagedSampleCodes = Collections.emptySet();
        }
        
        totalSampleCodes.removeAll(abnormalSolvedSampleCodes);
        totalSampleCodes.removeAll(storagedSampleCodes);
        log.info("Order start required samples {}.", totalSampleCodes);
        return totalSampleCodes.isEmpty();
    }
    
    private void startOrder(Order order)
    {
        log.info("Start order {}.", order.getId());
        order.setReceivedSampleStatus(1);
        Contract contract = order.getContract();
        if (null != contract)
        {
            ContractContent content = baseDaoSupport.get(ContractContent.class, contract.getId());
            //合同的结算类型  ---非一单一结的时候才会考虑
            if (StringUtils.isNotEmpty(content) && !"4".equals(content.getSettlementMode()))
            {
                if (null != contract.getStartMode() && 1 == contract.getStartMode().intValue())//合同订单且启动方式是系统启动 直接设置付费状态1
                {
                    order.setSchedulePaymentStatus(1);
                }
            }
            
        }
        baseDaoSupport.update(order);
        StartOrderTestingRequest startRequest = new StartOrderTestingRequest();
        startRequest.setId(order.getId());
        
        List<TestingStartRecord> records = testingResolveService.resolve(startRequest, 1);
        
        if (!CollectionUtils.isEmpty(records))
        {
            testingScheduleService.start(records, 1);
        }
        else
        {
            log.warn("Order {} start record is empty.", order.getId());
        }
    }
    
    @Override
    public Order getOrderByReceivedSampleCode(String code)
    {
        String hql = "SELECT s.orderId FROM ReceivedSample s WHERE s.sampleCode = :code";
        List<String> records = baseDaoSupport.findByNamedParam(String.class, hql, new String[] {"code"}, new Object[] {code});
        
        if (CollectionUtils.isEmpty(records))
        {
            log.info(" records is null~~~!!!!!!%%%%");
            return null;
        }
        log.info(records.get(0) + " order is is ~~~!!!!!!%%%%");
        return baseDaoSupport.get(Order.class, records.get(0));
    }
    
    private ReceivedSample getSampleIdByCode(String code)
    {
        String hql = "FROM ReceivedSample s WHERE s.sampleCode = :code";
        List<ReceivedSample> records = baseDaoSupport.findByNamedParam(ReceivedSample.class, hql, new String[] {"code"}, new Object[] {code});
        
        if (CollectionUtils.isEmpty(records))
        {
            return null;
        }
        
        return records.get(0);
    }
    
    private Order getOrderBySampleId(String id)
    {
        OrderSimpleModel data = bmmadapter.getOrderBySampleId(id);
        
        if (null == data || StringUtils.isEmpty(data.getId()))
        {
            return null;
        }
        
        return baseDaoSupport.get(Order.class, data.getId());
    }
    
    private boolean isOrderValidSchedule(String orderId)
    {
        String hql = "FROM TestingSchedule t WHERE t.orderId = :orderId ";
        List<TestingSchedule> records = baseDaoSupport.findByNamedParam(TestingSchedule.class, hql, new String[] {"orderId"}, new String[] {orderId});
        for (TestingSchedule testingSchedule : records)
        {
            if (StringUtils.isNotEmpty(testingSchedule.getVerifyKey()))
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean isExtraSample(String sampleId)
    {
        String hql = "FROM OrderExtraSampleDetail t WHERE t.sampleId = :sampleId ";
        List<OrderExtraSampleDetail> records =
            baseDaoSupport.findByNamedParam(OrderExtraSampleDetail.class, hql, new String[] {"sampleId"}, new String[] {sampleId});
        if (Collections3.isNotEmpty(records))
        {
            return true;
        }
        return false;
    }
}
