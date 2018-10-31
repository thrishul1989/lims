package com.todaysoft.lims.maintain.order.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.maintain.commons.Constants;
import com.todaysoft.lims.maintain.entity.OrderProduct;
import com.todaysoft.lims.maintain.order.entity.ContractPaymentRecord;
import com.todaysoft.lims.maintain.order.entity.ContractReconciliationRecord;
import com.todaysoft.lims.maintain.order.entity.OrderEntity;
import com.todaysoft.lims.maintain.order.entity.OrderPaymentConfirm;
import com.todaysoft.lims.maintain.order.entity.OrderReduce;
import com.todaysoft.lims.maintain.order.entity.OrderSampleDetailsResponse;
import com.todaysoft.lims.maintain.order.service.IOrderService;
import com.todaysoft.lims.maintain.testing.service.impl.BMMAdapter;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

/**
 *SHEDULE_STATUS =2       5  代码实现 ---订单已完成

PS:全额支付标记 ： 应收额 - 减免（申请通过） - 确认到账< 0
 * @author admin
 *
 */
@Service
public class OrderService implements IOrderService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    private static Logger log = LoggerFactory.getLogger(OrderService.class);
    
    @Autowired
    private BMMAdapter bmmadapter;
    
    /**
     * 流程正在启动，并主状态是 检测中的 ---判断的名单是否已完成
     */
    @Override
    @Transactional
    public void changeStatus()
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" from OrderEntity o where o.sheduleStatus = :status and o.testingStatus !=:testingStatus ");
        NamedQueryer queryer =
            NamedQueryer.builder().hql(sql.toString()).names(Lists.newArrayList("status", "testingStatus")).values(Lists.newArrayList(2, 4)).build();
        List<OrderEntity> orders = baseDaoSupport.find(queryer, OrderEntity.class);
        
        orders.stream().forEach(o -> {
            checkOrderFinish(o);
        });
        
        log.info("测试日志文件输出");
    }
    
    public void checkOrderFinish(OrderEntity order)
    {
        if (checkOrderIsFinish(order))
        {
            order.setTestingStatus(Constants.ORDER_TESTING_FINISHED);//已完成
            //  order.setUpdateTime(new Date());
            //当科技订单完成后 对未取消的产品 重新算价格
            /*if (Constants.ORDER_RESEARCH_TYPE.equals(order.getOrderType()))
            {
                Integer reAmount = recaclResearcherOrder(order.getId());
                order.setAmount(reAmount);
            }
            else
            {
                //非科研 订单存在非一单一结
                if (Constants.ORDER_SETTLEMENT_CENTRAL == order.getSettlementType())
                {
                    Integer reAmount = recaclOrderProduct(order.getId());
                    order.setAmount(reAmount);
                }
            }*/
            baseDaoSupport.update(order);
            log.info("符合系统判定完成订单Id为:" + order.getId() + " 订单编号" + order.getCode());
            
        }
    }
    
    private boolean checkOrderIsFinish(OrderEntity order)
    {
        boolean completeOrder = false;
        if (StringUtils.isNotEmpty(order) && order.getSheduleStatus() == Constants.SHEDULE_STATUS_UNABLE)//订单已启动
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
    
    private Integer getOrderSettleType(OrderEntity order)
    {
        return order.getSettlementType();
    }
    
    private Integer recaclOrderProduct(String id)
    {
        Integer amount = 0;
        String hql = " select sum(p.productPrice) FROM OrderProduct p where p.order.id=:orderId and p.productStatus !=:status) ";
        List<Long> result =
            baseDaoSupport.findByNamedParam(Long.class, hql, new String[] {"orderId", "status"}, new Object[] {id, Constants.ORDER_PRODUCT_CANCEL});
        if (Collections3.isNotEmpty(result))
        {
            amount = Collections3.getFirst(result).intValue();
        }
        return amount;
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
    
    private boolean checkOrderAmount(OrderEntity o)
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
    
    //状态不为已完成的订单产品   6 ：已取消    ||  科研已取消1 
    private boolean checkOrderProductFinishStatus(OrderEntity target)
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
        return (!Constants.ORDER_RESEARCH_TYPE.equals(target.getOrderType()) && Collections3.isEmpty(result))
            || (Constants.ORDER_RESEARCH_TYPE.equals(target.getOrderType()) && Collections3.isEmpty(researchResult));
    }
    
    @Override
    @Transactional
    public List<String> changeOrderProduct()
    {
        String hql = " from OrderEntity o where o.orderType != :type and o.contractId is not null ";
        List<OrderEntity> orders = baseDaoSupport.findByNamedParam(OrderEntity.class, hql, new String[] {"type"}, new Object[] {"4"});
        log.debug("数据开始处理....");
        List<String> list = new LinkedList<String>();
        if (Collections3.isNotEmpty(orders))
        {
            
            orders.stream().forEach(o -> {
                searchOrderProductByOrder(o, list);
            });
        }
        
        log.debug("处理结束....");
        return list;
    }
    
    private List<String> searchOrderProductByOrder(OrderEntity o, List<String> list)
    {
        if (Collections3.isNotEmpty(o.getOrderProductList()))
        {
            for (OrderProduct op : o.getOrderProductList())
            {
                String hql = " select cp.contractPrice FROM ContractProduct cp  where  cp.contract.id=:cid and  cp.productId =:pid ";
                List<Integer> result =
                    baseDaoSupport.findByNamedParam(Integer.class, hql, new String[] {"cid", "pid"}, new Object[] {o.getContractId(), op.getProduct().getId()});
                if (Collections3.isNotEmpty(result))
                {
                    Integer amount = Collections3.getFirst(result).intValue();
                    if (!op.getProductPrice().equals(amount))
                    {
                        op.setProductPrice(amount);
                        list.add(op.getId());
                        log.info("系统需要处理订单产品价格老数据,订单产品主键为" + op.getId());
                    }
                    
                    baseDaoSupport.update(op);
                }
                
            }
            
        }
        return list;
        
    }
    
    @Override
    @Transactional
    public int changePaymentConfirmStatus()
    {
        int totalCount = 0;
        String hql =
            "select o from OrderEntity o where o.paymentStatus !=:status and  exists  (select p from OrderPos p where p.orderId = o.id ) "
                + "AND NOT EXISTS ( SELECT   c FROM OrderPaymentConfirm c where c.orderId = o.id ) ";
        
        List<OrderEntity> result =
            baseDaoSupport.findByNamedParam(OrderEntity.class, hql, new String[] {"status"}, new Object[] {Constants.ORDER_PAYMENT_CONFIRMING});
        
        if (Collections3.isNotEmpty(result))
        {
            log.info("符合pos机付款待确认总条数：" + result.size());
            
            for (OrderEntity o : result)
            {
                o.setPaymentStatus(Constants.ORDER_PAYMENT_CONFIRMING);
                baseDaoSupport.update(o);
                log.info("变更状态订单" + o.getCode());
                totalCount++;
            }
        }
        
        String tq =
            "   select t from OrderEntity t where t.paymentStatus !=:status and exists  (select j from OrderTransfer j where j.orderId = t.id ) AND NOT EXISTS"
                + " ( SELECT  u FROM OrderPaymentConfirm u where u.orderId = t.id )";
        
        List<OrderEntity> resultTrans =
            baseDaoSupport.findByNamedParam(OrderEntity.class, tq, new String[] {"status"}, new Object[] {Constants.ORDER_PAYMENT_CONFIRMING});
        if (Collections3.isNotEmpty(resultTrans))
        {
            log.info("符合转账付款待确认总条数：" + resultTrans.size());
            for (OrderEntity o : resultTrans)
            {
                
                o.setPaymentStatus(Constants.ORDER_PAYMENT_CONFIRMING);
                baseDaoSupport.update(o);
                log.info("变更状态订单" + o.getCode());
                totalCount++;
                
            }
        }
        return totalCount;
        
    }
    
    @Override
    public void searchReceiveStatus()
    {
        String hql = "select o from OrderEntity o where o.deleted = false and o.receivedSampleStatus=:status ";
        
        List<OrderEntity> result = baseDaoSupport.findByNamedParam(OrderEntity.class, hql, new String[] {"status"}, new Object[] {0});
        
        if (Collections3.isNotEmpty(result))
        {
            
            for (OrderEntity o : result)
            {
                OrderSampleDetailsResponse response = bmmadapter.getOrderSampleDetails(o.getId());
                Set<String> totalSampleCodes = response.getTotalSampleCodes();
                
                if (Collections3.isEmpty(totalSampleCodes))
                {
                    log.warn("Order {} total sample codes is empty.", o.getId());
                    continue;
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
                
                if (totalSampleCodes.isEmpty())
                {
                    log.info(" Need  change Order receivestatus,orderid: {}.", o.getId());
                }
                
            }
        }
        log.info(" OVER  SUCCESS.");
    }
    
    @Override
    @Transactional
    public void synchronizeOrderPayTime()
    {
        String hql = " from OrderEntity o where o.deleted = false and o.payTime is null ";
        
        List<OrderEntity> result = baseDaoSupport.find(OrderEntity.class, hql);
        
        if (Collections3.isNotEmpty(result))
        {
            log.info("total counts ：" + result.size());
            for (OrderEntity o : result)
            {
                // 集中模式是否是对账或者清单对账  
                if (o.getSettlementType().equals(2))
                {
                    List<ContractReconciliationRecord> recordList = getContractRecRecordByOrderId(o.getId());
                    if (Collections3.isNotEmpty(recordList)) // 不定期对账
                    {
                        o.setPayTime(recordList.get(0).getCreateTime());
                    }
                    else
                    {
                        List<ContractPaymentRecord> payList = getSettlePaymentByOrderId(o.getId());
                        if (Collections3.isNotEmpty(payList)) // 出账单对账
                        {
                            o.setPayTime(Collections3.getFirst(payList).getCheckTime());
                        }
                    }
                }
                else
                { //先查询是否有收款确认记录
                    List<OrderPaymentConfirm> paymentList = getOrderPaymentConfirmByOrderId(o.getId());
                    if (Collections3.isNotEmpty(paymentList))
                    {
                        o.setPayTime(paymentList.get(0).getCheckTime());
                    }
                }
                baseDaoSupport.update(o);
                log.info(" set order paytime success ：" + o.getCode());
            }
            
        }
        log.info(" OVER  SUCCESS.");
    }
    
    private List<ContractReconciliationRecord> getContractRecRecordByOrderId(String id)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM ContractReconciliationRecord ccs WHERE ccs.orderId = :orderId ORDER BY ccs.createTime ASC")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(id))
                .build();
        
        return baseDaoSupport.find(queryer, ContractReconciliationRecord.class);
    }
    
    private List<ContractPaymentRecord> getSettlePaymentByOrderId(String id)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("select ts FROM ContractPaymentRecord ts ,  ContractSettleBillDetail d  WHERE d.orderId = :orderId and d.settleBillId = ts.settleBillId  ORDER BY ts.checkTime ASC")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(id))
                .build();
        return baseDaoSupport.find(queryer, ContractPaymentRecord.class);
    }
    
    private List<OrderPaymentConfirm> getOrderPaymentConfirmByOrderId(String id)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM OrderPaymentConfirm ts WHERE ts.orderId = :orderId ORDER BY ts.checkTime ASC")
                .names(Arrays.asList("orderId"))
                .values(Arrays.asList(id))
                .build();
        return baseDaoSupport.find(queryer, OrderPaymentConfirm.class);
    }
    
}
