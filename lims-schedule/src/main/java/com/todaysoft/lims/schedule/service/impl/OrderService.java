package com.todaysoft.lims.schedule.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.schedule.adapter.impl.ProductAdapter;
import com.todaysoft.lims.schedule.entity.Contract;
import com.todaysoft.lims.schedule.entity.ContractContent;
import com.todaysoft.lims.schedule.entity.Order;
import com.todaysoft.lims.schedule.entity.OrderDelayPaymentApplyRecord;
import com.todaysoft.lims.schedule.entity.OrderDelayPaymentApproveRecord;
import com.todaysoft.lims.schedule.entity.OrderPrimarySample;
import com.todaysoft.lims.schedule.entity.OrderReduceApplyRecord;
import com.todaysoft.lims.schedule.entity.OrderReduceApproveRecord;
import com.todaysoft.lims.schedule.entity.OrderResearchSample;
import com.todaysoft.lims.schedule.entity.OrderSampleView;
import com.todaysoft.lims.schedule.entity.OrderSubsidiarySample;
import com.todaysoft.lims.schedule.entity.Product;
import com.todaysoft.lims.schedule.entity.ProductTestingMethod;
import com.todaysoft.lims.schedule.entity.ScheduleTestingConfig;
import com.todaysoft.lims.schedule.entity.ScheduleTestingNodeConfig;
import com.todaysoft.lims.schedule.entity.TestingMethod;
import com.todaysoft.lims.schedule.entity.TestingSchedule;
import com.todaysoft.lims.schedule.model.OrderProductTestings;
import com.todaysoft.lims.schedule.model.OrderSchedules;
import com.todaysoft.lims.schedule.model.OrderTestings;
import com.todaysoft.lims.schedule.model.TestingNode;
import com.todaysoft.lims.schedule.service.IOrderService;
import com.todaysoft.lims.schedule.service.PaymentConfirmType;
import com.todaysoft.lims.utils.Collections3;

@Service
public class OrderService implements IOrderService
{
    private static Logger log = LoggerFactory.getLogger(OrderService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private ProductAdapter productAdapter;
    
    @Override
    public Order getOrder(String id)
    {
        return baseDaoSupport.get(Order.class, id);
    }
    
    @Override
    public OrderTestings getOrderTestings(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        
        if (null == order)
        {
            log.warn("Can not found order entity by id {}", id);
            return null;
        }
        
        List<Product> products = new ArrayList<Product>();
        
        List<Product> records = getOrderProducts(id);
        
        if (!CollectionUtils.isEmpty(records))
        {
            products.addAll(records);
        }
        
        records = getResearchOrderProducts(id);
        
        if (!CollectionUtils.isEmpty(records))
        {
            products.addAll(records);
        }
        
        OrderTestings orderTestings = new OrderTestings();
        orderTestings.setId(order.getId());
        orderTestings.setSubmitTime(order.getSubmitTime());
        
        if (CollectionUtils.isEmpty(products))
        {
            log.warn("Can not found any products for order entity, order id {}", id);
            orderTestings.setProductTestings(Collections.emptyList());
        }
        else
        {
            orderTestings.setProductTestings(getOrderProductTestings(order, products));
        }
        
        return orderTestings;
    }
    
    @Override
    public boolean isOrderPlanned(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT (*) FROM OrderPlanTask opt WHERE opt.orderId = :orderId")
                .names(Collections.singletonList("orderId"))
                .values(Collections.singletonList(orderId))
                .build();
        return baseDaoSupport.find(queryer, Number.class).get(0).intValue() > 0;
    }
    
    @Override
    public OrderSchedules getOrderSchedules(String id, List<String> scheduleIds)
    {
        List<String> params = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        StringBuffer hql = new StringBuffer();
        hql.append(" FROM TestingSchedule s WHERE s.orderId = :orderId ");
        params.add("orderId");
        values.add(id);
        if (Collections3.isNotEmpty(scheduleIds))
        {
            hql.append(" AND s.id IN (:scheduleIds)");
            params.add("scheduleIds");
            values.add(scheduleIds);
        }
        NamedQueryer queryer = NamedQueryer.builder().hql(hql.toString()).names(params).values(values).build();
        List<TestingSchedule> records = baseDaoSupport.find(queryer, TestingSchedule.class);
        return new OrderSchedules(records);
    }
    
    @Override
    public OrderSampleView getBeforeSampleBySampleCode(String sampleCode)
    {
        List<OrderSampleView> orderSampleViews =
            baseDaoSupport.find(OrderSampleView.class, "FROM OrderSampleView osv WHERE osv.sampleErrorNewNo = '" + sampleCode + "'");
        return Collections3.getFirst(orderSampleViews);
    }
    
    @Override
    public String getOrderIdBySampleId(String sampleId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderSampleView osv WHERE osv.sampleId = :sampleId")
                .names(Collections.singletonList("sampleId"))
                .values(Collections.singletonList(sampleId))
                .build();
        OrderSampleView orderSampleView = Collections3.getFirst(baseDaoSupport.find(queryer, OrderSampleView.class));
        return null != orderSampleView ? orderSampleView.getOrderId() : null;
    }
    
    @Override
    public String getOrderIdBySampleCode(String sampleCode)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderSampleView osv WHERE osv.sampleCode = :sampleCode")
                .names(Collections.singletonList("sampleCode"))
                .values(Collections.singletonList(sampleCode))
                .build();
        OrderSampleView orderSampleView = Collections3.getFirst(baseDaoSupport.find(queryer, OrderSampleView.class));
        return null != orderSampleView ? orderSampleView.getOrderId() : null;
    }
    
    @Override
    public boolean isOrderTestingStarted(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        
        if (null == order)
        {
            return false;
        }
        
        return Order.SCHEDULE_STARTED.equals(order.getScheduleStatus());
    }
    
    @Override
    public boolean isSampleReceiveFinished(String orderId)
    {
        if (!isPrimarySampleReceiveFinished(orderId))
        {
            return false;
        }
        
        if (!isSubsidiarySampleReceiveFinished(orderId))
        {
            return false;
        }
        
        return isResearchSampleReceiveFinished(orderId);
    }
    
    private boolean isPrimarySampleReceiveFinished(String orderId)
    {
        // 是否包含待送样和送样中的
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT(s.id) FROM OrderPrimarySample s WHERE (s.receiveStatus = :undeliveredStatus OR s.receiveStatus = :deliveringStatus) AND s.orderId = :orderId")
                .names(Arrays.asList("orderId", "undeliveredStatus", "deliveringStatus"))
                .values(Arrays.asList(orderId, OrderPrimarySample.RECEIVE_STATUS_UNDELIVERED, OrderPrimarySample.RECEIVE_STATUS_DELIVERING))
                .build();
        
        int count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        
        if (count > 0)
        {
            return false;
        }
        
        // 是否包含运输异常样本未处理的
        queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT(s.id) FROM OrderPrimarySample s WHERE s.receiveStatus = :errorStatus AND (s.errorType = :deliveryErrorType OR s.errorType IS NULL) AND s.errorSolveStatus = :unsolvedStatus AND s.orderId = :orderId")
                .names(Arrays.asList("orderId", "errorStatus", "deliveryErrorType", "unsolvedStatus"))
                .values(Arrays.asList(orderId,
                    OrderPrimarySample.RECEIVE_STATUS_ERROR,
                    OrderPrimarySample.ERROR_TYPE_DELIVERY,
                    OrderPrimarySample.ERROR_SOLVE_STATUS_UNSOLVED))
                .build();
        
        count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        
        if (count > 0)
        {
            return false;
        }
        
        return true;
    }
    
    private boolean isSubsidiarySampleReceiveFinished(String orderId)
    {
        // 是否包含待送样和送样中的
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT(s.id) FROM OrderSubsidiarySample s WHERE (s.receiveStatus = :undeliveredStatus OR s.receiveStatus = :deliveringStatus) AND s.orderId = :orderId")
                .names(Arrays.asList("orderId", "undeliveredStatus", "deliveringStatus"))
                .values(Arrays.asList(orderId, OrderSubsidiarySample.RECEIVE_STATUS_UNDELIVERED, OrderSubsidiarySample.RECEIVE_STATUS_DELIVERING))
                .build();
        
        int count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        
        if (count > 0)
        {
            return false;
        }
        
        // 是否包含运输异常样本未处理的
        queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT(s.id) FROM OrderSubsidiarySample s WHERE s.receiveStatus = :errorStatus AND (s.errorType = :deliveryErrorType OR s.errorType IS NULL) AND s.errorSolveStatus = :unsolvedStatus AND s.orderId = :orderId")
                .names(Arrays.asList("orderId", "errorStatus", "deliveryErrorType", "unsolvedStatus"))
                .values(Arrays.asList(orderId,
                    OrderSubsidiarySample.RECEIVE_STATUS_ERROR,
                    OrderSubsidiarySample.ERROR_TYPE_DELIVERY,
                    OrderSubsidiarySample.ERROR_SOLVE_STATUS_UNSOLVED))
                .build();
        
        count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        
        if (count > 0)
        {
            return false;
        }
        
        return true;
    }
    
    private boolean isResearchSampleReceiveFinished(String orderId)
    {
        // 是否包含待送样和送样中的
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT(s.id) FROM OrderResearchSample s WHERE (s.receiveStatus = :undeliveredStatus OR s.receiveStatus = :deliveringStatus) AND s.orderId = :orderId")
                .names(Arrays.asList("orderId", "undeliveredStatus", "deliveringStatus"))
                .values(Arrays.asList(orderId, OrderResearchSample.RECEIVE_STATUS_UNDELIVERED, OrderResearchSample.RECEIVE_STATUS_DELIVERING))
                .build();
        
        int count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        
        if (count > 0)
        {
            return false;
        }
        
        // 是否包含运输异常样本未处理的
        queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT(s.id) FROM OrderResearchSample s WHERE s.receiveStatus = :errorStatus AND (s.errorType = :deliveryErrorType OR s.errorType IS NULL) AND s.errorSolveStatus = :unsolvedStatus AND s.orderId = :orderId")
                .names(Arrays.asList("orderId", "errorStatus", "deliveryErrorType", "unsolvedStatus"))
                .values(Arrays.asList(orderId,
                    OrderResearchSample.RECEIVE_STATUS_ERROR,
                    OrderResearchSample.ERROR_TYPE_DELIVERY,
                    OrderResearchSample.ERROR_SOLVE_STATUS_UNSOLVED))
                .build();
        
        count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        
        if (count > 0)
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean isSampleStorageFinished(String id)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("SELECT COUNT(rs.id) FROM ReceivedSample rs WHERE rs.lsLocation IS NULL AND EXISTS (SELECT osv.id FROM OrderSampleView osv WHERE osv.orderId = rs.orderId AND osv.samplePackageStatus = 2 AND osv.orderId = :orderId)")
                .names(Collections.singletonList("orderId"))
                .values(Collections.singletonList(id))
                .build();
        int count = baseDaoSupport.find(queryer, Number.class).get(0).intValue();
        return count == 0;
    }
    
    @Override
    public boolean isReducedAsPaymentConfirmed(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        
        if (null == order)
        {
            return false;
        }
        
        int productAmount = null == order.getProductAmount() ? 0 : order.getProductAmount().intValue();
        int subsidiarySampleAmount = null == order.getSubsidiarySampleAmount() ? 0 : order.getSubsidiarySampleAmount().intValue();
        
        int incoming = null == order.getIncomingAmount() ? 0 : order.getIncomingAmount();
        int discount = null == order.getDiscountAmount() ? 0 : order.getDiscountAmount();
        int reduce = null == order.getReduceAmount() ? 0 : order.getReduceAmount();
        
        return incoming + discount + reduce >= productAmount + subsidiarySampleAmount;
    }
    
    @Override
    public boolean isAutoStartContractOrder(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        
        if (null == order)
        {
            return false;
        }
        
        String contractId = order.getContractId();
        
        if (StringUtils.isEmpty(contractId))
        {
            return false;
        }
        
        Contract contract = baseDaoSupport.get(Contract.class, contractId);
        ContractContent contractContent = baseDaoSupport.get(ContractContent.class, contractId);
        
        if (null == contract || null == contractContent)
        {
            return false;
        }
        
        return !ContractContent.SETTLEMENT_SINGLE.equals(contractContent.getSettlementMode()) && Contract.START_AUTO.equals(contract.getStartType());
    }
    
    @Override
    public PaymentConfirmType getOrderPaymentConfirmType(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        
        if (null == order)
        {
            return null;
        }
        
        String contractId = order.getContractId();
        
        if (StringUtils.isEmpty(contractId))
        {
            return PaymentConfirmType.MANUAL_CONFIRMED_SINGLE_SETTLEMENT;
        }
        
        Contract contract = baseDaoSupport.get(Contract.class, contractId);
        ContractContent contractContent = baseDaoSupport.get(ContractContent.class, contractId);
        
        if (null == contract || null == contractContent)
        {
            return PaymentConfirmType.MANUAL_CONFIRMED_SINGLE_SETTLEMENT;
        }
        
        if (ContractContent.SETTLEMENT_SINGLE.equals(contractContent.getSettlementMode()))
        {
            return PaymentConfirmType.MANUAL_CONFIRMED_SINGLE_SETTLEMENT;
        }
        
        if (Contract.START_AUTO.equals(contract.getStartType()))
        {
            return PaymentConfirmType.AUTO_CONFIRMED_CENTRAL_SETTLEMENT;
        }
        else
        {
            return PaymentConfirmType.MANUAL_CONFIRMED_CENTRAL_SETTLEMENT;
        }
    }
    
    @Override
    public Date getFirstPaymentAmountConfirmedTime(String id)
    {
        String hql = "SELECT MIN(t.confirmTime) FROM OrderPaymentConfirm t WHERE t.orderId = :orderId";
        List<Date> records = baseDaoSupport.findByNamedParam(Date.class, hql, new String[] {"orderId"}, new Object[] {id});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    @Override
    public Date getDelayPaymentAgreedTime(String id)
    {
        String hql =
            "SELECT MAX(t.approveTime) FROM OrderDelayPaymentApproveRecord t WHERE t.status = :approveAgreedStatus AND t.applyRecord.orderId = :orderId AND t.applyRecord.status = :applyAgreedStatus";
        List<Date> records =
            baseDaoSupport.findByNamedParam(Date.class, hql, new String[] {"orderId", "applyAgreedStatus", "approveAgreedStatus"}, new Object[] {id,
                OrderDelayPaymentApplyRecord.STATUS_ARGEED, OrderDelayPaymentApproveRecord.STATUS_ARGEED});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    @Override
    public Date getReduceAgreedTime(String id)
    {
        String hql =
            "SELECT MAX(t.approveTime) FROM OrderReduceApproveRecord t WHERE t.status = :approveAgreedStatus AND t.applyRecord.orderId = :orderId AND t.applyRecord.status = :applyAgreedStatus";
        List<Date> records =
            baseDaoSupport.findByNamedParam(Date.class, hql, new String[] {"orderId", "applyAgreedStatus", "approveAgreedStatus"}, new Object[] {id,
                OrderReduceApplyRecord.STATUS_ARGEED, OrderReduceApproveRecord.STATUS_ARGEED});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    @Override
    public Date getTestingStartTime(String id)
    {
        String hql = "SELECT MIN(t.startTime) FROM TestingSchedule t WHERE t.orderId = :orderId";
        List<Date> records = baseDaoSupport.findByNamedParam(Date.class, hql, new String[] {"orderId"}, new Object[] {id});
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    @Override
    public Date getLastSampleReceivedTime(String id, Date testingStartTime)
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("SELECT MAX(t.receiveTime) FROM SampleReceiving t WHERE t.orderId = :orderId");
        names.add("orderId");
        values.add(id);
        
        if (null != testingStartTime)
        {
            hql.append(" AND t.receiveTime <= :testingStartTime");
            names.add("testingStartTime");
            values.add(testingStartTime);
        }
        
        List<Date> records = baseDaoSupport.find(NamedQueryer.builder().hql(hql.toString()).names(names).values(values).build(), Date.class);
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    @Override
    public Date getLastSampleStoragedTime(String id, Date testingStartTime)
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("SELECT MAX(t.operateTime) FROM SampleStoraging t LEFT JOIN t.sampleStoragingDetail as d WHERE EXISTS (SELECT s.id FROM OrderSampleView s WHERE s.sampleCode = d.sampleCode AND s.orderId = :orderId)");
        names.add("orderId");
        values.add(id);
        
        if (null != testingStartTime)
        {
            hql.append(" AND t.operateTime <= :testingStartTime");
            names.add("testingStartTime");
            values.add(testingStartTime);
        }
        
        List<Date> records = baseDaoSupport.find(NamedQueryer.builder().hql(hql.toString()).names(names).values(values).build(), Date.class);
        return CollectionUtils.isEmpty(records) ? null : records.get(0);
    }
    
    private List<Product> getOrderProducts(String id)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM Product p WHERE EXISTS (SELECT op.id FROM OrderProduct op WHERE op.productId = p.id AND op.orderId = :orderId)")
                .names(Collections.singletonList("orderId"))
                .values(Collections.singletonList(id))
                .build();
        return baseDaoSupport.find(queryer, Product.class);
    }
    
    private List<Product> getResearchOrderProducts(String id)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM Product p WHERE EXISTS (SELECT rop.id FROM ResearchOrderProduct rop WHERE rop.productId = p.id AND rop.sample.orderId = :orderId)")
                .names(Collections.singletonList("orderId"))
                .values(Collections.singletonList(id))
                .build();
        return baseDaoSupport.find(queryer, Product.class);
    }
    
    private List<OrderProductTestings> getOrderProductTestings(Order order, List<Product> products)
    {
        Map<String, ScheduleTestingConfig> testingConfigs = new HashMap<String, ScheduleTestingConfig>();
        List<OrderProductTestings> records = new ArrayList<OrderProductTestings>();
        
        for (Product product : products)
        {
            records.add(getOrderProductTestings(order, product, testingConfigs));
        }
        
        return records;
    }
    
    private OrderProductTestings getOrderProductTestings(Order order, Product product, Map<String, ScheduleTestingConfig> testingConfigs)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM ProductTestingMethod ptm WHERE ptm.productId = :productId")
                .names(Collections.singletonList("productId"))
                .values(Collections.singletonList(product.getId()))
                .build();
        
        List<ProductTestingMethod> testingMethods = baseDaoSupport.find(queryer, ProductTestingMethod.class);
        
        NamedQueryer queryer2 =
            NamedQueryer.builder()
                .hql("FROM OrderSampleView osv WHERE osv.orderId = :orderId")
                .names(Collections.singletonList("orderId"))
                .values(Collections.singletonList(order.getId()))
                .build();
        
        List<OrderSampleView> sampleList = baseDaoSupport.find(queryer2, OrderSampleView.class);
        
        OrderProductTestings testings = new OrderProductTestings();
        testings.setOrderId(order.getId());
        testings.setProductId(product.getId());
        testings.setProductCode(product.getCode());
        testings.setProductName(product.getName());
        
        if (CollectionUtils.isEmpty(testingMethods))
        {
            testings.setDuration(-1);
        }
        else
        {
            int duration = -1;
            String scheduleConfigId;
            ScheduleTestingConfig scheduleConfig;
            
            for (ProductTestingMethod testingMethod : testingMethods)
            {
                scheduleConfigId = testingMethod.getScheduleConfigId();
                
                if (StringUtils.isEmpty(scheduleConfigId))
                {
                    continue;
                }
                
                scheduleConfig = testingConfigs.get(scheduleConfigId);
                
                if (null == scheduleConfig)
                {
                    scheduleConfig = baseDaoSupport.get(ScheduleTestingConfig.class, scheduleConfigId);
                    
                    if (null != scheduleConfig)
                    {
                        testingConfigs.put(scheduleConfigId, scheduleConfig);
                    }
                }
                
                if (null == scheduleConfig || null == scheduleConfig.getThreshold())
                {
                    continue;
                }
                //scheduleConfig.getThreshold()
                duration = Math.max(duration, getDuration(sampleList, product, testingMethod, scheduleConfig));
                
            }
            
            testings.setDuration(duration);
        }
        
        return testings;
    }
    
    private int getDuration(List<OrderSampleView> sampleList, Product product, ProductTestingMethod testingMethod, ScheduleTestingConfig scheduleConfig)
    {
        List<String> allProcess = Lists.newArrayList();
        TestingMethod method = baseDaoSupport.get(TestingMethod.class, testingMethod.getMethodId());
        
        if (StringUtils.isNotEmpty(method.getTestingProcess()))
        {
            String[] testingProcess = method.getTestingProcess().split("/");
            for (int i = 0; i < testingProcess.length; i++)
            {
                allProcess.add(testingProcess[i]);
            }
        }
        if (StringUtils.isNotEmpty(method.getAnalyseProcess()))
        {
            String[] analyProcess = method.getAnalyseProcess().split("/");
            for (int i = 0; i < analyProcess.length; i++)
            {
                allProcess.add(analyProcess[i]);
            }
        }
        
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM ScheduleTestingNodeConfig nc WHERE nc.configId = :configId")
                .names(Collections.singletonList("configId"))
                .values(Collections.singletonList(scheduleConfig.getId()))
                .build();
        
        List<ScheduleTestingNodeConfig> nodeConfigList = baseDaoSupport.find(queryer, ScheduleTestingNodeConfig.class);
        int threshold = 0;
        if (Collections3.isNotEmpty(allProcess))
        {
            for (String ps : allProcess)
            {
                if (Collections3.isNotEmpty(nodeConfigList))
                {
                    for (ScheduleTestingNodeConfig nodeConfig : nodeConfigList)
                    {
                        if (ps.equals(nodeConfig.getSemantic()))
                        {
                            threshold += nodeConfig.getThreshold();
                        }
                    }
                }
            }
        }
        
        int na = 0;
        if (Collections3.isNotEmpty(sampleList))
        {
            for (OrderSampleView sampleView : sampleList)
            {
                int count = 0;
                List<TestingNode> nodeList = productAdapter.getTestingNodes(sampleView.getSampleType(), product.getTestingSampleTypeId());
                if (Collections3.isNotEmpty(nodeList))
                {
                    for (TestingNode node : nodeList)
                    {
                        if (Collections3.isNotEmpty(nodeConfigList))
                        {
                            for (ScheduleTestingNodeConfig nodeConfig : nodeConfigList)
                            {
                                if (node.getType().equals(nodeConfig.getSemantic()))
                                {
                                    count += nodeConfig.getThreshold();
                                }
                            }
                        }
                    }
                }
                na = Math.max(na, count);
            }
        }
        return threshold + na;
    }
    
    @Override
    public boolean canWriteReportNode(String id)
    {
        Order order = baseDaoSupport.get(Order.class, id);
        if (null == order)
        {
            log.warn("Can not found order entity by id {}", id);
            return false;
        }
        if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(order.getContractId()))
        {
            Contract contract = baseDaoSupport.get(Contract.class, order.getContractId());
            if (null != contract)
            {
                List<ContractContent> contents =
                    baseDaoSupport.find(ContractContent.class, "FROM ContractContent c WHERE c.contractId = '" + contract.getId() + "'");
                if (Collections3.isNotEmpty(contents))
                {
                    String deliveryModel = contents.get(0).getDeliveryMode();
                    if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(deliveryModel))
                    {
                        if (!deliveryModel.contains("3"))
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
}
