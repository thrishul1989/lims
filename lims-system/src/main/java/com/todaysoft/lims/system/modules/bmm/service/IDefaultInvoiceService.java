package com.todaysoft.lims.system.modules.bmm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.DefaultInvoiceModel;
import com.todaysoft.lims.system.modules.bmm.model.FinanceInvoiceTask;
import com.todaysoft.lims.system.modules.bmm.model.request.DefaultInvoiceRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.DefaultInvoiceSearcher;
import com.todaysoft.lims.system.modules.bmm.model.response.VerifiedOrderInvoiceImportRecord;

public interface IDefaultInvoiceService
{
    Pagination<DefaultInvoiceModel> paging(DefaultInvoiceSearcher searcher);
    
    DefaultInvoiceModel get(String id);
    
    void solve(DefaultInvoiceModel request);

    void delayAdvanceInvoice(String id);
    
    List<DefaultInvoiceModel> list(DefaultInvoiceSearcher searcher);
    
    String export(DefaultInvoiceSearcher searcher);
    
    List<VerifiedOrderInvoiceImportRecord> parse(MultipartFile file);
    
    void importRecords(List<VerifiedOrderInvoiceImportRecord> records);
    
    DefaultInvoiceModel getByOrderId(String id);
    
    DefaultInvoiceModel getInfoByOrderId(String orderId);
    
    FinanceInvoiceTask getFinanceInvoiceByOrderId(String orderId);
    
    void updateOrderById(DefaultInvoiceRequest request);
}
