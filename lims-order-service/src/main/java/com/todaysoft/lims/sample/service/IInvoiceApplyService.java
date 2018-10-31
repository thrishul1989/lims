package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.InvoiceApplySearcher;
import com.todaysoft.lims.sample.entity.InvoiceApply;
import com.todaysoft.lims.sample.model.InvoiceApplyEvent;

public interface IInvoiceApplyService
{
    Pagination<InvoiceApply> paging(InvoiceApplySearcher searcher);
    
    InvoiceApply get(String id);
    
    void doApply(InvoiceApply data);
    
    void send(InvoiceApply data);
    
    List<String> institutionList(InvoiceApplyEvent event);
}
