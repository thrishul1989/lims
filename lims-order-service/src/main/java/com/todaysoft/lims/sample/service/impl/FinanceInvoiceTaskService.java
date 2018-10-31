package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import com.todaysoft.lims.sample.service.IFinanceInvoiceTaskService;
import com.todaysoft.lims.utils.Collections3;

@Service
public class FinanceInvoiceTaskService implements IFinanceInvoiceTaskService
{
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public FinanceInvoiceTask get(String id)
    {
        return baseDaoSupport.get(FinanceInvoiceTask.class, id);
    }
    
    @Override
    public List<FinanceInvoiceTask> financeTaskList(String recordKey)
    {
        NamedQueryer queryer =
            NamedQueryer.builder()
                .hql("FROM FinanceInvoiceTask fit WHERE fit.recordKey = :recordKey")
                .names(Lists.newArrayList("recordKey"))
                .values(Lists.newArrayList(recordKey))
                .build();
        List<FinanceInvoiceTask> financeTaskList = baseDaoSupport.find(queryer, FinanceInvoiceTask.class);
        return financeTaskList;
    }
    
    @Override
    @Transactional
    public void modifyByRefund(String orderId)
    {
        String hqlOrder = "FROM FinanceInvoiceTask fit WHERE fit.status = 2 AND fit.recordKey = :orderId)";
        NamedQueryer queryerOrder = NamedQueryer.builder().hql(hqlOrder).names(Lists.newArrayList("orderId")).values(Lists.newArrayList(orderId)).build();
        List<FinanceInvoiceTask> financeTaskList = baseDaoSupport.find(queryerOrder, FinanceInvoiceTask.class);
        if (Collections3.isNotEmpty(financeTaskList))
        {
            FinanceInvoiceTask fit = Collections3.getFirst(financeTaskList);
            fit.setStatus(FinanceInvoiceTask.STATUS_TODO);
            baseDaoSupport.merge(fit);
        }
        else
        {
            String hqlIa =
                "FROM FinanceInvoiceTask fit WHERE fit.status = 2 AND EXISTS(SELECT ia.id FROM InvoiceApply ia WHERE ia.orderIds LIKE :orderId AND fit.recordKey = ia.id)";
            NamedQueryer queryerIa = NamedQueryer.builder().hql(hqlIa).names(Lists.newArrayList("orderId")).values(Lists.newArrayList(orderId)).build();
            List<FinanceInvoiceTask> fitList = baseDaoSupport.find(queryerIa, FinanceInvoiceTask.class);
            if (Collections3.isNotEmpty(fitList))
            {
                FinanceInvoiceTask fit = Collections3.getFirst(fitList);
                fit.setStatus(FinanceInvoiceTask.STATUS_TODO);
                baseDaoSupport.merge(fit);
            }
        }
    }
    
}
