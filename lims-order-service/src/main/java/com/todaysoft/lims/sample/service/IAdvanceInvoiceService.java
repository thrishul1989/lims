package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.AdvanceInvoiceSearcher;
import com.todaysoft.lims.sample.model.AdvanceInvoiceImportListModel;
import com.todaysoft.lims.sample.model.AdvanceInvoiceOrderProductRequest;
import com.todaysoft.lims.sample.model.InvoiceApplyModel;

public interface IAdvanceInvoiceService
{
    Pagination<InvoiceApplyModel> paging(AdvanceInvoiceSearcher searcher);
    
    InvoiceApplyModel get(String id);
    
    void solve(InvoiceApplyModel request);

    void delayAdvanceInvoice(InvoiceApplyModel request);
    
    List<InvoiceApplyModel> list(AdvanceInvoiceSearcher searcher);
    
    void importSolve(AdvanceInvoiceImportListModel request);
    
    void updateOrdersById(AdvanceInvoiceOrderProductRequest request);
    
    List<InvoiceApplyModel> simpleList(AdvanceInvoiceSearcher searcher);
}
