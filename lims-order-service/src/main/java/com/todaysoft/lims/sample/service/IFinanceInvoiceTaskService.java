package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;

public interface IFinanceInvoiceTaskService
{
    FinanceInvoiceTask get(String id);
    
    List<FinanceInvoiceTask> financeTaskList(String recordKey);
    
    void modifyByRefund(String orderId);
}
