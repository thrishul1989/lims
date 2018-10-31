package com.todaysoft.lims.invoice.service;

import com.todaysoft.lims.invoice.entity.FinanceInvoiceTask;
import com.todaysoft.lims.invoice.entity.GoldenTemporary;
import com.todaysoft.lims.invoice.ons.InvoiceApplyEvent;

public interface IInvoiceService
{
    void apply(InvoiceApplyEvent event);
    
    void updateStatus(FinanceInvoiceTask task);
    
    void updateStatusForManual(FinanceInvoiceTask x);
    
    void updateStatusForAuto(FinanceInvoiceTask x);

    void dockingInvoiceForManual(FinanceInvoiceTask x);

    void dockingInvoiceForAuto(FinanceInvoiceTask x);

    void updateInvoiceInfoByTemporary(GoldenTemporary x);

}
