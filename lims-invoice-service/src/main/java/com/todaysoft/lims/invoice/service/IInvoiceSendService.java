package com.todaysoft.lims.invoice.service;

import java.util.List;

import com.todaysoft.lims.invoice.dao.search.InvoiceSendDealSearcher;
import com.todaysoft.lims.invoice.dao.search.InvoiceSendSearcher;
import com.todaysoft.lims.invoice.entity.InvoiceSend;
import com.todaysoft.lims.invoice.entity.InvoiceSendRecordKey;
import com.todaysoft.lims.invoice.model.InvoiceWatingSend;
import com.todaysoft.lims.persist.Pagination;

public interface IInvoiceSendService
{
    Pagination<InvoiceWatingSend> paging(InvoiceSendSearcher searcher);
    List<InvoiceWatingSend> listByIds(InvoiceSendSearcher searcher);
    void create(InvoiceSend request);
    Pagination<InvoiceWatingSend> dealPaging(InvoiceSendSearcher searcher);
    InvoiceSendRecordKey getInvoiceSendRecordKey(InvoiceSendDealSearcher searcher);
    List<InvoiceWatingSend> waitingView(InvoiceSendSearcher searcher);
    List<InvoiceWatingSend> doneView(InvoiceSendSearcher searcher);
}
