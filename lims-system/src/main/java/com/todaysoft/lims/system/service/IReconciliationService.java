package com.todaysoft.lims.system.service;

import java.io.InputStream;
import java.util.List;

import com.todaysoft.lims.system.model.searcher.OrderAccountCheckTaskSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.payment.OrderPaymentConfirm;
import com.todaysoft.lims.system.model.vo.reconciliation.AccountCheckMistake;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderAccountCheckTask;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderAccountStateRecord;
import com.todaysoft.lims.system.model.vo.reconciliation.OrderPaymentConfirmSearcher;

public interface IReconciliationService
{
    
    Pagination<OrderAccountCheckTask> paging(OrderAccountCheckTaskSearcher searcher, int pageNo, int defaultpagesize);
    
    Pagination<OrderPaymentConfirm> getPaymentRecordByDate(OrderPaymentConfirmSearcher searcher, int pageNo, int defaultpagesize);
    
    void handle(OrderAccountCheckTask request);
    
    OrderAccountStateRecord accountStateRecordDetail(OrderPaymentConfirmSearcher searcher);
    
    Pagination<AccountCheckMistake> mistakePaging(OrderPaymentConfirmSearcher searcher, int pageNo, int defaultpagesize);
    
    List<OrderAccountStateRecord> searchOrderAccountStateByDate(OrderPaymentConfirmSearcher searcher);
    
    String download(InputStream is, List<OrderAccountStateRecord> list);
    
}
