package com.todaysoft.lims.maintain.invoice.service.impl;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.maintain.entity.FinanceInvoiceTask;
import com.todaysoft.lims.maintain.entity.InvoiceApply;
import com.todaysoft.lims.maintain.invoice.service.IInvoiceService;
import com.todaysoft.lims.maintain.order.entity.OrderEntity;
import com.todaysoft.lims.maintain.order.entity.OrderReduce;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Service
public class InvoiceService implements IInvoiceService
{
    private static Logger log = LoggerFactory.getLogger(InvoiceService.class);
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;

    @Override
    @Transactional
    public void solveZeroDatas()
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM FinanceInvoiceTask t WHERE t.status != :status")
                .names(Arrays.asList("status"))
                .values(Arrays.asList(FinanceInvoiceTask.STATUS_ALREADY))
                .build();
       List<FinanceInvoiceTask> list = baseDaoSupport.find(queryer,FinanceInvoiceTask.class);
       if(Collections3.isNotEmpty(list))
       {
           for(FinanceInvoiceTask task : list)
           {
               if("1".equals(task.getCategory()))//1-默认开票 2-申请开票
               {
                   OrderEntity order = baseDaoSupport.get(OrderEntity.class,task.getRecordKey());
                   if(null != order)
                   {
                       if(isAmountZero(order))
                       {
                           baseDaoSupport.delete(task);
                           log.debug("delete order for financeInvoiceTask , task id is {}...", task.getId());
                       }
                   }
               }
               if("2".equals(task.getCategory()))
               {
                   InvoiceApply record = baseDaoSupport.get(InvoiceApply.class, task.getRecordKey());
                   if(null != record)
                   {
                       //发票申请中所有订单应收款为0 ，才不生成开票任务
                       boolean flag = true;
                       String orderIds = record.getOrderIds();
                       if(StringUtils.isNotEmpty(orderIds))
                       {
                           Set<String> orderKeys = new HashSet<String>(Arrays.asList(orderIds.split(",")));
                           for (String orderKey : orderKeys)
                           {
                               OrderEntity order = baseDaoSupport.get(OrderEntity.class, orderKey);
                               
                               if (null != order)
                               {
                                   if (!isAmountZero(order))
                                   {
                                       flag = false;
                                   }
                               }
                           }
                       }
                       if(flag)
                       {
                           baseDaoSupport.delete(task);
                           log.debug("delete invoiceApply for financeInvoiceTask , task id is {}...", task.getId());
                       }
                   }
               }
           }
       }
       log.debug(" End delete financeInvoiceTask...");
    }
    
  //判断应收款是否为0
    private boolean isAmountZero(OrderEntity order)
    {
        int amount = null == order.getAmount() ? 0 : order.getAmount();
        int sanpleAmount = null == order.getSubsidiarySampleAmount() ? 0 : order.getSubsidiarySampleAmount();
        
        // 抵用券
        int dicountAmount = null == order.getDiscountAmount() ? 0 : order.getDiscountAmount();
        String hql = "FROM OrderReduce r WHERE r.orderId.id = :orderId AND r.status = :agreedStatus";
        // 减免金额
        List<OrderReduce> reduces =
            baseDaoSupport.findByNamedParam(OrderReduce.class, hql, new String[] {"orderId", "agreedStatus"}, new Object[] {order.getId(), 1});
        int reduceAmount = Collections3.isEmpty(reduces) ? 0 : (null == reduces.get(0).getCheckAmount() ? 0 : reduces.get(0).getCheckAmount());
        return 0 == (amount + sanpleAmount - dicountAmount - reduceAmount) ? true : false;
        
    }
}
