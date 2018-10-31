package com.todaysoft.lims.system.modules.bmm.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.AdvanceInvoiceImportListModel;
import com.todaysoft.lims.system.modules.bmm.model.AdvanceInvoiceImportParseModel;
import com.todaysoft.lims.system.modules.bmm.model.InvoiceApplyModel;
import com.todaysoft.lims.system.modules.bmm.model.request.AdvanceInvoiceOrderProductRequest;
import com.todaysoft.lims.system.modules.bmm.model.request.AdvanceInvoiceSearcher;

public interface IAdvanceInvoiceService
{
    Pagination<InvoiceApplyModel> paging(AdvanceInvoiceSearcher searcher);
    
    InvoiceApplyModel get(String id);
    
    void solve(InvoiceApplyModel request);

    void delayAdvanceInvoice(String id);
    
    List<InvoiceApplyModel> list(AdvanceInvoiceSearcher searcher);
    
    String download(InputStream is, List<InvoiceApplyModel> list);
    
    List<AdvanceInvoiceImportParseModel> parse(MultipartFile file);
    
    void importSolve(AdvanceInvoiceImportListModel request);
    
    void updateOrderById(AdvanceInvoiceOrderProductRequest request);
    
    List<InvoiceApplyModel> simpleList(AdvanceInvoiceSearcher searcher);
    
}
