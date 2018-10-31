package com.todaysoft.lims.system.modules.invoice.service;

import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.invoice.model.InvoiceSendModel;
import com.todaysoft.lims.system.modules.invoice.model.InvoiceSendSearcher;
import com.todaysoft.lims.system.modules.invoice.service.request.InvoiceSendRequest;

public interface IInvoiceSendService
{
    Pagination<InvoiceSendModel> paging(InvoiceSendSearcher searcher);
    List<InvoiceSendModel> listByIds(InvoiceSendSearcher searcher);
    void create(InvoiceSendRequest invoiceSendRequest);
    Pagination<InvoiceSendModel> dealPaging(InvoiceSendSearcher searcher);
    List<InvoiceSendModel> waitingView(InvoiceSendSearcher searcher);
    List<InvoiceSendModel> doneView(InvoiceSendSearcher searcher);
    String download(InputStream is, InvoiceSendSearcher searcher);
    
    void startInvoiceScheduleTask();
}
