package com.todaysoft.lims.sample.service;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.DefaultInvoiceSearcher;
import com.todaysoft.lims.sample.entity.FinanceInvoiceTask;
import com.todaysoft.lims.sample.model.DefaultInvoiceImportListModel;
import com.todaysoft.lims.sample.model.DefaultInvoiceModel;

public interface IDefaultInvoiceService
{
    Pagination<DefaultInvoiceModel> paging(DefaultInvoiceSearcher searcher);
    
    DefaultInvoiceModel get(String id, String orderId);
    
    void solve(DefaultInvoiceModel request);

    void delayAdvanceInvoice(DefaultInvoiceModel request);

    List<DefaultInvoiceModel> list(DefaultInvoiceSearcher searcher);
    
    void importSolve(DefaultInvoiceImportListModel request);
    
    DefaultInvoiceModel getInfoByOrderId(String orderId);
    
    FinanceInvoiceTask getFinanceInvoiceByOrderId(String orderId);
    
    BigDecimal getRealtimeInvoiceAmount(String id);
}
