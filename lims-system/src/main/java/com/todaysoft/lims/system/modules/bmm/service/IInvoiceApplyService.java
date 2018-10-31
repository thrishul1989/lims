package com.todaysoft.lims.system.modules.bmm.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApply;
import com.todaysoft.lims.system.modules.bmm.model.request.InvoiceApplySearcher;

public interface IInvoiceApplyService
{
    Pagination<InvoiceApply> paging(InvoiceApplySearcher searcher);
    
    InvoiceApply get(String id);
    
    void doApply(InvoiceApply data);
    
    void send(InvoiceApply data);
}
