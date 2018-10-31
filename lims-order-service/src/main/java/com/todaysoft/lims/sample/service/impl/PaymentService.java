package com.todaysoft.lims.sample.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.PaymentSearchRequest;
import com.todaysoft.lims.sample.entity.contract.Contract;
import com.todaysoft.lims.sample.entity.contract.ContractContent;
import com.todaysoft.lims.sample.entity.contract.ContractPaymentRecord;
import com.todaysoft.lims.sample.entity.contract.ContractSettleBill;
import com.todaysoft.lims.sample.entity.contract.ContractSettleBillDetail;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.sample.entity.order.OrderProduct;
import com.todaysoft.lims.sample.entity.payment.Coupon;
import com.todaysoft.lims.sample.entity.payment.OrderDelay;
import com.todaysoft.lims.sample.entity.payment.OrderPaymentConfirm;
import com.todaysoft.lims.sample.entity.payment.OrderPaymentView;
import com.todaysoft.lims.sample.entity.payment.OrderPos;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.entity.payment.OrderRefundRecord;
import com.todaysoft.lims.sample.entity.payment.OrderTransfer;
import com.todaysoft.lims.sample.model.payment.PaymentFormRequest;
import com.todaysoft.lims.sample.model.request.StartOrderTestingRequest;
import com.todaysoft.lims.sample.ons.OrderStatusConsumer;
import com.todaysoft.lims.sample.service.IOrderService;
import com.todaysoft.lims.sample.service.IOrderStatusService;
import com.todaysoft.lims.sample.service.IPaymentService;
import com.todaysoft.lims.sample.service.adapter.TestingAdapter;
import com.todaysoft.lims.sample.service.order.Constants;
import com.todaysoft.lims.sample.util.Arith;
import com.todaysoft.lims.sample.util.Constant;
import com.todaysoft.lims.sample.util.ToDefaultUtils;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class PaymentService implements IPaymentService
{
    private static Logger log = LoggerFactory.getLogger(OrderStatusConsumer.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Autowired
    private IOrderStatusService orderStatusService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private TestingAdapter testingAdapter;
    
    @Override
    public Pagination<Order> appPaymentConfirm(PaymentSearchRequest request)
    {
        Pagination<Order> paging = baseDaoSupport.find(request.toQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    public Pagination<Order> delayConfirmList(PaymentSearchRequest request)
    {
        Pagination<Order> paging = baseDaoSupport.find(request.toDelayQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    public Pagination<Order> replenishmentList(PaymentSearchRequest request)
    {
        Pagination<Order> paging = baseDaoSupport.find(request.toReplenishQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    public Pagination<Order> refundmentList(PaymentSearchRequest request)
    {
        Pagination<Order> paging = baseDaoSupport.find(request.toRefundmentQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    public List<OrderPos> getParmentInfoByOrderId(PaymentSearchRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer subhql = new StringBuffer();
        subhql.append(" FROM OrderPos o where o.orderId =:oid ");
        queryer.setHql(subhql.toString());
        queryer.setNames(Arrays.asList("oid"));
        queryer.setValues(Arrays.asList((Object)request.getId()));
        return baseDaoSupport.find(queryer, OrderPos.class);
        
    }
    
    @Override
    public List<OrderTransfer> getTransInfoByOrderId(PaymentSearchRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer subhql = new StringBuffer();
        subhql.append(" FROM OrderTransfer o where o.orderId =:oid ");
        queryer.setHql(subhql.toString());
        queryer.setNames(Arrays.asList("oid"));
        queryer.setValues(Arrays.asList((Object)request.getId()));
        return baseDaoSupport.find(queryer, OrderTransfer.class);
    }
    
    @Override
    @Transactional
    public boolean confirmPayment(PaymentFormRequest request)
    {
        Order order = baseDaoSupport.get(Order.class, request.getOrderId());
        
        if (null == order || !orderStatusService.isConfirmOrder(order))
        {
            System.out.println("根据id：" + request.getOrderId() + "找不到状态为待付款确认的订单");
            return false;
        }
        else
        {
            double amount = null == request.getCheckAmount() ? 0D : request.getCheckAmount().doubleValue();
            Integer incoming = null == order.getIncomingAmount() ? 0 : order.getIncomingAmount();
            incoming += (int)amount;
            
            order.setIncomingAmount(incoming);
            orderStatusService.setForPaymentConfirmed(order);
            baseDaoSupport.update(order);
            
            OrderPaymentConfirm entity = new OrderPaymentConfirm();
            BeanUtils.copyProperties(request, entity, "id");
            entity.setCheckTime(new Date());
            entity.setPaymentType(Constant.ORDER_PAYMENT_TYPE_DEFAULT);//收款
            
            PaymentSearchRequest searcher = new PaymentSearchRequest();
            searcher.setId(order.getId());
            if (Constant.ORDER_PAYTYPE_POS.equals(order.getPayType()))
            {
                List<OrderPos> result = getParmentInfoByOrderId(searcher);
                entity.setOrderPos(Collections3.isNotEmpty(result) ? Collections3.getFirst(result) : null);
            }
            else if (Constant.ORDER_PAYTYPE_TRANS.equals(order.getPayType()))
            { //转账
                /* OrderTransfer transfer = baseDaoSupport.get(OrderTransfer.class, request.getPosOrTransId());
                 entity.setOrderTransfer(StringUtils.isNotEmpty(transfer) ? transfer : null);*/
                List<OrderTransfer> transfer = getTransInfoByOrderId(searcher);
                entity.setOrderTransfer(Collections3.isNotEmpty(transfer) ? Collections3.getFirst(transfer) : null);
            }
            entity.setPayType(order.getPayType());
            baseDaoSupport.insert(entity);
            return true;
        }
        
    }
    
    @Override
    @Transactional
    public Boolean backConfirmPayment(PaymentFormRequest request)
    {
        
        Boolean result = true;
        OrderPaymentConfirm entity = new OrderPaymentConfirm();
        BeanUtils.copyProperties(request, entity, "id");
        entity.setCheckTime(new Date());
        entity.setPaymentType(Constant.ORDER_PAYMENT_TYPE_DEFAULT);//收款
        Order order = baseDaoSupport.get(Order.class, request.getOrderId());
        if (StringUtils.isNotEmpty(order))
        {
            
            order.setPayType(request.getPayType()); //同时保存订单付款方式
            order.setDiscountCouponId(request.getDiscountCouponId()); //抵用券id
            if (StringUtils.isNotEmpty(request.getDiscountCouponId()))
            {
                Coupon coupon = baseDaoSupport.get(Coupon.class, request.getDiscountCouponId());
                if (StringUtils.isNotEmpty(coupon))
                {
                    order.setDiscountAmount(coupon.getAmount()); //抵用券金额
                }
                coupon.setConsumed(Constant.ORDER_COUPON_USED);
                baseDaoSupport.update(coupon);
                
            }
            
            if (StringUtils.isNotEmpty(request.getCheckAmount()))
            {
                order.setIncomingAmount(request.getCheckAmount().intValue());
            }
            orderStatusService.setForPaymentConfirmed(order);
            
            if (!StringUtils.isNotEmpty(order.getPayTime()))
            {
                order.setPayTime(new Date());
            }
            baseDaoSupport.update(order);
        }
        
        OrderPos pos = null;
        OrderTransfer transfer = null;
        if (Constant.ORDER_PAYTYPE_POS.equals(request.getPayType()))
        {
            pos = new OrderPos();
            BeanUtils.copyProperties(request, pos, "id");
            baseDaoSupport.insert(pos);
            entity.setOrderPos(pos);
            baseDaoSupport.insert(entity);
        }
        else
        {
            transfer = new OrderTransfer();
            BeanUtils.copyProperties(request, transfer, "id");
            transfer.setTransferDate(request.getPaymentTime());
            transfer.setTransferMember(request.getPaymenter());
            baseDaoSupport.insert(transfer);
            entity.setOrderTransfer(transfer);
            baseDaoSupport.insert(entity);
        }
        return result;
    }
    
    /**
     *判断延迟申请是否已通过，如果未通过，默认已通过，并且加备注
     */
    @Override
    @Transactional
    public void confirmDelayOrder(PaymentFormRequest request)
    {
        OrderPaymentConfirm entity = new OrderPaymentConfirm();
        BeanUtils.copyProperties(request, entity, "id");
        entity.setCheckTime(new Date());
        entity.setPaymentType(Constant.ORDER_PAYMENT_TYPE_DEFAULT);//收款
        
        Order order = baseDaoSupport.get(Order.class, request.getOrderId());
        if (StringUtils.isNotEmpty(order))
        {
            
            order.setPayType(request.getPayType()); //同时保存订单付款方式
            order.setDiscountCouponId(request.getDiscountCouponId()); //抵用券id
            Double amount = Arith.add(StringUtils.isNotEmpty(order.getIncomingAmount()) ? order.getIncomingAmount() : 0, request.getCheckAmount());
            
            order.setIncomingAmount(amount.intValue());
            orderStatusService.setForPaymentConfirmed(order);
            
            if (StringUtils.isNotEmpty(request.getDiscountCouponId()))
            {
                Coupon coupon = baseDaoSupport.get(Coupon.class, request.getDiscountCouponId());
                if (StringUtils.isNotEmpty(coupon))
                {
                    order.setDiscountAmount(coupon.getAmount()); //设置抵用券金额
                }
                coupon.setConsumed(Constant.ORDER_COUPON_USED);
                if (StringUtils.isNotEmpty(request.getCheckAmount()))
                {
                    order.setIncomingAmount(request.getCheckAmount().intValue());
                }
                if (!StringUtils.isNotEmpty(order.getPayTime()))
                {
                    order.setPayTime(new Date());
                }
                baseDaoSupport.update(coupon);
                
            }
            baseDaoSupport.update(order);
            
            List<OrderDelay> delay = order.getOrderDelay();
            if (Collections3.isNotEmpty(delay))
            {
                OrderDelay target = delay.get(0);
                target.setStatus(Constant.ORDER_DELAY_SUCCESS);
                target.setRemark("用户付款,自动审核通过");
                baseDaoSupport.update(target);
            }
        }
        
        OrderPos pos = null;
        OrderTransfer transfer = null;
        if (Constant.ORDER_PAYTYPE_POS.equals(request.getPayType()))
        {
            pos = new OrderPos();
            BeanUtils.copyProperties(request, pos, "id");
            baseDaoSupport.insert(pos);
            entity.setOrderPos(pos);
            baseDaoSupport.insert(entity);
        }
        else
        {
            transfer = new OrderTransfer();
            BeanUtils.copyProperties(request, transfer, "id");
            transfer.setTransferDate(request.getPaymentTime());
            transfer.setTransferMember(request.getPaymenter());
            baseDaoSupport.insert(transfer);
            entity.setOrderTransfer(transfer);
            baseDaoSupport.insert(entity);
        }
    }
    
    /**
     * 补款 ---确认记录
     */
    @Override
    @Transactional
    public void confirmReplenish(PaymentFormRequest request)
    {
        OrderPaymentConfirm entity = new OrderPaymentConfirm();
        BeanUtils.copyProperties(request, entity, "id");
        entity.setCheckTime(new Date());
        entity.setPaymentType(Constant.ORDER_PAYMENT_TYPE_REPLENISH);//补款
        Order order = baseDaoSupport.get(Order.class, request.getOrderId());
        if (StringUtils.isNotEmpty(order))
        {
            orderStatusService.setForPaymentConfirmed(order);
            double amount = null == request.getCheckAmount() ? 0D : request.getCheckAmount().doubleValue();
            Double price = Arith.add(amount, order.getIncomingAmount());
            order.setIncomingAmount(price.intValue()); //设置订单的补款金额
            if (!StringUtils.isNotEmpty(order.getPayTime()))
            {
                order.setPayTime(new Date());
            }
            baseDaoSupport.update(order);
        }
        OrderPos pos = null;
        OrderTransfer transfer = null;
        if (Constant.ORDER_PAYTYPE_POS.equals(request.getPayType()))
        {
            pos = new OrderPos();
            BeanUtils.copyProperties(request, pos, "id");
            baseDaoSupport.insert(pos);
            entity.setOrderPos(pos);
            baseDaoSupport.insert(entity);
        }
        else
        {
            transfer = new OrderTransfer();
            BeanUtils.copyProperties(request, transfer, "id");
            transfer.setTransferDate(request.getPaymentTime());
            transfer.setTransferMember(request.getPaymenter());
            baseDaoSupport.insert(transfer);
            entity.setOrderTransfer(transfer);
            baseDaoSupport.insert(entity);
        }
    }
    
    @Override
    public Pagination<Order> paymentHistory(PaymentSearchRequest request)
    {
        Pagination<Order> paging = baseDaoSupport.find(request.toPaymentHistoryQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    public List<OrderPaymentView> getPayMentViewByOrderId(PaymentSearchRequest request)
    {
        NamedQueryer queryer = new NamedQueryer();
        StringBuffer subhql = new StringBuffer();
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        subhql.append(" FROM OrderPaymentView o where 1 =1 ");
        addFilter(request, subhql, paramNames, parameters);
        queryer.setHql(subhql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return baseDaoSupport.find(queryer, OrderPaymentView.class);
    }
    
    private void addFilter(PaymentSearchRequest request, StringBuffer subhql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(request.getId()))
        {
            
            subhql.append(" AND o.orderId =:orderId");
            paramNames.add("orderId");
            parameters.add(request.getId());
            
        }
        if (StringUtils.isNotEmpty(request.getProtoId()))
        {
            
            subhql.append(" AND o.protoId =:protoId");
            paramNames.add("protoId");
            parameters.add(request.getProtoId());
            
        }
        
    }
    
    /**
     * 退款确认
     */
    @Override
    @Transactional
    public void confirmRefund(PaymentFormRequest request)
    {
        OrderRefundRecord record = new OrderRefundRecord();
        record.setOperateId(request.getCheckId());
        record.setOperateName(request.getCheckName());
        record.setOperateImg(request.getInstrumentImg());
        record.setOperateTime(new Date());
        record.setRefundAmount(request.getCheckAmount().intValue());
        record.setRefundName(request.getPaymenter()); //退款人
        record.setRefundTime(request.getPaymentTime());
        
        BeanUtils.copyProperties(request, record);
        baseDaoSupport.insert(record);
        
        Order order = baseDaoSupport.get(Order.class, request.getOrderId());
        OrderRefund orderRefund = baseDaoSupport.get(OrderRefund.class, request.getApplyId());
        if (StringUtils.isNotEmpty(order) && StringUtils.isNotEmpty(orderRefund))
        {
            String ids = orderRefund.getOrderProductId();
            if (StringUtils.isNotEmpty(ids))
            {
                OrderProduct orderProduct = baseDaoSupport.get(OrderProduct.class, ids);
                if (StringUtils.isNotEmpty(orderProduct))
                {
                    orderProduct.setRefundStatus(Constant.ORDER_PRODUCT_REFUND);
                    baseDaoSupport.update(orderProduct);
                }
            }
            orderRefund.setHandler(true);
            baseDaoSupport.update(orderRefund);
            
            if (StringUtils.isNotEmpty(order.getDiscountCouponId()))
            {
                Coupon coupon = baseDaoSupport.get(Coupon.class, order.getDiscountCouponId());
                if (StringUtils.isNotEmpty(coupon))
                {
                    coupon.setConsumed(Constant.ORDER_COUPON_USED);
                    baseDaoSupport.update(coupon);
                }
            }
            
            Double price = Arith.sub(order.getIncomingAmount(), request.getCheckAmount());
            order.setIncomingAmount(price.intValue()); //设置订单已收入减掉 --退款金额
            
            Double orderprice = Arith.sub(order.getAmount(), request.getCheckAmount());
            order.setAmount(orderprice.intValue());
            order.setUpdateTime(new Date());
            order.setUpdatorId(request.getCheckId());
            order.setUpdatorName(request.getCheckName());
            baseDaoSupport.update(order);
            
        }
        
    }
    
    @Override
    public OrderPaymentConfirm getPayMentById(String id)
    {
        return baseDaoSupport.get(OrderPaymentConfirm.class, id);
    }
    
    @Override
    public OrderRefundRecord getRefundRecordById(String id)
    {
        return baseDaoSupport.get(OrderRefundRecord.class, id);
    }
    
    @Override
    @Transactional
    public void contractPayment(PaymentFormRequest request)
    {
        ContractPaymentRecord entity = new ContractPaymentRecord();
        BeanUtils.copyProperties(request, entity);
        entity.setPayType(1); //默认转账
        entity.setCheckTime(new Date());
        baseDaoSupport.insert(entity);
        
        Contract contract = baseDaoSupport.get(Contract.class, request.getContractId());
        if (StringUtils.isNotEmpty(contract))
        {
            Integer amount = ToDefaultUtils.toDefault(contract.getIncomingAmount());
            Integer incomming = request.getCheckAmount().intValue();
            Double price = Arith.add(amount, incomming);
            Integer unReconciledAmount = ToDefaultUtils.toDefault(contract.getUnReconciledAmount());
            
            Double enterAmount = Arith.add(unReconciledAmount, incomming);
            contract.setIncomingAmount(price.intValue());
            if (StringUtils.isEmpty(request.getSettleBillId())) //不定期才会设置
            {
                contract.setUnReconciledAmount(enterAmount.intValue());
            }
            baseDaoSupport.update(contract);
        }
        
        if (StringUtils.isNotEmpty(request.getSettleBillId()))
        {
            ContractSettleBill settelBill = baseDaoSupport.get(ContractSettleBill.class, request.getSettleBillId());
            if (StringUtils.isNotEmpty(settelBill))
            {
                settelBill.setStatus(1); //已付款
                baseDaoSupport.update(settelBill);
                
                //更新清单相关订单 收入额、状态
                List<ContractSettleBillDetail> detail = settelBill.getSettleBillDetail();
                if (Collections3.isNotEmpty(detail))
                {
                    detail.stream().forEach(o -> {
                        Order order = baseDaoSupport.get(Order.class, o.getOrderId());
                        if (StringUtils.isNotEmpty(order))
                        {
                            orderStatusService.setForOrderReconciliated(order); //已对账
                            order.setIncomingAmount(o.getOrderAmount());
                            order.setPayTime(new Date());
                            baseDaoSupport.update(order);
                        }
                        
                    });
                }
                
            }
            
        }
        
    }
    
    @Override
    public List<OrderPaymentConfirm> getOrderPaymentConfirmByOrderId(String orderId)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPaymentConfirm ts WHERE ts.orderId = :orderId ORDER BY ts.checkTime ASC")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(orderId))
                .build();
        return baseDaoSupport.find(queryer, OrderPaymentConfirm.class);
    }
    
    @Override
    public Pagination<Order> cancelOrderList(PaymentSearchRequest request)
    {
        Pagination<Order> paging = baseDaoSupport.find(request.toCancelQuery(), request.getPageNo(), request.getPageSize(), Order.class);
        return paging;
    }
    
    @Override
    @Transactional
    public boolean checkOrderFinish(String orderId)
    {
        Order order = baseDaoSupport.get(Order.class, orderId);
        
        if (checkOrderIsFinish(order))
        {
            order.setTestingStatus(Constants.ORDER_TESTING_FINISHED);//已完成
            order.setUpdateTime(new Date());
            //当科技订单完成后 对未取消的产品 重新算价格
            if (Constant.ORDER_RESEARCH_TYPE.equals(order.getOrderType()))
            {
                Integer reAmount = recaclResearcherOrder(order.getId());
                order.setAmount(reAmount);
            }
            else
            {
                //非科研 订单存在非一单一结
                if (StringUtils.isNotEmpty(order.getContract()))
                {
                    ContractContent content = orderService.getContractContentByContractId(order.getContract().getId());
                    if (StringUtils.isNotEmpty(content))
                    {
                        if (!Constant.CONTRACT_SETTLE_METHOD_ONEORDER.equals(content.getSettlementMode()))
                        {
                            Integer reAmount = recaclOrderProduct(order.getId());
                            order.setAmount(reAmount);
                        }
                    }
                    
                }
                
            }
            
            baseDaoSupport.update(order);
            
            //提前出报告，完成后，未结束的检测流程取消
            StartOrderTestingRequest startOrderTestingRequest = new StartOrderTestingRequest();
            startOrderTestingRequest.setId(orderId);
            startOrderTestingRequest.setCancelRemark("提前出报告，订单已完成，流程自动取消");
            startOrderTestingRequest.setCancelTime(new Date());
            testingAdapter.cancelOrderSchedule(startOrderTestingRequest);//取消流程
            
        }
        return true;
    }
    
    private boolean checkOrderIsFinish(Order order)
    {
        boolean completeOrder = false;
        if (StringUtils.isNotEmpty(order) && order.getSheduleStatus() == Constant.SHEDULE_STATUS_UNABLE)//订单已启动
        {
            
            Integer settleType = getOrderSettleType(order); //结算模式
            
            boolean datasendResult = checkOrderProductFinishStatus(order);
            
            if (datasendResult)
            {
                if (settleType == Constants.ORDER_SETTLEMENT_SINGLE) //一单一结
                {
                    if (checkOrderAmount(order))
                    {
                        completeOrder = true;
                    }
                }
                else
                {
                    completeOrder = true;
                }
                
            }
            
        }
        return completeOrder;
    }
    
    @Override
    @Transactional
    public boolean checkOrderIsCancel(String orderId)
    {
        boolean cancelOrder = false;
        Order o = baseDaoSupport.get(Order.class, orderId);
        if (StringUtils.isNotEmpty(o))
        {
            cancelOrder = checkOrderProductCancelStatus(o);
            log.info("check order iscancel {} " + cancelOrder);
            if (cancelOrder) //取消订单
            {
                o.setTestingStatus(Constants.ORDER_TESTING_CANCELED);//已取消
                o.setUpdateTime(new Date());
                o.setCancelTime(new Date());
                o.setCancelName("系统取消");
                o.setCancelRemark("流程都已取消,系统确认订单取消");
                baseDaoSupport.update(o);
            }
            else if (checkOrderIsFinish(o))
            { //有可能是完成订单
                log.info("check order iscancel end , start check order isfinish {} " + checkOrderIsFinish(o));
                
                o.setTestingStatus(Constants.ORDER_TESTING_FINISHED);//已完成
                o.setUpdateTime(new Date());
                //当科技订单完成后 对未取消的产品 重新算价格
                if (Constant.ORDER_RESEARCH_TYPE.equals(o.getOrderType()))
                {
                    Integer reAmount = recaclResearcherOrder(o.getId());
                    o.setAmount(reAmount);
                }
                
                baseDaoSupport.update(o);
            }
            
        }
        
        return true;
    }
    
    /* private boolean checkOrderProductByStatus(Order o)
     {
         boolean cancelOrder = false;
         if (Constant.ORDER_RESEARCH_TYPE.equals(o.getOrderType()))
         {
             String hql = " select p FROM OrderResearchSample s join s.orderResearchProduct p where s.order.id=:orderId and p.productStatus !=:status) ";
             List<OrderResearchProduct> result =
                 baseDaoSupport.findByNamedParam(OrderResearchProduct.class, hql, new String[] {"orderId", "status"}, new Object[] {o.getId(),
                     Constants.ORDER_RESEARCH_PRODUCT_CANCEL});
             
             cancelOrder = Collections3.isEmpty(result);
             
         }
         else
         {
             //非科研
             if (Collections3.isNotEmpty(o.getOrderProductList()))
             {
                 Long count = o.getOrderProductList().stream().filter(e -> e.getProductStatus() != Constants.ORDER_PRODUCT_CANCEL).count();
                 cancelOrder = count.intValue() <= 0;
             }
         }
         return cancelOrder;
     }*/
    
    /*String[] modes = result.getRight().split(",");//交付形式
      List<String> modeList = Arrays.asList(modes);
      if ((modeList.size() >= 2 || modeList.contains("1") || modeList.contains("2")) && !modeList.contains("3"))
      {//选择原始数据或者分析数据，进入数据发送
       r1 = searchDataSendIsFinish(order.getId());
      }
      
      for (String mode : modes)
      {
       if ("3".equals(mode))
       {
           //如果科研订单选择了结题报告
           r2 = searchConcludingReportIsFinish(order.getId());
       }
      }*/
    
    private boolean searchDataSendIsFinish(String id)
    {
        String hql = " from TestingDataSend s where s.orderId = :id and s.statu !=:status ";
        List<?> result = baseDaoSupport.findByNamedParam(hql, new String[] {"id", "status"}, new Object[] {id, 1}); //1---已完成
        return Collections3.isEmpty(result); //空 ---true ---已完成
    }
    
    private boolean searchConcludingReportIsFinish(String id)
    {
        String hql = " from TestingConcludingReport s where s.orderId = :id and s.statu !=:status ";
        List<?> result = baseDaoSupport.findByNamedParam(hql, new String[] {"id", "status"}, new Object[] {id, 1}); //1---已完成
        return Collections3.isEmpty(result); //空 ---true ---已完成
    }
    
    private Integer getOrderSettleType(Order order)
    {
        
        Integer settlementType = Constants.ORDER_SETTLEMENT_SINGLE;
        String deliveryMode = "";
        if (StringUtils.isNotEmpty(order.getContract()))
        {
            Contract contract = order.getContract();
            ContractContent content = orderService.getContractContentByContractId(contract.getId());
            if (StringUtils.isNotEmpty(content))
            {
                if (!Constant.CONTRACT_SETTLE_METHOD_ONEORDER.equals(content.getSettlementMode()))
                {
                    settlementType = Constants.ORDER_SETTLEMENT_CENTRAL;
                }
                deliveryMode = content.getDeliveryMode();
            }
        }
        
        return settlementType;
    }
    
    private Integer recaclResearcherOrder(String id)
    {
        Integer amount = 0;
        String hql =
            " select sum(p.productPrice) FROM OrderResearchSample s join s.orderResearchProduct p where s.order.id=:orderId and p.productStatus !=:status) ";
        List<Long> result =
            baseDaoSupport.findByNamedParam(Long.class, hql, new String[] {"orderId", "status"}, new Object[] {id, Constants.ORDER_RESEARCH_PRODUCT_CANCEL});
        if (Collections3.isNotEmpty(result))
        {
            amount = Collections3.getFirst(result).intValue();
        }
        return amount;
    }
    
    @Override
    public Integer recaclOrderProduct(String id)
    {
        Integer amount = 0;
        String hql = " select sum(p.productPrice) FROM OrderProduct p where p.order.id=:orderId and p.productStatus !=:status) ";
        List<Long> result =
            baseDaoSupport.findByNamedParam(Long.class, hql, new String[] {"orderId", "status"}, new Object[] {id, Constants.ORDER_PRODUCT_CANCEL});
        if (Collections3.isNotEmpty(result))
        {
            
            if (null != Collections3.getFirst(result))
            {
                amount = Collections3.getFirst(result).intValue();
            }
            
        }
        return amount;
    }
    
    @Override
    public Integer recaclOrderProductInvoice(String id)
    {
        Integer amount = 0;
        String hql = " select sum(p.productPrice) FROM OrderProduct p where p.order.id=:orderId ) ";
        List<Long> result = baseDaoSupport.findByNamedParam(Long.class, hql, new String[] {"orderId"}, new Object[] {id});
        if (Collections3.isNotEmpty(result))
        {
            if (null != Collections3.getFirst(result))
            {
                amount = Collections3.getFirst(result).intValue();
            }
            
        }
        return amount;
    }
    
    //是否已结清
    private boolean checkOrderAmount(Order o)
    {
        Integer reduceAmount = 0;
        List<OrderReduce> reduce = o.getOrderReduce();
        if (Collections3.isNotEmpty(reduce))
        {
            OrderReduce entity = Collections3.getFirst(reduce);
            if (entity.getStatus() == 1) //审核通过
            {
                reduceAmount = entity.getCheckAmount();
            }
        }
        o.setIncomingAmount(o.getIncomingAmount() == null ? 0 : o.getIncomingAmount());
        return o.getAmount() + o.getSubsidiarySampleAmount() - o.getDiscountAmount() - reduceAmount - o.getIncomingAmount() <= 0;
    }
    
    //状态不为取消的订单产品   8 ：已取消    ||  科研已取消 2 
    private boolean checkOrderProductCancelStatus(Order target)
    {
        String researchHql = " select p.id FROM OrderResearchSample s join s.orderResearchProduct p where s.order.id=:orderId and p.productStatus !=:status ";
        List<String> researchResult =
            baseDaoSupport.findByNamedParam(String.class, researchHql, new String[] {"orderId", "status"}, new Object[] {target.getId(),
                Constants.ORDER_RESEARCH_PRODUCT_CANCEL});
        
        String hql = " select p.id FROM OrderProduct p where p.order.id=:orderId and p.productStatus !=:status ";
        List<String> result =
            baseDaoSupport.findByNamedParam(String.class,
                hql,
                new String[] {"orderId", "status"},
                new Object[] {target.getId(), Constants.ORDER_PRODUCT_CANCEL});
        
        return (!Constant.ORDER_RESEARCH_TYPE.equals(target.getOrderType()) && Collections3.isEmpty(result))
            || (Constant.ORDER_RESEARCH_TYPE.equals(target.getOrderType()) && Collections3.isEmpty(researchResult));
    }
    
    //状态不为已完成的订单产品   6 ：已取消    ||  科研已取消1 
    private boolean checkOrderProductFinishStatus(Order target)
    {
        String researchHql =
            " select p.id FROM OrderResearchSample s join s.orderResearchProduct p where s.order.id=:orderId and p.productStatus not in (:status) ";
        List<String> researchResult =
            baseDaoSupport.findByNamedParam(String.class,
                researchHql,
                new String[] {"orderId", "status"},
                new Object[] {target.getId(), Lists.newArrayList(Constants.ORDER_RESEARCH_PRODUCT_FINISH, Constants.ORDER_RESEARCH_PRODUCT_CANCEL)});
        
        String hql = " select p.id FROM OrderProduct p where p.order.id=:orderId and p.productStatus not in (:status) ";
        List<String> result =
            baseDaoSupport.findByNamedParam(String.class,
                hql,
                new String[] {"orderId", "status"},
                new Object[] {target.getId(), Lists.newArrayList(Constants.ORDER_PRODUCT_FINISH, Constants.ORDER_PRODUCT_CANCEL)});
        return (!Constant.ORDER_RESEARCH_TYPE.equals(target.getOrderType()) && Collections3.isEmpty(result))
            || (Constant.ORDER_RESEARCH_TYPE.equals(target.getOrderType()) && Collections3.isEmpty(researchResult));
    }
    
}
